package com.app.onlinevitaminstore.controller;

import com.app.onlinevitaminstore.service.OrderService;
import com.app.onlinevitaminstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @GetMapping("/user/orders")
    public String orders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("ordersList", orderService.getOrdersByUser(username));
        return "orders";
    }

    @GetMapping("/admin/dashboard")
    public String productsList(Model model) {
        model.addAttribute("productsList", productService.getAllProducts());
        return "admin-dashboard";
    }

    @GetMapping("/admin/allOrders")
    public String getAllOrders(Model model) {
        model.addAttribute("ordersList", orderService.getAllOrders());
        return "orders";
    }
}
