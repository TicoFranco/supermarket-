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

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Purchase> items;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @DateTimeFormat(pattern = "dd/MM/yyyy kk:mm")
    private Date createdAt;

    public Order(Set<Purchase> items,User user,Date time){
        this.items = items;
        this.total = items.stream().mapToDouble(Purchase::getValue).sum();
        this.user = user;
        this.createdAt = time;
    }

    public Order(User user,Date time){
        this.user = user;
        this.createdAt = time;
    }

    public Order(){}

    public void setTotal(double total){
        this.total = total;
    }

    public void setItems(Set<Purchase> items){
        this.items = items;
        this.total = items.stream().mapToDouble(Purchase::getValue).sum();
    }

    public UUID getId(){
        return this.id;
    }

    public Date getDate(){
        return this.createdAt;
    }

    public Double getTotal(){
        return this.total;
    }
}
