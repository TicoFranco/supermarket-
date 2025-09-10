package com.supermarket.food_test.dtos;

import com.supermarket.food_test.models.FoodType;

public record FoodDTO(String name, Double price, FoodType type , String url) {
}
