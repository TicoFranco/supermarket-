package com.supermarket.food_test.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private UUID id;

    @Column(nullable = false)
    private double total;

    @ManyToMany
    @JoinTable(
            name = "order_food",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private Set<Food> items;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @DateTimeFormat(pattern = "dd/MM/yyyy kk:mm")
    private Date createdAt;

    public Order(Set<Food> items,User user,Date time){
        this.items = items;
        this.total = items.stream().mapToDouble(Food::getPrice).sum();
        this.user = user;
        this.createdAt = time;
    }

    public Order(){}
}
