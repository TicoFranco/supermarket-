package com.supermarket.food_test.dtos.input;

import java.util.Set;

public record OrderDto(String email, Set<OrderItemDto> items) {
}
