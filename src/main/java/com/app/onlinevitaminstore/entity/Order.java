package com.app.onlinevitaminstore.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDateTime;
    @ManyToMany(targetEntity = Product.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<Product> products = new ArrayList<>();
    private double totalPrice;


}
