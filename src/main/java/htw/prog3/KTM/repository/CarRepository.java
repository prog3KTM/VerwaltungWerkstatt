package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.car.Car;
import htw.prog3.KTM.generated.tables.records.CarRecord;
import htw.prog3.KTM.generated.Tables;
import org.jooq.DSLContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CarRepository {

    private final DatabaseManager databaseManager;

    public CarRepository(DatabaseManager databaseManager) {
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
    public List<Car> findAll() {
        try {
            DSLContext create = getDSLContext();
            return create.selectFrom(Tables.CAR)
                    .fetch()
                    .map(this::mapToCar);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all cars", e);
        }
    }

    // Save a new car
    public void save(Car car) {
        try {
            DSLContext create = getDSLContext();

            // Ensure auto status is not null
            if (car.getCarStatus() == null) {
                car.setCarStatus(Car.CarStatus.AVAILABLE);
            }

            CarRecord carRecord = create.newRecord(Tables.CAR, car);

            // Explicitly set the autostatus field
            carRecord.setStatus(Car.CarStatus.toString(car.getCarStatus()));

            carRecord.store();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving the car", e);
        }
    }

    // Find a car by ID (now accepts a String)
    public Optional<Car> findById(int id) {
        try {
            DSLContext create = getDSLContext();
            CarRecord record = create.selectFrom(Tables.CAR)
                    .where(Tables.CAR.ID.eq(id))
                    .fetchOne();
            return Optional.ofNullable(record)
                    .map(this::mapToCar);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding car by ID", e);
        }
    }

    // Delete a car by ID (now accepts a String)
    public void deleteById(int id) {
        try {
            DSLContext create = getDSLContext();
            int rowsDeleted = create.deleteFrom(Tables.CAR)
                    .where(Tables.CAR.ID.eq(id))
                    .execute();
            if (rowsDeleted == 0) {
                throw new RuntimeException("Car with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting car", e);
        }
    }

    // Utility method to map AutoRecord to Auto object
    private Car mapToCar(CarRecord record) {
        return new Car(
                record.getId(),                // ID
                record.getModel(),             // Model
                record.getBrand(),             // Brand
                record.getLicenseplate(),      // License Plate
                record.getStatus() // Auto Status (converted from Integer)
        );
    }

    // Helper method to map integer status to Auto.AutoStatus enum
    private Car.CarStatus mapToCarStatus(String status) {
        if (status == null) {
            return Car.CarStatus.AVAILABLE; // Default to AVAILABLE if status is null
        }
        return Car.CarStatus.fromString(status); // Explicitly reference the nested enum
    }
}