package htw.prog3.KTM.repository;

import htw.prog3.KTM.generated.tables.records.CustomerRecord;
import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.database.DatabaseManager;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static htw.prog3.KTM.generated.Tables.CUSTOMER;


public class CustomerRepository {

    private final DatabaseManager databaseManager;

    public CustomerRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    private Connection getConnection() throws SQLException {
        return databaseManager.getConnection();
    }


    public List<Customer> findAll() {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            Result<Record> result = create.select().from(CUSTOMER).fetch();

            return result.map(r -> {
                Integer id = r.getValue(CUSTOMER.ID);
                String name = r.getValue(CUSTOMER.NAME);
                String address = r.getValue(CUSTOMER.ADDRESS);
                String phone = r.getValue(CUSTOMER.PHONE);
                return new Customer(id, name, address, phone);
            });
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der Kunden", e);
        }
    }


    public Optional<Customer> findById(int id) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            CustomerRecord customerRecord = create.selectFrom(CUSTOMER).where(CUSTOMER.ID.eq(id)).fetchOne();

            return Optional.ofNullable(customerRecord)
                    .map(record -> new Customer(record.getId(), record.getName(), record.getAddress(), record.getPhone()));
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen des Kunden", e);
        }
    }


    public void save(Customer customer) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            CustomerRecord customerRecord = create.newRecord(CUSTOMER, customer);
            create.executeInsert(customerRecord);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern des Kunden", e);
        }
    }


    public void update(Customer customer) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            CustomerRecord kundeRecord = create.newRecord(CUSTOMER, customer);
            create.executeUpdate(kundeRecord);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Kunden", e);
        }
    }


    public void delete(int id) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            int deletedCount = create.deleteFrom(CUSTOMER)
                    .where(CUSTOMER.ID.eq(id))
                    .execute();
            if (deletedCount == 0) {
                throw new RuntimeException("Kunde mit ID " + id + " nicht gefunden.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen des Kunden", e);
        }
    }
}
