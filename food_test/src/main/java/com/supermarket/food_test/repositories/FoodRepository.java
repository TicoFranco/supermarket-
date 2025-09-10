package com.supermarket.food_test.repositories;

import com.supermarket.food_test.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {

    @Query("select f from Food f where f.name = :name")
    Optional<Food> findByName(String name);
}
