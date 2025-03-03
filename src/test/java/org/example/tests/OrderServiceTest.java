package org.example.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void shouldProcessOrderSuccessfully() throws Exception {
        Order order = new Order(1, "Book", 3, 120);
        when(orderRepository.saveOrder(order)).thenReturn(1);

        String result = orderService.processOrder(order);
        assertEquals("Order processed successfully", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void shouldFailOrderProcessing() throws Exception {
        Order order = new Order(1, "Book", 3, 120);
        when(orderRepository.saveOrder(order)).thenThrow(new Exception("Database error"));

        String result = orderService.processOrder(order);

        assertEquals("Order processing failed", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void shouldCalculateTotalSuccessfully() {
        Order order = new Order(1, "Books", 3, 100);
        when(orderRepository.getOrderById(1)).thenReturn(Optional.of(order));

        double result = orderService.calculateTotal(1);

        assertEquals(300, result);
        verify(orderRepository, times(1)).getOrderById(1);
    }

    @Test
    void shouldReturnZeroWhenOrderNotFound() {
        when(orderRepository.getOrderById(1)).thenReturn(Optional.empty());

        double result = orderService.calculateTotal(1);

        assertEquals(0, result);
        verify(orderRepository, times(1)).getOrderById(1);
    }

    @Test
    void shouldReturnZeroWhenQuantityIsZero() {
        Order order = new Order(1, "Books", 0, 100);
        when(orderRepository.getOrderById(1)).thenReturn(Optional.of(order));

        double result = orderService.calculateTotal(1);

        assertEquals(0, result);
        verify(orderRepository, times(1)).getOrderById(1);
    }
}