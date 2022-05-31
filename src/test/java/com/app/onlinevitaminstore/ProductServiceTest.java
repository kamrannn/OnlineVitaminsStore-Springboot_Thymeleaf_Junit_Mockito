package com.app.onlinevitaminstore;

import com.app.onlinevitaminstore.entity.Product;
import com.app.onlinevitaminstore.dao.ProductRepository;
import com.app.onlinevitaminstore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    public void getProductByIdTest() {
        Product product = Product.builder()
                .id(1L)
                .name("Football")
                .description("The description of football")
                .photo("football.jpg")
                .price(5000.0)
                .build();
        when(productRepository.getById(1L)).thenReturn(product);
        assertEquals(product, productService.getProductById(1L));
    }

    @Test
    public void getAllProductsTest() {
        Product product = Product.builder()
                .id(1L)
                .name("Football")
                .description("The description of football")
                .photo("football.jpg")
                .price(5000.0)
                .build();

        List<Product> productList = new ArrayList<>();

        productList.add(product);
        when(productRepository.getAllProducts()).thenReturn(productList);
        assertEquals(productList, productService.getAllProducts());
    }
}
