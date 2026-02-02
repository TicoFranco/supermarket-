package com.supermarket.food_test.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "food")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private long id;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodType type;
    @Column(nullable = false)
    private String url;

    public double getPrice(){
        return this.price;
    }

    public String getName(){
        return this.name;
    }

    public String getUrl(){
        return this.url;
    }


}
