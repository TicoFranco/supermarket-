package com.supermarket.food_test.repositories;

import com.supermarket.food_test.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    @Query(value = "select * from purchase where order_id = :id",nativeQuery = true)
    List<Purchase> purchasesByOrder(@Param("id") UUID id);
}
