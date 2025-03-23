package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.jobs.ServiceJob;
import htw.prog3.KTM.model.jobs.ServiceJobType;
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
    private static final String TABLE_NAME = "SERVICE_JOB";
    private static final String COL_JOB_ID = "JOB_ID";
    private static final String COL_AUTO_ID = "AUTO_ID";
    private static final String COL_JOB_NAME = "JOB_NAME";
    private static final String COL_STATUS = "STATUS";
    private static final String COL_TYPE = "TYPE";

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
                    COL_AUTO_ID + " TEXT NOT NULL, " +
                    COL_JOB_NAME + " TEXT NOT NULL, " +
                    COL_STATUS + " TEXT NOT NULL, " +
                    COL_TYPE + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + COL_AUTO_ID + ") REFERENCES CAR(ID)" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error creating service job table", e);
        }
    }

    // Save a service job
    public void save(ServiceJob serviceJob, int carId) {
        try {
            DSLContext create = getDSLContext();
            
            // Check if the service job already exists
            Result<Record> existingRecord = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_JOB_ID).eq(serviceJob.getId()))
                    .fetch();
            
            if (existingRecord.isNotEmpty()) {
                // Update existing record
                create.update(DSL.table(TABLE_NAME))
                        .set(DSL.field(COL_JOB_NAME), serviceJob.getName())
                        .set(DSL.field(COL_STATUS), serviceJob.getStatus())
                        .set(DSL.field(COL_TYPE), serviceJob.getType().ordinal())
                        .where(DSL.field(COL_JOB_ID).eq(serviceJob.getId()))
                        .execute();
            } else {
                // Insert new record
                create.insertInto(DSL.table(TABLE_NAME))
                        .columns(
                                DSL.field(COL_JOB_ID),
                                DSL.field(COL_AUTO_ID),
                                DSL.field(COL_JOB_NAME),
                                DSL.field(COL_STATUS),
                                DSL.field(COL_TYPE)
                        )
                        .values(
                                serviceJob.getId(),
                                carId,
                                serviceJob.getName(),
                                serviceJob.getStatus(),
                                serviceJob.getType().ordinal()
                        )
                        .execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving service job", e);
        }
    }

    // Find all service jobs for a specific auto
    public List<ServiceJob> findByAutoId(int autoId) {
        try {
            DSLContext create = getDSLContext();
            Result<Record> result = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_AUTO_ID).eq(autoId))
                    .fetch();
            
            List<ServiceJob> serviceJobs = new ArrayList<>();
            for (Record record : result) {
                serviceJobs.add(mapToServiceJob(record));
            }
            return serviceJobs;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding service jobs by auto ID", e);
        }
    }

    // Find all service jobs
    public List<ServiceJob> findAll() {
        try {
            DSLContext create = getDSLContext();
            Result<Record> result = create.select()
                    .from(TABLE_NAME)
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

    // Find a service job by ID
    public Optional<ServiceJob> findById(int jobId) {
        try {
            DSLContext create = getDSLContext();
            Record record = create.select()
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_JOB_ID).eq(jobId))
                    .fetchOne();
            
            return Optional.ofNullable(record)
                    .map(this::mapToServiceJob);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding service job by ID", e);
        }
    }

    // Get auto ID for a service job
    public String getAutoIdForServiceJob(int jobId) {
        try {
            DSLContext create = getDSLContext();
            Record record = create.select(DSL.field(COL_AUTO_ID))
                    .from(TABLE_NAME)
                    .where(DSL.field(COL_JOB_ID).eq(jobId))
                    .fetchOne();
            
            return record != null ? record.get(DSL.field(COL_AUTO_ID, String.class)) : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting auto ID for service job", e);
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

    // Delete all service jobs for an auto
    public void deleteByAutoId(String autoId) {
        try {
            DSLContext create = getDSLContext();
            create.deleteFrom(DSL.table(TABLE_NAME))
                    .where(DSL.field(COL_AUTO_ID).eq(autoId))
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting service jobs for auto", e);
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
} 