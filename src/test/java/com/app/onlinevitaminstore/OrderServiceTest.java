package com.app.onlinevitaminstore;

import com.app.onlinevitaminstore.model.Order;
import com.app.onlinevitaminstore.model.Product;
import com.app.onlinevitaminstore.model.Role;
import com.app.onlinevitaminstore.model.User;
import com.app.onlinevitaminstore.repository.OrderRepository;
import com.app.onlinevitaminstore.repository.UserRepository;
import com.app.onlinevitaminstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @MockBean
    OrderRepository orderRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void getAllOrdersTest() {
        Product product = Product.builder()
                .id(1L)
                .name("Football")
                .description("The description of football")
                .photo("football.jpg")
                .price(5000.0)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Order order = Order.builder()
                .id(1L)
                .orderDateTime(LocalDateTime.now())
                .products(productList)
                .totalPrice(5000.0)
                .build();

        List<Order> ordersList = new ArrayList<>();
        ordersList.add(order);
        when(orderRepository.findAll()).thenReturn(ordersList);
        assertEquals(ordersList, orderService.getAllOrders());
    }

    @Test
    public void getOrdersByUserTest() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        User user = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@gmail.com")
                .username("test_user")
                .address("Street 54, house #3, Islamabad, Pakistan")
                .password(bCryptPasswordEncoder.encode("password"))
                .role(role)
                .build();
        Product product = Product.builder()
                .id(1L)
                .name("Football")
                .description("The description of football")
                .photo("football.jpg")
                .price(5000.0)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Order order = Order.builder()
                .id(1L)
                .orderDateTime(LocalDateTime.now())
                .products(productList)
                .totalPrice(5000.0)
                .build();

        List<Order> ordersList = new ArrayList<>();
        ordersList.add(order);

        user.setOrders(ordersList);

        when(userRepository.findUserByUsername("test_user")).thenReturn(Optional.of(user));
        assertEquals(user.getOrders(), orderService.getOrdersByUser("test_user"));
    }

    @Test
    public void getProductsByOrderTest() {
        Product product = Product.builder()
                .id(1L)
                .name("Football")
                .description("The description of football")
                .photo("football.jpg")
                .price(5000.0)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Order order = Order.builder()
                .id(1L)
                .orderDateTime(LocalDateTime.now())
                .products(productList)
                .totalPrice(5000.0)
                .build();

        order.setProducts(productList);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertEquals(productList, orderService.getProductsByOrder(1L));
    }


    @Test
    public void getCustomerFromAnOrderTest() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        User user = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@gmail.com")
                .username("test_user")
                .address("Street 54, house #3, Islamabad, Pakistan")
                .password(bCryptPasswordEncoder.encode("password"))
                .role(role)
                .build();
        Product product = Product.builder()
                .id(1L)
                .name("Football")
                .description("The description of football")
                .photo("football.jpg")
                .price(5000.0)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Order order = Order.builder()
                .id(1L)
                .orderDateTime(LocalDateTime.now())
                .products(productList)
                .totalPrice(5000.0)
                .build();

        List<Order> ordersList = new ArrayList<>();
        ordersList.add(order);
        user.setOrders(ordersList);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(user, orderService.getCustomerFromAnOrder(1L));
    }
}
