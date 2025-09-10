package com.supermarket.food_test.dtos;

import java.util.Set;

public record OrderDTO(Set<FoodDTO> items, LoginDTO user) {
}
