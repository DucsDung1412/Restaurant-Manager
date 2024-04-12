package vn.DungVipPro.java5_asm.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserInfoService userInfoService;
    private AuthoritiesService authoritiesService;
    private UsersService usersService;
    private OrderDetailsService orderDetailsService;
    @Autowired
    public SystemController(OrderDetailsService orderDetailsService, AuthoritiesService authoritiesService, OrdersService ordersService, ProductsService productsService, UserInfoService userInfoService, UsersService usersService, HttpSession session) {
        this.userInfoService = userInfoService;
        this.productsService = productsService;
        this.authoritiesService = authoritiesService;
        this.usersService = usersService;
        this.orderDetailsService = orderDetailsService;
    }

    @PostMapping("/signup")
    @Transactional
    public String signup(@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("birthDay") Date birthDay, @RequestParam("cfpassword") String cfpassword){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String enCode = bCryptPasswordEncoder.encode(password);
        Users users = new Users(userName,"{bcrypt}"+enCode,true);
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

    @GetMapping("/remove-products")
    public String removeProducts(@RequestParam("id") Long id){
        OrderDetails orderDetails = this.orderDetailsService.findById(id);
        if(orderDetails.getQuantity() < 2){
            this.orderDetailsService.delete(orderDetails);
        } else {
            int quantity = orderDetails.getQuantity() - 1;
            orderDetails.setQuantity(quantity);
            this.orderDetailsService.save(orderDetails);
        }

        return "redirect:/shopping-cart";
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
    public String saveUsers(@ModelAttribute("user") Users user, @RequestParam("exampleRadios") String exampleRadios){
        try {
            Users u = usersService.findById(user.getUserName());
            u.getAuthorities().setAuthority(exampleRadios);
            user.setAuthorities(u.getAuthorities());
        } catch (Exception e){
            Authorities authorities = new Authorities(user, exampleRadios);
            user.setAuthorities(authorities);
        }
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
