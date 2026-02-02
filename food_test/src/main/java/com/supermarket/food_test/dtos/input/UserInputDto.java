package com.supermarket.food_test.dtos.input;

import com.supermarket.food_test.models.UserRole;

public record UserInputDto(String email, String password, String firstName, String lastName, String  address, UserRole userRole) {
}
