package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.car.Car;
import htw.prog3.KTM.generated.tables.records.CarRecord;
import htw.prog3.KTM.generated.Tables;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CarRepository {

    private final DatabaseManager databaseManager;
    private static final String TABLE_NAME = "CAR";
    private static final String COL_ID = "id";
    private static final String COL_MODEL = "model";
    private static final String COL_BRAND = "brand";
    private static final String COL_LICENSE_PLATE = "licenseplate";
    private static final String COL_CAR_STATUS = "carstatus";

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
            
            create.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_MODEL + " VARCHAR(100) NOT NULL, " +
                    COL_BRAND + " VARCHAR(100) NOT NULL, " +
                    COL_LICENSE_PLATE + " VARCHAR(100) NOT NULL, " +
                    COL_CAR_STATUS + " INTEGER" +
                    ")");

            // Check if carstatus column exists, if not add it
            try {
                create.select().from(TABLE_NAME).where(DSL.field(COL_CAR_STATUS).isNotNull()).limit(1).fetch();
            } catch (Exception e) {
                // Column doesn't exist, add it
                create.execute("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_CAR_STATUS + " INTEGER");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating car table", e);
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

            // Ensure car status is not null
            if (car.getCarStatus() == null) {
                car.setCarStatus(Car.CarStatus.AVAILABLE);
            }

            // Using jOOQ DSL style with fallback to SQL for compatibility
            create.insertInto(DSL.table(TABLE_NAME))
                .set(DSL.field(COL_ID), car.getId())
                .set(DSL.field(COL_MODEL), car.getModel())
                .set(DSL.field(COL_BRAND), car.getBrand())
                .set(DSL.field(COL_LICENSE_PLATE), car.getLicensePlate())
                .set(DSL.field(COL_CAR_STATUS), car.getCarStatus().ordinal())
                .execute();
                
        } catch (SQLException e) {
            throw new RuntimeException("Error saving the car", e);
        }
    }

    // Find a car by ID
    public Optional<Car> findById(int id) {
        try {
            DSLContext create = getDSLContext();
            
            Record record = create.select()
                .from(TABLE_NAME)
                .where(DSL.field(COL_ID).eq(id))
                .fetchOne();
            
            if (record == null) {
                return Optional.empty();
            }
            
            Car car = new Car(
                Integer.parseInt(record.getValue(COL_ID).toString()),
                record.getValue(COL_MODEL).toString(),
                record.getValue(COL_BRAND).toString(),
                record.getValue(COL_LICENSE_PLATE).toString(),
                record.getValue(COL_CAR_STATUS) != null ? record.getValue(COL_CAR_STATUS).toString() : "0"
            );
            
            return Optional.of(car);
            
        } catch (SQLException e) {
            throw new RuntimeException("Error finding car by ID", e);
        }
    }

    // Delete a car by ID
    public void deleteById(int id) {
        try {
            DSLContext create = getDSLContext();
            
            int rowsDeleted = create.deleteFrom(DSL.table(TABLE_NAME))
                .where(DSL.field(COL_ID).eq(id))
                .execute();
            
            if (rowsDeleted == 0) {
                throw new RuntimeException("Car with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting car", e);
        }
    }

    // Utility method to map CarRecord to Car object - kept for compatibility
    private Car mapToCar(CarRecord record) {
        try {
            return new Car(
                Integer.parseInt(record.getValue(COL_ID).toString()),
                record.getValue(COL_MODEL).toString(),
                record.getValue(COL_BRAND).toString(),
                record.getValue(COL_LICENSE_PLATE).toString(),
                record.getValue(COL_CAR_STATUS) != null ? record.getValue(COL_CAR_STATUS).toString() : "0"
            );
        } catch (Exception e) {
            throw new RuntimeException("Error mapping record to Car: " + e.getMessage(), e);
        }
    }

    // Helper method to map integer status to Car.CarStatus enum
    private Car.CarStatus mapToCarStatus(String status) {
        if (status == null) {
            return Car.CarStatus.AVAILABLE; // Default to AVAILABLE if status is null
        }
        return Car.CarStatus.fromString(status); // Explicitly reference the nested enum
    }
}