package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.generated.tables.records.AutoRecord;
import htw.prog3.KTM.generated.Tables;
import htw.prog3.KTM.model.auto.Auto;
import org.jooq.DSLContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AutoRepository {

    private final DatabaseManager databaseManager;

    public AutoRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        createTableIfNotExists();
    }

    private DSLContext getDSLContext() throws SQLException {
        return databaseManager.getDSLContext();
    }

    private void createTableIfNotExists() {
        try {
            DSLContext create = getDSLContext();
            create.execute("CREATE TABLE IF NOT EXISTS AUTO (" +
                    "id VARCHAR(100) PRIMARY KEY, " +
                    "model VARCHAR(100) NOT NULL, " +
                    "brand VARCHAR(100) NOT NULL, " +
                    "licensePlate VARCHAR(100) NOT NULL, " +
                    "autostatus INTEGER" +
                    ")");
            
            // Check if autostatus column exists, if not add it
            try {
                create.select().from("AUTO").where("autostatus IS NOT NULL").limit(1).fetch();
            } catch (Exception e) {
                // Column doesn't exist, add it
                create.execute("ALTER TABLE AUTO ADD COLUMN autostatus INTEGER");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating auto table", e);
        }
    }

    // Retrieve all cars
    public List<Auto> findAll() {
        try {
            DSLContext create = getDSLContext();
            return create.selectFrom(Tables.AUTO)
                    .fetch()
                    .map(this::mapToAuto);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all cars", e);
        }
    }

    // Save a new car
    public void save(Auto auto) {
        try {
            DSLContext create = getDSLContext();
            
            // Ensure auto status is not null
            if (auto.getAutoStatus() == null) {
                auto.setAutoStatus(Auto.AutoStatus.AVAILABLE);
            }
            
            AutoRecord autoRecord = create.newRecord(Tables.AUTO, auto);
            
            // Explicitly set the autostatus field
            autoRecord.setAutostatus(auto.getAutoStatus().getCode());
            
            autoRecord.store();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving the car", e);
        }
    }

    // Find a car by ID (now accepts a String)
    public Optional<Auto> findById(String id) {
        try {
            DSLContext create = getDSLContext();
            AutoRecord record = create.selectFrom(Tables.AUTO)
                    .where(Tables.AUTO.ID.eq(id))
                    .fetchOne();
            return Optional.ofNullable(record)
                    .map(this::mapToAuto);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding car by ID", e);
        }
    }

    // Delete a car by ID (now accepts a String)
    public void deleteById(String id) {
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
            return Auto.AutoStatus.AVAILABLE; // Default to AVAILABLE if status is null
        }
        return Auto.AutoStatus.fromCode(status); // Explicitly reference the nested enum
    }
}