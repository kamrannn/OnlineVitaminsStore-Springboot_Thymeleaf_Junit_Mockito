package com.app.onlinevitaminstore.controller;

import com.app.onlinevitaminstore.model.Product;
import com.app.onlinevitaminstore.service.CartService;
import com.app.onlinevitaminstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @GetMapping("/new")
    public String getProductUploadForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-vitamins";
    }

    @PostMapping("/save")
    public String saveUser(Product product, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        productService.saveProduct(product, multipartFile);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/all")
    public String productsList(Model model) {
        model.addAttribute("productsList", productService.getAllProducts());
        model.addAttribute("cartItems", cartService.viewCart());
        return "customer-dashboard";
    }

    @GetMapping("/getById/{id}")
    public String getProductById(Model model, @PathVariable(name = "id") Long productId) {
        model.addAttribute("product", productService.getProductById(productId));
        return "view-vitamin";
    }

    @GetMapping("/deleteById/{id}")
    public String deleteProductById(@PathVariable(name = "id") Long productId) {
//        productService.deleteProduct(productId);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/update/{id}")
    public String updateProduct(Model model, @PathVariable(name = "id") Long productId) {
        model.addAttribute("productId", productId);
        model.addAttribute("product", productService.getProductById(productId));
        return "update-vitamin";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable(name = "id") Long productId, Product product, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        product.setId(productId);
        productService.saveProduct(product, multipartFile);
        return "redirect:/admin/dashboard";
    }
}
