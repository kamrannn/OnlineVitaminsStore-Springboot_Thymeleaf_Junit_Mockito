package com.app.onlinevitaminstore.controller;

import com.app.onlinevitaminstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;


    @GetMapping("/getById/{id}")
    public String getProductsByOrderId(@PathVariable(name = "id") Long orderId, Model model) {
        model.addAttribute("productsList", orderService.getProductsByOrder(orderId));
        return "ordered-vitamins";
    }

    @GetMapping("/getCustomerByOrderId/{id}")
    public String getCustomerByOrder(@PathVariable(name = "id") Long orderId, Model model) {
        model.addAttribute("customer", orderService.getCustomerFromAnOrder(orderId));
        return "self-orders";
    }
}
