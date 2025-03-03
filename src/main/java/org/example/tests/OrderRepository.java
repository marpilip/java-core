package org.example.tests;

import java.util.Optional;

public interface OrderRepository {
    int saveOrder(Order order) throws Exception;
    Optional<Order> getOrderById(int id);
}
