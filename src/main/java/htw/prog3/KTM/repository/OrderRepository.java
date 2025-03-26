package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.generated.Tables;
import htw.prog3.KTM.generated.tables.records.OrdersRecord;
import htw.prog3.KTM.model.order.Order;
import htw.prog3.KTM.model.order.OrderStatus;
import htw.prog3.KTM.util.main;
import org.jooq.DSLContext;

import java.time.LocalDateTime;
import java.util.*;

public class OrderRepository {

    private final DatabaseManager databaseManager;

    public OrderRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        createTableIfNotExists();
    }

    public Optional<Order> findById(int id) {
        OrdersRecord record = databaseManager.getDSLContext().selectFrom(Tables.ORDERS)
                .where(Tables.ORDERS.ID.eq(id))
                .fetchOne();

        return Optional.ofNullable(record)
                .map(this::mapToOrder);
    }

    public void deleteById(int id) {
        int rowsDeleted = databaseManager.getDSLContext().deleteFrom(Tables.ORDERS)
                .where(Tables.ORDERS.ID.eq(id))
                .execute();
        if (rowsDeleted == 0) {
            throw new RuntimeException("Order with ID " + id + " not found.");
        }
    }

    public List<Order> findAll() {
        return databaseManager.getDSLContext().selectFrom(Tables.ORDERS)
                .fetch()
                .map(this::mapToOrder);
    }

    public int saveOrder(Order order) {
        try {
            String services = serviceIntegerstoString(order.getServicesIds());
            if(order.getOrderDate() == null) {
                order.setOrderDate(LocalDateTime.now());
            }
            
            // Use direct SQL approach for maximum compatibility
            String sql = "INSERT INTO orders (customer_id, total, status, order_date, service_list) " +
                      "VALUES (?, ?, ?, ?, ?) RETURNING id";
                      
            var result = databaseManager.getDSLContext().fetch(
                sql, 
                order.getCustomerId(),
                (float) order.getTotal(),
                order.getStatus().toString(),
                order.getOrderDate().toString(),
                services
            );
            
            if (!result.isEmpty()) {
                Object idValue = result.getValue(0, "id");
                return idValue != null ? Integer.parseInt(idValue.toString()) : -1;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private Order mapToOrder(OrdersRecord record) {
        try {
            // Use direct approach to extract values
            int id = record.getValue("id", Integer.class);
            float total = record.getValue("total", Float.class);
            int customerId = record.getValue("customer_id", Integer.class);
            String status = record.getValue("status", String.class);
            String serviceList = record.getValue("service_list", String.class);
            // Get other fields directly using SQL
            var result = databaseManager.getDSLContext().fetch(
                "SELECT order_date FROM ORDERS WHERE id = ?", id
            );
            
            String orderDateStr = "";
            
            if (!result.isEmpty()) {
                Object orderDateObj = result.getValue(0, "order_date");
                
                orderDateStr = orderDateObj != null ? orderDateObj.toString() : "";
            }
            
            return new Order(
                id,
                getServiceIntegers(serviceList),
                total,
                getOrderStatus(status),
                customerId,
                getOrderDate(orderDateStr)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new Order(0, new HashSet<>(), 0, OrderStatus.PENDING, 0, LocalDateTime.now());
        }
    }

    private OrderStatus getOrderStatus(String status) {
        if (status == null || status.isEmpty()) {
            return OrderStatus.PENDING;
        }
        
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        return OrderStatus.PENDING;
    }

    private LocalDateTime getOrderDate(String date) {
        if (date == null || date.isEmpty()) {
            return LocalDateTime.now();
        }
        
        try {
            return LocalDateTime.parse(date);
        } catch (Exception e) {
            System.err.println("Warning: Could not parse date: " + date);
            return LocalDateTime.now();
        }
    }

    private Set<Integer> getServiceIntegers(String stringlist) {
        Set<Integer> serviceIntegers = new HashSet<>();
        if(stringlist == null || stringlist.isEmpty()) return serviceIntegers;
        if(!stringlist.contains(",")) {
            serviceIntegers.add(Integer.parseInt(stringlist));
            return serviceIntegers;
        }
        String[] numbers = stringlist.split(",");
        for (String number : numbers) {
            try {
                serviceIntegers.add(Integer.parseInt(number.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Warning: Could not parse integer: " + number);
            }
        }
        return serviceIntegers;
    }

    private String serviceIntegerstoString(Set<Integer> serviceIntegers) {
        String out = "";
        if(serviceIntegers == null || serviceIntegers.isEmpty()) { return out; }
        for(int i : serviceIntegers) {
            out = out + i + ",";
        }
        out = out.substring(0, out.length() - 1);
        return out;
    }

    public void updateOrder(Order order) {
        try {
            String serviceIds = serviceIntegerstoString(order.getServicesIds());
            
            // Use direct SQL approach for maximum compatibility
            String sql = "UPDATE ORDERS SET " +
                      "customer_id = ?, total = ?, status = ?, " +
                      "order_date = ?, service_list = ? " +
                      "WHERE id = ?";
                      
            int rowsAffected = databaseManager.getDSLContext().execute(
                sql, 
                order.getCustomerId(),
                (float) order.getTotal(),
                order.getStatus().toString(),
                order.getOrderDate().toString(),
                serviceIds,
                order.getId()
            );
            
            System.out.println("Affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        DSLContext create = databaseManager.getDSLContext();
        create.execute("CREATE TABLE IF NOT EXISTS ORDERS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_id INTEGER NOT NULL, " +
                "total REAL NOT NULL CHECK (total >= 0), " +
                "status TEXT NOT NULL CHECK (status IN ('PENDING', 'PROCESSING', 'COMPLETED', 'CANCELLED')), " +
                "order_date TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "service_list TEXT NOT NULL, " +
                "FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id) ON DELETE CASCADE " +
                ");");
    }

    public void dropData() {
        databaseManager.getDSLContext().deleteFrom(Tables.ORDERS).execute();
    }
}
