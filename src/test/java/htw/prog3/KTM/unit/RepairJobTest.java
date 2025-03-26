package htw.prog3.KTM.unit;

import htw.prog3.KTM.model.jobs.JobStatus;
import htw.prog3.KTM.model.jobs.RepairJob;
import htw.prog3.KTM.model.jobs.RepairJobType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepairJobTest {
    private RepairJob repairJob;

    @BeforeEach
    void setUp() {
        repairJob = new RepairJob(1, RepairJobType.ENGINE_REPAIR, "ENGINE_REPAIR", "IN_PROGRESS");
    }

    @Test
    void testRepairJobCreation() {
        assertEquals(1, repairJob.getId());
        assertEquals("ENGINE_REPAIR", repairJob.getName());
        assertEquals(RepairJobType.ENGINE_REPAIR, repairJob.getType());
        assertEquals(JobStatus.IN_PROGRESS, repairJob.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        repairJob.setId(2);
        repairJob.setName("ELECTRICAL_REPAIR");
        repairJob.setType(RepairJobType.ELECTRICAL_REPAIR);
        repairJob.setStatus(JobStatus.COMPLETED);

        assertEquals(2, repairJob.getId());
        assertEquals("ELECTRICAL_REPAIR", repairJob.getName());
        assertEquals(RepairJobType.ELECTRICAL_REPAIR, repairJob.getType());
        assertEquals(JobStatus.COMPLETED, repairJob.getStatus());
    }

    @Test
    void testGetTypeString() {
        assertEquals("ENGINE_REPAIR", repairJob.getTypeString());
    }

    @Test
    void testGetStatusString() {
        assertEquals("IN_PROGRESS", repairJob.getStatusString());
    }

    @Test
    void testInvalidStatus() {
        // Create a RepairJob with an invalid status
        RepairJob job = new RepairJob(3, RepairJobType.ENGINE_REPAIR, "Test Job", "UNKNOWN_STATUS");
        
        // Check that the status was set to the default (CREATED) rather than throwing an exception
        assertEquals(JobStatus.CREATED, job.getStatus());
    }

@Test
    void createRepairJob_ValidData_ObjectCreatedSuccessfully() {
        repairJob = new RepairJob(repairJob.getId(),repairJob.getType(),repairJob.getName(),repairJob.getStatusString());
    }
}
