package com.supermarket.food_test.dtos;

import com.supermarket.food_test.models.UserRole;

public record UserDTO(String email, String password, String firstName, String lastName, String  address, UserRole userRole) {
}
