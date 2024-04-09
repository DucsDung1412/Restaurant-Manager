package vn.DungVipPro.java5_asm.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.DungVipPro.java5_asm.model.OrderDetails;
import vn.DungVipPro.java5_asm.model.Orders;
import vn.DungVipPro.java5_asm.model.Products;
import vn.DungVipPro.java5_asm.model.Users;
import vn.DungVipPro.java5_asm.service.OrdersService;
import vn.DungVipPro.java5_asm.service.ProductsService;
import vn.DungVipPro.java5_asm.service.UsersService;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MyRestController {
    private ProductsService productsService;
    private HttpSession session;
    private UsersService usersService;
    private OrdersService ordersService;

    @Autowired
    public MyRestController(ProductsService productsService, HttpSession session, UsersService usersService, OrdersService ordersService) {
        this.productsService = productsService;
        this.session = session;
        this.usersService = usersService;
        this.ordersService = ordersService;
    }

    @PostMapping("/add-to-cart")
    public List<Map<String, Object>> addToCart(Model model, @RequestBody Products p, Pageable pageable){
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
        List<Map<String, Object>> s = new ArrayList<>();
        long i = 0L;
        for (OrderDetails e : cart) {
            Map<String, Object> map = new HashMap<>();
            if(e.getId() != null){
                i = e.getId();
            }
            map.put("id", e.getId() == null ? i : e.getId());
            map.put("price", e.getProducts().getPrice());
            map.put("name", e.getProducts().getName());
            map.put("avatar", "../images/" + e.getProducts().getCategory() + "/" + e.getProducts().getSeason() + "/" + e.getProducts().getCountry() + "/" + e.getProducts().getImage());
            map.put("quantity", e.getQuantity());
            s.add(map);
            i++;
        }
        return s;
    }

    @PostMapping("/remove-to-cart")
    public String removeToCart(@RequestBody Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<OrderDetails> cart = (List<OrderDetails>) session.getAttribute("cart");
        List<OrderDetails> cartRemove = new ArrayList<>();
        Users u = this.usersService.findById(username);
        if (cart == null) {
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
}
