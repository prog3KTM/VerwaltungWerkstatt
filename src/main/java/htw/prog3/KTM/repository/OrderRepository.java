package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.generated.Tables;
import htw.prog3.KTM.generated.tables.records.OrdersRecord;
import htw.prog3.KTM.model.order.Order;
import htw.prog3.KTM.model.order.OrderStatus;
import htw.prog3.KTM.util.main;
import org.jooq.DSLContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        String serviceJobs = serviceIntegerstoString(order.getServicesJobIds());
        String repairJobs = serviceIntegerstoString(order.getRepairJobIds());
        if(order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        var record = databaseManager.getDSLContext().insertInto(Tables.ORDERS)
                .set(Tables.ORDERS.TOTAL, (float) order.getTotal())
                .set(Tables.ORDERS.STATUS, order.getStatus().toString())
                .set(Tables.ORDERS.ORDER_DATE, order.getOrderDate().toString())
                .set(Tables.ORDERS.SERVICEJOB_LIST, serviceJobs)
                .set(Tables.ORDERS.REPAIRJOB_LIST, repairJobs)
                .set(Tables.ORDERS.CUSTOMER_ID, order.getCustomerId())
                .returning(Tables.ORDERS.ID)
                .fetchOne();

        if (record != null) {
            return record.getId();
        }else {
            return -1;
        }
    }

    private Order mapToOrder(OrdersRecord record) {
        return new Order(
                record.getId(),
                getServiceIntegers(record.getServicejobList()),
                getServiceIntegers(record.getRepairjobList()),
                record.getTotal(),
                getOrderStatus(record.getStatus()),
                record.getCustomerId(),
                getOrderDate(record.getOrderDate())
        );
    }

    private OrderStatus getOrderStatus(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        return null;
    }

    private LocalDateTime getOrderDate(String date) {
        return LocalDateTime.parse(date);
    }

    private List<Integer> getServiceIntegers(String stringlist) {
        List<Integer> serviceIntegers = new ArrayList<>();
        if(!serviceIntegers.contains(",")) return serviceIntegers;
        String[] numbers = stringlist.split(",");
        for (String number : numbers) {
            serviceIntegers.add(Integer.parseInt(number));
        }
        return serviceIntegers;
    }

    private String serviceIntegerstoString(List<Integer> serviceIntegers) {
        String out = "";
        if(serviceIntegers.isEmpty()) { return out; }
        for(int i : serviceIntegers) {
            out = out + i + ",";
        }
        out = out.substring(0, out.length() - 1);
        return out;
    }

    public void updateOrder(Order order) {
        String serviceJobs = serviceIntegerstoString(order.getServicesJobIds());
        String repairJobs = serviceIntegerstoString(order.getRepairJobIds());

        int rowsAffected = databaseManager.getDSLContext()
                .update(Tables.ORDERS)
                .set(Tables.ORDERS.TOTAL, (float) order.getTotal())
                .set(Tables.ORDERS.STATUS, order.getStatus().toString())
                .set(Tables.ORDERS.ORDER_DATE, order.getOrderDate().toString())
                .set(Tables.ORDERS.SERVICEJOB_LIST, serviceJobs)
                .set(Tables.ORDERS.REPAIRJOB_LIST, repairJobs)
                .set(Tables.ORDERS.CUSTOMER_ID, order.getCustomerId())
                .where(Tables.ORDERS.ID.eq(order.getId()))
                .execute();
        System.out.println("Affected: " + rowsAffected);
    }

    private void createTableIfNotExists() {
        DSLContext create = databaseManager.getDSLContext();
        create.execute("CREATE TABLE IF NOT EXISTS Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_id INTEGER NOT NULL, " +
                "total REAL NOT NULL CHECK (total >= 0), " +
                "status TEXT NOT NULL CHECK (status IN ('PENDING', 'PROCESSING', 'COMPLETED', 'CANCELLED')), " +
                "order_date TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "serviceJob_list TEXT NOT NULL, " +
                "repairJob_list TEXT NOT NULL, " +
                "FOREIGN KEY (customer_id) REFERENCES Customer(id) ON DELETE CASCADE " +
                ");");
    }

    public void dropData() {
        databaseManager.getDSLContext().deleteFrom(Tables.ORDERS).execute();
    }
}
