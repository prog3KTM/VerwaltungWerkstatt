package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.order.Order;
import htw.prog3.KTM.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(DatabaseManager databaseManager) {
        this.orderRepository = new OrderRepository(databaseManager);
    }


    public int createOrder(Order order) {
        return orderRepository.saveOrder(order);
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(int id) {
        orderRepository.deleteById(id);
    }

    public void updateOrder(Order order) {
        orderRepository.updateOrder(order);
    }

    public void dropData() {
        orderRepository.dropData();
    }
}
