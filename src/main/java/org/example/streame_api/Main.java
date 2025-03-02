package org.example.streame_api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Customer> customers = getCustomers();

        // Задание 1
        List<Product> booksOver100 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Books".equals(product.getCategory())
                        && product.getPrice().compareTo(new BigDecimal("100")) > 0)
                .distinct()
                .toList();

        System.out.println("Books over 100: " + booksOver100);

        // Задание 2
        List<Order> childrenProductsOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream().anyMatch(product ->
                        "Children's products".equals(product.getCategory())))
                .toList();

        System.out.println("Orders with Children's products: " + childrenProductsOrders);

        // Задание 3
        BigDecimal toysDiscountSum = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Toys".equals(product.getCategory()))
                .map(product -> product.getPrice().multiply(new BigDecimal("0.9")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Toys discount sum: " + toysDiscountSum);

        // Задание 4
        List<Product> level2Products = customers.stream()
                .filter(customer -> customer.getLevel() == 2)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021, 1, 31))
                        && order.getOrderDate().isBefore(LocalDate.of(2021, 4, 2)))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .toList();

        System.out.println("Level 2 products between dates: " + level2Products);

        //Задание 5
        List<Product> top2CheapestBooks = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Books".equals(product.getCategory()))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .toList();

        System.out.println("Top 2 cheapest books: " + top2CheapestBooks);

        // Задание 6
        List<Order> latest3Orders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();

        System.out.println("Latest 3 orders: " + latest3Orders);

        // Задание 7
        List<Product> productsFromMarch15 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getDeliveryDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println("Order ID: " + order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .toList();

        System.out.println("Products from orders on 15 March 2021: " + productsFromMarch15);

        // Задание 8
        BigDecimal february2021Total = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().getMonthValue() == 2
                        && order.getOrderDate().getYear() == 2021)
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total for February 2021: " + february2021Total);

        // Задание 9
        OptionalDouble averagePaymentMarch14 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 14)))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(product -> product.getPrice().doubleValue())
                .average();

        System.out.println("Average payment on 14 March 2021: "
                + (averagePaymentMarch14.isPresent() ? averagePaymentMarch14.getAsDouble() : "No orders"));

        // Задание 10
        DoubleSummaryStatistics booksStatistics = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .mapToDouble(product -> product.getPrice().doubleValue())
                .summaryStatistics();

        System.out.println("Books statistics: " + booksStatistics);

        // Задание 11
        Map<Long, Integer> orderProductCount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(Order::getId,
                        order -> order.getProducts().size(),
                        Integer::sum));

        System.out.println("Order product count: " + orderProductCount);

        // Задание 12
        Map<Customer, Set<Order>> customerOrdersMap = customers.stream()
                .collect(Collectors.toMap(customer -> customer, Customer::getOrders));

        System.out.println("Customer orders map: " + customerOrdersMap);

        // Задание 13
        Map<Order, BigDecimal> orderTotalMap = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getProducts().stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add),
                        (existingValue, newValue) -> existingValue));

        System.out.println("Order total map: " + orderTotalMap);

        // Задание 14
        Map<String, List<String>> categoryProductNamesMap = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())));

        System.out.println("Category product names map: " + categoryProductNamesMap);

        // Задание 15
        Map<String, Product> mostExpensiveProductByCategory = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toMap(Product::getCategory, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Product::getPrice))));

        System.out.println("Most expensive product by category: " + mostExpensiveProductByCategory);
    }

    private static List<Customer> getCustomers() {
        Product book1 = new Product(1L, "Book1", "Books", new BigDecimal("150.00"));
        Product book2 = new Product(2L, "Book2", "Books", new BigDecimal("90.00"));
        Product toy1 = new Product(3L, "Toy1", "Toys", new BigDecimal("50.00"));
        Product toy2 = new Product(4L, "Toy2", "Toys", new BigDecimal("100.00"));
        Product phone1 = new Product(5L, "Phone1", "Phones", new BigDecimal("750.00"));
        Product phone2 = new Product(6L, "Phone2", "Phones", new BigDecimal("1200.00"));
        Product childrenProduct1 = new Product(7L, "Children's Product1", "Children's products",
                new BigDecimal("75.00"));
        Product childrenProduct2 = new Product(8L, "Children's Product2", "Children's products",
                new BigDecimal("120.00"));

        Order order1 = new Order(1L, LocalDate.of(2020, 2, 15),
                LocalDate.of(2021, 2, 20), "Delivered",
                new HashSet<>(Arrays.asList(book1, phone1)));
        Order order2 = new Order(2L, LocalDate.of(2023, 3, 14),
                LocalDate.of(2021, 3, 18), "Delivered",
                new HashSet<>(Arrays.asList(toy1, book2)));
        Order order3 = new Order(3L, LocalDate.of(2021, 3, 15),
                LocalDate.of(2021, 3, 15), "Delivered",
                new HashSet<>(Arrays.asList(toy2, book1)));
        Order order4 = new Order(4L, LocalDate.of(2021, 4, 1),
                LocalDate.of(2021, 4, 5), "Delivered",
                new HashSet<>(Arrays.asList(phone2, toy2)));
        Order order5 = new Order(5L, LocalDate.of(2021, 2, 10),
                LocalDate.of(2021, 2, 15), "Delivered",
                new HashSet<>(Arrays.asList(phone1, phone2)));
        Order order6 = new Order(6L, LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 2, 15), "Delivered",
                new HashSet<>(Arrays.asList(childrenProduct2, childrenProduct1)));

        Customer customer1 = new Customer(1L, "John Doe", 1L,
                new HashSet<>(Arrays.asList(order1, order2)));
        Customer customer2 = new Customer(2L, "Jane Doe", 2L,
                new HashSet<>(Arrays.asList(order3, order4)));
        Customer customer3 = new Customer(3L, "Alice", 1L,
                new HashSet<>(List.of(order5)));
        Customer customer4 = new Customer(4L, "Bob", 2L,
                new HashSet<>(Arrays.asList(order1, order3)));
        Customer customer5 = new Customer(5L, "Charlie", 1L,
                new HashSet<>(Arrays.asList(order2, order4, order5)));
        Customer customer6 = new Customer(6L, "Anna", 1L,
                new HashSet<>(Arrays.asList(order6, order1)));

        return Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6);
    }
}
