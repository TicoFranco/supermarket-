package com.supermarket.food_test.repositories;

import com.supermarket.food_test.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order,UUID> {
}
