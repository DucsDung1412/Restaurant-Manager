package vn.DungVipPro.java5_asm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import vn.DungVipPro.java5_asm.dao.OrderDetailsDAO;
import vn.DungVipPro.java5_asm.dao.OrdersDAO;
import vn.DungVipPro.java5_asm.dao.UserInfoDAO;
import vn.DungVipPro.java5_asm.model.*;
import vn.DungVipPro.java5_asm.service.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class PageController {
    private ProductsService productsService;
    private UserInfoService userInfoService;
    private UsersService usersService;
    private HttpSession session;
    private OrderDetailsService orderDetailsService;
    private OrdersService ordersService;

    @Autowired
    public PageController(OrdersService ordersService, ProductsService productsService, UserInfoService userInfoService, UsersService usersService, OrderDetailsService orderDetailsService, HttpSession session) {
        this.productsService = productsService;
        this.userInfoService = userInfoService;
        this.usersService = usersService;
        this.session = session;
        this.orderDetailsService = orderDetailsService;
        this.ordersService = ordersService;
    }

    @GetMapping("/test")
    public String test(Model model) throws IOException {
        Products p = productsService.findById(19L).get();
        File f = new File(new ClassPath(".").getPath(".") + "/src/main/resources/static/images");
        Path path = Paths.get(f.getAbsolutePath() + File.separator + p.getCategory() + File.separator + p.getSeason() + File.separator + p.getCountry() + File.separator + p.getImage());
        String image = "../images/" +  p.getCategory() + File.separator + p.getSeason() + File.separator + p.getCountry() + File.separator + p.getImage();
        model.addAttribute("image", image);
        return "/test";
    }

    @GetMapping("/index")
    public String index(Model model, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!username.equals("anonymousUser")){
            Users u = this.usersService.findById(username);
            for(Orders o : u.getUserInfo().getList()){
                if(o.getStatusOrders()){
                    model.addAttribute("listSP", o.getList());
                    model.addAttribute("ListSPSize", o.getList().size());
                    break;
                }
            }
        } else {
            model.addAttribute("ListSPSize", 0);
            model.addAttribute("listSP", null);
        }

        pageable = PageRequest.of(0, 6);
        Page<Products> listProducts = this.productsService.findByCategory("Starter",pageable);
        model.addAttribute("listProducts", listProducts);
        return "/index";
    }

    @GetMapping("/menu")
    public String menu(Model model, Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!username.equals("anonymousUser")){
            Users u = this.usersService.findById(username);
            for(Orders o : u.getUserInfo().getList()){
                if(o.getStatusOrders()){
                    model.addAttribute("listSP", o.getList());
                    break;
                }
            }
        } else {
            model.addAttribute("listSP", null);
        }

        Page<Products> listProducts = this.productsService.findAll(pageable);
        model.addAttribute("listProducts", listProducts);
        return "/menu";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<OrderDetails> cart = (List<OrderDetails>) session.getAttribute("cart");

        List<OrderDetails> cartRemove = (List<OrderDetails>) session.getAttribute("cartRemove");
        if(cartRemove != null){
            for (OrderDetails od : cartRemove){
                this.orderDetailsService.delete(od);
            }
        }

        List<OrderDetails> cartClone = new ArrayList<>();
        if(cart != null){
            for (OrderDetails ooo : cart){

                try {
                    this.orderDetailsService.save(ooo);
                } catch (Exception e){
                    Orders ors = this.ordersService.findById(ooo.getOrders().getId());
                    Products pds = this.productsService.findById(ooo.getProducts().getId()).get();
                    ooo.setOrders(ors);
                    ooo.setProducts(pds);
                    try {
                        this.orderDetailsService.save(ooo);
                    } catch (Exception ex){

                    }
                }
                cartClone.add(ooo);
            }
        }

        session.removeAttribute("cart");
        session.removeAttribute("cartRemove");

        if(!username.equals("anonymousUser")){
            if(!cartClone.isEmpty()){
                model.addAttribute("listSP", cartClone);
            } else {
                Users u = this.usersService.findById(username);
                for(Orders o : u.getUserInfo().getList()){
                    if(o.getStatusOrders()){
                        model.addAttribute("listSP", o.getList());
                        Double subtotal = 0.0;
                        for (OrderDetails ords : o.getList()){
                            subtotal = subtotal + (ords.getProducts().getPrice() * ords.getQuantity());
                        };
                        subtotal = (double) Math.round(subtotal * 100) / 100;
                        model.addAttribute("subtotal", subtotal);
                        break;
                    }
                }
            }
        } else {
            model.addAttribute("listSP", null);
        }


        return "/cart";
    }

    @GetMapping("/showLogin")
    public String showLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!username.equals("anonymousUser")){
            return "/index";
        }
        return "/login";
    }

    @GetMapping("/showSignup")
    public String showSignup(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!username.equals("anonymousUser")){
            return "/index";
        }
        return "/signup";
    }

    @GetMapping("/logout")
    public String logout(Model model, Pageable pageable){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "/login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Pageable pageable, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "id", required = false) String id){
        if (id != null && !id.isEmpty()) {
            model.addAttribute("user", this.usersService.findById(id));
        } else {
            Users u = new Users();
            model.addAttribute("user", u);
        }

        if (email != null && !email.isEmpty()) {
            Page<Users> listUsers = this.usersService.findByUserNameContains(email, pageable);
            model.addAttribute("listUsers", listUsers);
        } else {
            Page<Users> listUsers = this.usersService.findAll(pageable);
            model.addAttribute("listUsers", listUsers);
        }
        return "/dashboard";
    }

    @GetMapping("/dashboard-products")
    public String showDashboardProducts(Model model, Pageable pageable, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "id", required = false) Long id){
        if (id != null) {
            model.addAttribute("product", this.productsService.findById(id).get());
        } else {
            Products product = new Products();
            model.addAttribute("product", product);
        }

        if (name != null && !name.isEmpty()) {
            Page<Products> listProducts = this.productsService.findByNameContains(name, pageable);
            model.addAttribute("listProducts", listProducts);
        } else {
            Page<Products> listProducts = this.productsService.findAll(pageable);
            model.addAttribute("listProducts", listProducts);
        }
        return "/dashboard-products";
    }
}
