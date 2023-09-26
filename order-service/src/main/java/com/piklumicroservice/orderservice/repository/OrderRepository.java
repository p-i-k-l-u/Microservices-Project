package com.piklumicroservice.orderservice.repository;

import com.piklumicroservice.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
