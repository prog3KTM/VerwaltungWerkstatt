package htw.prog3.KTM.repository;

import htw.prog3.KTM.model.Auto.AutoStatus;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.generated.tables.records.AutoRecord;
import htw.prog3.KTM.generated.Tables;
import htw.prog3.KTM.model.Auto.Auto;
import org.jooq.DSLContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AutoRepository {

    private final DatabaseManager databaseManager;

    public AutoRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    private DSLContext getDSLContext() throws SQLException {
        return databaseManager.getDSLContext();
    }

    // Retrieve all cars
    public List<Auto> findAll() {
        try {
            DSLContext create = getDSLContext();
            return create.selectFrom(Tables.AUTO).fetch().map(this::mapToAuto);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all cars", e);
        }
    }

    // Save a new car
    public void save(Auto auto) {
        try {
            DSLContext create = getDSLContext();
            AutoRecord autoRecord = create.newRecord(Tables.AUTO, auto);
            autoRecord.store();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving the car", e);
        }
    }

    // Find a car by ID
    public Optional<Auto> findById(int id) {
        try {
            DSLContext create = getDSLContext();
            AutoRecord record = create.selectFrom(Tables.AUTO)
                    .where(Tables.AUTO.ID.eq(id))
                    .fetchOne();
            return Optional.ofNullable(record).map(this::mapToAuto);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding car by ID", e);
        }
    }

    // Delete a car by ID
    public void deleteById(int id) {
        try {
            DSLContext create = getDSLContext();
            int rowsDeleted = create.deleteFrom(Tables.AUTO)
                    .where(Tables.AUTO.ID.eq(id))
                    .execute();
            if (rowsDeleted == 0) {
                throw new RuntimeException("Car with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting car", e);
        }
    }

    // Utility method to map AutoRecord to Auto object
    private Auto mapToAuto(AutoRecord record) {
        return new Auto(
                record.getId(),                // ID
                record.getModel(),             // Model
                record.getBrand(),             // Brand
                record.getLicenseplate(),      // License Plate
                mapToAutoStatus(record.getAutostatus()) // Auto Status (converted from Integer)
        );
    }

    // Helper method to map integer status to Auto.AutoStatus enum
    private Auto.AutoStatus mapToAutoStatus(Integer status) {
        if (status == null) {
            return null; // Handle null status gracefully
        }
        return Auto.AutoStatus.fromCode(status); // Explicitly reference the nested enum
    }
}