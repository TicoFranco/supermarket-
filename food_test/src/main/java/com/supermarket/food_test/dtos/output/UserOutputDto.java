package com.supermarket.food_test.dtos.output;

import com.supermarket.food_test.models.Order;
import com.supermarket.food_test.models.UserRole;

import java.util.Set;

public record UserOutputDto(String email, String firstName, String lastName, Set<Order> orders, String userRole) {}
