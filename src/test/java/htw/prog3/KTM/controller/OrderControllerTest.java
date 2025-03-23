package htw.prog3.KTM.controller;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.order.Order;
import htw.prog3.KTM.model.order.OrderStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderControllerTest {

    private static OrderController orderController;

    @BeforeAll
    static void setUp() {
        DatabaseManager databaseManager = new DatabaseManager("test.db");
        AppConfig appConfig = new AppConfig();
        orderController = appConfig.getOrderController();
        orderController.dropData();
    }

    private static Order order_testdata = new Order(Arrays.asList(1, 3), Arrays.asList(1, 3, 9), 10.4, OrderStatus.PENDING, 5);

    @Test
    void createOrder() {
        List<Integer> numberlistservice = new ArrayList<>();
        numberlistservice.add(1);
        List<Integer> numberlistrepair = new ArrayList<>();
        numberlistservice.add(1);
        numberlistservice.add(2);
        Order order = new Order(numberlistrepair, numberlistservice, 10.4, OrderStatus.PENDING, 5);
        orderController.createOrder(order);
    }

    @Test
    void getOrderById() {
        orderController.dropData();
        int id = orderController.createOrder(order_testdata);

        Optional<Order> order = orderController.getOrderById(id);
        assertEquals(5, order.get().getCustomerId());
    }

    @Test
    void getAllOrders() {
        orderController.dropData();
        orderController.createOrder(order_testdata);

        List<Order> orders = orderController.getAllOrders();
        assertEquals(1, orders.size());
    }

    @Test
    void updateOrderById() {
        orderController.dropData();
        int id = orderController.createOrder(order_testdata);

        Order newOrder = order_testdata;
        newOrder.setStatus(OrderStatus.CANCELLED);
        newOrder.setId(id);
        orderController.updateOrder(newOrder);
        Optional<Order> order = orderController.getOrderById(id);
        assertEquals(OrderStatus.CANCELLED, order.get().getStatus());
    }

    @Test
    void deleteOrderById() {
        orderController.dropData();
        int id = orderController.createOrder(order_testdata);

        orderController.deleteOrderById(id);
        Optional<Order> order = orderController.getOrderById(id);
        assertEquals(false, order.isPresent());
    }
}