package com.app.onlinevitaminstore.controller;

import com.app.onlinevitaminstore.service.OrderService;
import com.app.onlinevitaminstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @GetMapping("/dashboard")
    public String productsList(Model model) {
        model.addAttribute("productsList", productService.getAllProducts());
        return "admin-dashboard";
    }

    @GetMapping("/allOrders")
    public String getAllOrders(Model model) {
        model.addAttribute("ordersList", orderService.getAllOrders());
        return "orders";
    }
}
