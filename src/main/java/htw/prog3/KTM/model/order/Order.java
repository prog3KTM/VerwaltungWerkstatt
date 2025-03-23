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
    private Set<Integer> servicesJobIds;
    private Set<Integer> repairJobIds;
    private double total;
    private OrderStatus status;
    private int customerId;
    private LocalDateTime orderDate;

    public Order(int id, Set<Integer> servicesJobIds, Set<Integer> repairJobIds, double total, OrderStatus status, int customerId, LocalDateTime orderDate) {
        this.id = id;
        this.servicesJobIds = servicesJobIds;
        this.repairJobIds = repairJobIds;
        this.total = total;
        this.status = status;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(Set<Integer> servicesJobIds, Set<Integer> repairJobIds, double total, OrderStatus status, int customerId) {
        this.servicesJobIds = servicesJobIds;
        this.repairJobIds = repairJobIds;
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

    public Set<Integer> getRepairJobIds() {
        return repairJobIds;
    }

    public Set<Integer> getServicesJobIds() {
        return servicesJobIds;
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
        List<Integer> toRemove = new ArrayList<>();
        boolean update = false;
        for(Integer serviceId : servicesJobIds) {
            main.getAppConfig().getServiceJobController()
                    .getServiceJobById(serviceId)
                    .ifPresentOrElse(services::add, () -> toRemove.add(serviceId));
        }
        if(!toRemove.isEmpty()) {
            this.servicesJobIds.removeAll(toRemove);
            update = true;
            toRemove.clear();
        }

        for(Integer serviceId : repairJobIds) {
            //TODO: add repairjob finder
        }
        if(!toRemove.isEmpty()) {
            this.repairJobIds.removeAll(toRemove);
            update = true;
            toRemove.clear();
        }
        if(update) {
            main.getAppConfig().getOrderController().updateOrder(this);
        }
        return services;
    }

    public void addRepairJob(int serviceId) {
        this.repairJobIds.add(serviceId);
    }

    public void addServicesJob(int serviceId) {
        this.servicesJobIds.add(serviceId);
    }
}
