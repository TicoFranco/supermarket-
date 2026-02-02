package com.supermarket.food_test.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id",nullable = false)
    private Food food;

    private int quantity;

    private double value;

    public Purchase(){}

    public Purchase(Order order,Food food,int quantity){
        this.order = order;
        this.food = food;
        this.quantity = quantity;
        this.value = food.getPrice()*quantity;
    }

    public double getValue(){
        return this.value;
    }

    public String getFoodName(){
        return this.food.getName();
    }

    public String getFoodUrl(){
        return this.food.getUrl();
    }

    public int getQuantity(){
        return this.quantity;
    }

}
