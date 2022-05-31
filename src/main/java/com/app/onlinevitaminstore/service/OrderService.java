package com.app.onlinevitaminstore.service;

import com.app.onlinevitaminstore.model.Order;
import com.app.onlinevitaminstore.model.Product;
import com.app.onlinevitaminstore.model.User;
import com.app.onlinevitaminstore.repository.OrderRepository;
import com.app.onlinevitaminstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return user.get().getOrders();
        } else {
            throw new RuntimeException("User doesn't exists in the database against this username");
        }
    }

    public List<Product> getProductsByOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get().getProducts();
        } else {
            throw new RuntimeException("There is no order against this order Id: " + orderId);
        }
    }

    public User getCustomerFromAnOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            List<User> users = userRepository.findAll();
            User foundUser = null;
            for (User user : users
            ) {
                for (Order ord : user.getOrders()
                ) {
                    if (ord.equals(order.get())) {
                        break;
                    }
                }
                foundUser = user;
            }
            return foundUser;
        } else {
            throw new RuntimeException("There is no order against this order Id: " + orderId);
        }
    }
}
