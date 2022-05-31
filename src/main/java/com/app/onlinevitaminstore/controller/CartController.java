package com.app.onlinevitaminstore.controller;

import com.app.onlinevitaminstore.model.Order;
import com.app.onlinevitaminstore.model.Product;
import com.app.onlinevitaminstore.service.CartService;
import com.app.onlinevitaminstore.service.ProductService;
import com.app.onlinevitaminstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable(name = "id") Long productId, Model model) {
        boolean alreadyPresent = cartService.addToCart(productId);
        if (alreadyPresent) {
            model.addAttribute("alreadyInCart", true);
        } else {
            model.addAttribute("addedToCart", true);
        }
        model.addAttribute("productsList", productService.getAllProducts());
        model.addAttribute("cartItems", cartService.viewCart());
        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        List<Product> productsInCart = cartService.viewCart();
        Double totalPrice = 0.0;
        for (Product product : productsInCart
        ) {
            totalPrice = totalPrice + product.getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", cartService.viewCart());
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        if(cartService.viewCart().isEmpty()){
            model.addAttribute("emptyCart",true);
            return "cart";
        }
        Order order = cartService.checkout();
        model.addAttribute("order", order);
        return "checkout";
    }

}
