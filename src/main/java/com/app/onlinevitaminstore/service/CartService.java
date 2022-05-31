package com.app.onlinevitaminstore.service;

import com.app.onlinevitaminstore.model.Order;
import com.app.onlinevitaminstore.model.Product;
import com.app.onlinevitaminstore.model.User;
import com.app.onlinevitaminstore.repository.OrderRepository;
import com.app.onlinevitaminstore.repository.ProductRepository;
import com.app.onlinevitaminstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    HashMap<Long, Product> productHashMap = new HashMap<>();

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    public boolean addToCart(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            if (productHashMap.containsKey(product.get().getId())) {
                return true;
            } else {
                productHashMap.put(product.get().getId(), product.get());
                return false;
            }
        } else {
            throw new RuntimeException("Product not found against this id:" + productId);
        }
    }

    public List<Product> viewCart() {
        List<Product> products = new ArrayList<>();
        productHashMap.forEach(
                (key, value) -> products.add(value));
        return products;
    }

    public Order checkout() {
        List<Product> products = viewCart();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            Order order = new Order();
            order.setOrderDateTime(LocalDateTime.now());
            order.setProducts(products);
            double totalPrice = 0.0;
            for (Product p : products
            ) {
                totalPrice = totalPrice + p.getPrice();
            }
            order.setTotalPrice(totalPrice);
            Order savedOrder = orderRepository.save(order);
            user.get().getOrders().add(savedOrder);
            userRepository.save(user.get());
            productHashMap.clear();
            return savedOrder;
        } else {
            throw new RuntimeException("User doesn't exists in the database with this username");
        }
    }
}
