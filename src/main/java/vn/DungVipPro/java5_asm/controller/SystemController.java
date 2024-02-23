package vn.DungVipPro.java5_asm.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.DungVipPro.java5_asm.model.*;
import vn.DungVipPro.java5_asm.service.*;

import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class SystemController {
    private ProductsService productsService;
    private HttpSession session;
    private UserInfoService userInfoService;
    private OrdersService ordersService;
    private AuthoritiesService authoritiesService;
    private UsersService usersService;
    private OrderDetailsService orderDetailsService;
    @Autowired
    public SystemController(OrderDetailsService orderDetailsService, AuthoritiesService authoritiesService, OrdersService ordersService, ProductsService productsService, UserInfoService userInfoService, UsersService usersService, HttpSession session) {
        this.userInfoService = userInfoService;
        this.session = session;
        this.productsService = productsService;
        this.ordersService = ordersService;
        this.authoritiesService = authoritiesService;
        this.usersService = usersService;
        this.orderDetailsService = orderDetailsService;
    }

    @PostMapping("/signup")
    @Transactional
    public String signup(@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("birthDay") Date birthDay, @RequestParam("cfpassword") String cfpassword){
        Users users = new Users(userName,"{noop}"+password,true);
        Authorities authorities = new Authorities(users,"ROLE_USER");
        UserInfo userInfo = new UserInfo(name, null, phone, null, birthDay, users);
        this.userInfoService.save(userInfo);
        this.authoritiesService.save(authorities);

        // Xóa users cho vui
//        Users u = new Users();
//        u.setUserName("zekoxpop@gmail.com");
//        u.setPassword("{noop}123");
//        u.setEnabled(true);
//        this.usersService.delete(u);
        return "/signup";
    }

    @PostMapping("/add-to-cart")
    public String test(Model model, @RequestBody Products p, Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Products pp = this.productsService.findById(p.getId()).get();
        List<OrderDetails> cart = (List<OrderDetails>) session.getAttribute("cart");

        Users u = this.usersService.findById(username);
        if (cart == null) {
            System.out.println("zo1");
            if(!username.equals("anonymousUser")){
                for(Orders o : u.getUserInfo().getList()){
                    if(o.getStatusOrders()){
                        cart = o.getList();
                        model.addAttribute("listSP", o.getList());
                        break;
                    }
                }
            }
        }

        Page<Products> listProducts = this.productsService.findAll(pageable);
        model.addAttribute("listProducts", listProducts);

        int b = 0;
        if(cart != null){
            for(OrderDetails od : cart){
                if(od.getProducts().getId() == p.getId()){
                    b++;
                    od.setQuantity(od.getQuantity() + 1);
                    System.out.println(od.getQuantity());
                    break;
                }
            }
        } else {
            cart = new ArrayList<>();
        }

        if(b == 0){
            Orders or = new Orders(null, true, true, "", true, u.getUserInfo());
            int cc = 0;
            for(Orders o : u.getUserInfo().getList()){
                if(o.getStatusOrders()){
                    or = o;
                    cc++;
                    break;
                }
            }
            if(cc == 0){
                this.ordersService.save(or);
            }
            OrderDetails od = new OrderDetails(1, or, pp);
            cart.add(od);
        }
        this.session.setAttribute("cart", cart);
        return "/menu";
    }

    @PostMapping("/remove-to-cart")
    public String removeToCart(@RequestBody Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<OrderDetails> cart = (List<OrderDetails>) session.getAttribute("cart");
        List<OrderDetails> cartRemove = new ArrayList<>();
        Users u = this.usersService.findById(username);
        if (cart == null) {
            System.out.println("zo1");
            if(!username.equals("anonymousUser")){
                for(Orders o : u.getUserInfo().getList()){
                    if(o.getStatusOrders()){
                        cart = o.getList();
                        break;
                    }
                }
            }
        }

        if (!cart.isEmpty()) {
            Iterator<OrderDetails> iterator = cart.iterator();
            while (iterator.hasNext()) {
                OrderDetails od = iterator.next();
                if (od.getId().equals(id)) { // Sử dụng phương thức equals() để so sánh ID
                    iterator.remove();
                    cartRemove.add(od);
                    break;
                }
            }
        }

        this.session.setAttribute("cart", cart);
        this.session.setAttribute("cartRemove", cartRemove);
        return "/index";
    }

    @GetMapping("/remove-products")
    public String removeProducts(Model model, @RequestParam("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        OrderDetails orderDetails = this.orderDetailsService.findById(id);
        this.orderDetailsService.delete(orderDetails);

        if(!username.equals("anonymousUser")){
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
        } else {
            model.addAttribute("listSP", null);
        }

        return "/cart";
    }

    @GetMapping("/search-users")
    public String searchUsers(RedirectAttributes ra, @RequestParam("email") String email){
        ra.addAttribute("email", email);
        return "redirect:/dashboard";
    }

    @GetMapping("/getUsers")
    public String getUsers(RedirectAttributes ra, @RequestParam("id") String username){
        ra.addAttribute("id", username);
        return "redirect:/dashboard";
    }

    @PostMapping("/save-users")
    public String saveUsers(@ModelAttribute("user") Users user){
        this.usersService.save(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/deleteUsers")
    public String deleteUsers(@RequestParam("id") String username){
        Users users = this.usersService.findById(username);
        this.usersService.delete(users);
        return "redirect:/dashboard";
    }

    @GetMapping("/search-products")
    public String searchProducts(RedirectAttributes ra, @RequestParam("name") String name){
        ra.addAttribute("name", name);
        return "redirect:/dashboard-products";
    }

    @GetMapping("/getProducts")
    public String getProducts(RedirectAttributes ra, @RequestParam("id") Long id){
        ra.addAttribute("id", id);
        return "redirect:/dashboard-products";
    }

    @PostMapping("/save-products")
    public String saveProducts(@ModelAttribute("product") Products products){
        this.productsService.save(products);
        return "redirect:/dashboard-products";
    }

    @GetMapping("/deleteProducts")
    public String deleteProducts(@RequestParam("id") Long id){
        Products products = this.productsService.findById(id).get();
        this.productsService.delete(products);
        return "redirect:/dashboard-products";
    }
}
