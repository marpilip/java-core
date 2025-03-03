package org.example.tests;

import java.util.Optional;

public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        try {
            int orderId = orderRepository.saveOrder(order);
            return "Order processed successfully";
        } catch (Exception e) {
            return "Order processing failed";
        }
    }

    public double calculateTotal(int id) {
        Optional<Order> orderOptional = orderRepository.getOrderById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return order.getTotalPrice();
        } else {
            return 0.0;
        }
    }
}
