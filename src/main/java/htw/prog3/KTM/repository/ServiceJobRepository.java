package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.jobs.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceJobRepository {

    private final DatabaseManager databaseManager;
    private static final String TABLE_NAME = "Service";
    private static final String COL_JOB_ID = "JOB_ID";
    private static final String COL_CAR_ID = "CAR_ID";
    private static final String COL_JOB_NAME = "JOB_NAME";
    private static final String COL_STATUS = "STATUS";
    private static final String COL_TYPE = "TYPE";
    private static final String COL_SERVICE_TYPE = "SERVICE_TYPE";

    public ServiceJobRepository(DatabaseManager databaseManager) {
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
                    COL_JOB_ID + " INTEGER PRIMARY KEY, " +
                    COL_CAR_ID + " INTEGER NOT NULL, " +
                    COL_JOB_NAME + " TEXT NOT NULL, " +
                    COL_STATUS + " TEXT NOT NULL, " +
                    COL_TYPE + " INTEGER NOT NULL, " +
                    COL_SERVICE_TYPE + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + COL_CAR_ID + ") REFERENCES CAR(ID)" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error creating service job table", e);
        }
    }

    // Save a service job
    public void save(Service service, int carId) {
        try {
            DSLContext create = getDSLContext();
            
            // Check if the service job already exists
            Result<Record> existingRecord = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_JOB_ID).eq(service.getId()))
                    .fetch();
            
            if (existingRecord.isNotEmpty()) {
                // Update existing record
                create.update(DSL.table(TABLE_NAME))
                        .set(DSL.field(COL_JOB_NAME), service.getName())
                        .set(DSL.field(COL_STATUS), service.getStatusString())
                        .set(DSL.field(COL_TYPE), service.getTypeOrdinal())
                        .where(DSL.field(COL_JOB_ID).eq(service.getId()))
                        .execute();
            } else {
                // Insert new record
                create.insertInto(DSL.table(TABLE_NAME))
                        .columns(
                                DSL.field(COL_JOB_ID),
                                DSL.field(COL_CAR_ID),
                                DSL.field(COL_JOB_NAME),
                                DSL.field(COL_STATUS),
                                DSL.field(COL_TYPE),
                                DSL.field(COL_SERVICE_TYPE)
                        )
                        .values(
                                service.getId(),
                                carId,
                                service.getName(),
                                service.getStatusString(),
                                service.getTypeOrdinal(),
                                service.getServiceType()
                        )
                        .execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving service job", e);
        }
    }

    // Find service jobs by car id
    public List<ServiceJob> findServiceJobByCarId(int carId) {
        try {
            DSLContext create = getDSLContext();
            
            Result<Record> result = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_CAR_ID).eq(carId))
                    .and(DSL.field(COL_SERVICE_TYPE).eq(ServiceJob.SERVICE_TYPE))
                    .fetch();
            
            List<ServiceJob> serviceJobs = new ArrayList<>();
            for (Record record : result) {
                serviceJobs.add(mapToServiceJob(record));
            }
            
            return serviceJobs;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding service jobs by car ID", e);
        }
    }

    public List<RepairJob> findRepairJobByCarId(int carId) {
        try {
            DSLContext create = getDSLContext();

            Result<Record> result = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_CAR_ID).eq(carId))
                    .and(DSL.field(COL_SERVICE_TYPE).eq(RepairJob.SERVICE_TYPE))
                    .fetch();

            List<RepairJob> repairJobs = new ArrayList<>();
            for (Record record : result) {
                repairJobs.add(mapToRepairJob(record));
            }

            return repairJobs;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding service jobs by car ID", e);
        }
    }

    public List<RepairJob> findAllRepairJobs() {
        try {
            DSLContext create = getDSLContext();
            Result<Record> result = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_SERVICE_TYPE).eq(RepairJob.SERVICE_TYPE))
                    .fetch();

            List<RepairJob> repairJobs = new ArrayList<>();
            for (Record record : result) {
                repairJobs.add(mapToRepairJob(record));
            }
            return repairJobs;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all service jobs", e);
        }
    }

    // Find all service jobs
    public List<ServiceJob> findAllServiceJobs() {
        try {
            DSLContext create = getDSLContext();
            Result<Record> result = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_SERVICE_TYPE).eq(ServiceJob.SERVICE_TYPE))
                    .fetch();
            
            List<ServiceJob> serviceJobs = new ArrayList<>();
            for (Record record : result) {
                serviceJobs.add(mapToServiceJob(record));
            }
            return serviceJobs;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all service jobs", e);
        }
    }

    public Optional<RepairJob> findRepairJobById(int jobId) {
        try {
            DSLContext create = getDSLContext();
            Record record = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_JOB_ID).eq(jobId))
                    .and(DSL.field(COL_SERVICE_TYPE).eq(RepairJob.SERVICE_TYPE))
                    .fetchOne();

            return Optional.ofNullable(record)
                    .map(this::mapToRepairJob);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding service job by ID", e);
        }
    }

    // Find a service job by ID
    public Optional<ServiceJob> findServiceJobById(int jobId) {
        try {
            DSLContext create = getDSLContext();
            Record record = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_JOB_ID).eq(jobId))
                    .and(DSL.field(COL_SERVICE_TYPE).eq(ServiceJob.SERVICE_TYPE))
                    .fetchOne();
            
            return Optional.ofNullable(record)
                    .map(this::mapToServiceJob);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding service job by ID", e);
        }
    }

    // Get the car ID for a specific service job
    public int getCarIdForServiceJob(int jobId) {
        try {
            DSLContext create = getDSLContext();
            Record record = create.select(DSL.field(COL_CAR_ID))
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_JOB_ID).eq(jobId))
                    .fetchOne();
            
            return record != null ? record.get(DSL.field(COL_CAR_ID, Integer.class)) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting car ID for service job", e);
        }
    }

    // Delete a service job by ID
    public void deleteById(int jobId) {
        try {
            DSLContext create = getDSLContext();
            create.deleteFrom(DSL.table(TABLE_NAME))
                    .where(DSL.field(COL_JOB_ID).eq(jobId))
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting service job", e);
        }
    }

    // Delete service jobs by car ID
    public void deleteByCarId(int carId) {
        try {
            DSLContext create = getDSLContext();
            create.deleteFrom(DSL.table(TABLE_NAME))
                    .where(DSL.field(COL_CAR_ID).eq(carId))
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting service jobs by car ID", e);
        }
    }

    // Utility method to map Record to ServiceJob object
    private ServiceJob mapToServiceJob(Record record) {
        int jobId = record.get(DSL.field(COL_JOB_ID, Integer.class));
        String jobName = record.get(DSL.field(COL_JOB_NAME, String.class));
        String status = record.get(DSL.field(COL_STATUS, String.class));
        int typeOrdinal = record.get(DSL.field(COL_TYPE, Integer.class));
        
        ServiceJobType type = ServiceJobType.values()[typeOrdinal];
        
        return new ServiceJob(jobId, type, jobName, status);
    }

    private RepairJob mapToRepairJob(Record record) {
        int jobId = record.get(DSL.field(COL_JOB_ID, Integer.class));
        String jobName = record.get(DSL.field(COL_JOB_NAME, String.class));
        String status = record.get(DSL.field(COL_STATUS, String.class));
        int typeOrdinal = record.get(DSL.field(COL_TYPE, Integer.class));

        RepairJobType type = RepairJobType.values()[typeOrdinal];

        return new RepairJob(jobId, type, jobName, status);
    }
} 