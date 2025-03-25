package htw.prog3.KTM.model.order;

import htw.prog3.KTM.controller.CustomerController;
import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.model.jobs.Service;
import htw.prog3.KTM.util.main;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Order {

    private static final double MWST = 0.19;

    private int id;
    private Set<Integer> services;
    private double total;
    private OrderStatus status;
    private int customerId;
    private LocalDateTime orderDate;

    public Order(int id, Set<Integer> services, double total, OrderStatus status, int customerId, LocalDateTime orderDate) {
        this.id = id;
        this.services = services;
        this.total = total;
        this.status = status;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(Set<Integer> services, double total, OrderStatus status, int customerId) {
        this.services = services;
        this.total = total;
        this.status = status;
        this.customerId = customerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public int getId() {
        return id;
    }

    public Set<Integer> getServicesIds() {
        return services;
    }

    public double getTotal() {
        return total;
    }

    public double getTotalWithTaxes() {
        return total+calculateTax();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Optional<Customer> getCustomer() {
        CustomerController customerController = main.getAppConfig().getCustomerController();
        try {
            return customerController.getCustomerById(customerId);
        } catch (SQLException e) {
            return null;
        }
    }

    private double calculateTax() {
        return MWST * total;
    }

    public List<Service> getServices() {
        List<Service> services = new ArrayList<>();
        for(Integer serviceId : this.services) {
            main.getAppConfig().getServiceJobController()
                    .getServiceJobById(serviceId)
                    .ifPresent(services::add);
            main.getAppConfig().getRepairJobController()
                    .getRepairJobById(serviceId)
                    .ifPresent(services::add);
        }
        return services;
    }

    public void addService(int serviceId) {
        this.services.add(serviceId);
    }
}
