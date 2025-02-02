package htw.prog3.KTM.unit;

import htw.prog3.KTM.model.jobs.JobStatus;
import htw.prog3.KTM.model.jobs.RepairJob;
import htw.prog3.KTM.model.jobs.RepairJobType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RepairJobTest {
    private String jobName;
    private JobStatus status;
    private RepairJobType type;
    private int jobId;

    @BeforeEach
    void setUp() {
        jobId = 0;
        type = RepairJobType.ENGINE_REPAIR;
        jobName = "Ölwechsel";
        status = JobStatus.CREATED;

    }

    @Test
    void createRepairJob_ValidData_ObjectCreatedSuccessfully() {
        RepairJob repairJob = new RepairJob(jobId,type,jobName,status);

    }
}
