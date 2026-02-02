package com.supermarket.food_test.dtos.input;

import com.supermarket.food_test.models.FoodType;

public record FoodDto(String name, Double price, FoodType type , String url) {
}
