package htw.prog3.KTM.controller;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.model.car.Car;
import htw.prog3.KTM.model.jobs.JobStatus;
import htw.prog3.KTM.model.jobs.RepairJob;
import htw.prog3.KTM.model.jobs.RepairJobType;
import htw.prog3.KTM.model.jobs.ServiceJob;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepairJobControllerTest {

    private static RepairJobController repairJobController;
    private static CarController carController;
    private static AppConfig appConfig;
    private static int testCarId;

    @BeforeAll
    static void setUp() {
        appConfig = new AppConfig();
        repairJobController = appConfig.getRepairJobController();
        carController = appConfig.getCarController();
    }
    
    @BeforeEach
    void setupTestData() {
        // Clean up any existing test cars first
        List<Car> existingCars = carController.getAllCars();
        for (Car car : existingCars) {
            if (car.getModel().startsWith("Test Car Model")) {
                // First delete any repair jobs associated with this car
                repairJobController.deleteRepairJobsByCarId(car.getId());
                // Then delete the car
                carController.deleteCarById(car.getId());
            }
        }
        
        // Create a test car with a unique model name and license plate
        String uniqueId = String.valueOf(System.currentTimeMillis());
        Car testCar = new Car(0, "Test Car Model " + uniqueId, 
                              "Test Brand", 
                              "TEST-" + uniqueId.substring(uniqueId.length() - 4), 
                              "AVAILABLE");
        
        carController.addCar(testCar);
        
        // Find the car to get its ID
        List<Car> cars = carController.getAllCars();
        Car created = cars.stream()
                .filter(c -> c.getModel().equals(testCar.getModel()) && c.getLicensePlate().equals(testCar.getLicensePlate()))
                .findFirst()
                .orElseThrow();
        testCarId = created.getId();
    }
    
    @Test
    void addRepairJob_ValidRepairJob_RepairJobAddedSuccessfully() {
        // Create a test repair job
        RepairJob repairJob = new RepairJob(0, RepairJobType.BRAKE_REPLACEMENT, "Test Brake Repair", "CREATED");
        
        // Add the repair job
        repairJobController.addRepairJob(repairJob, testCarId);
        
        // Get repair jobs for the car
        List<RepairJob> repairJobs = repairJobController.getRepairJobsByCarId(testCarId);
        
        // Verify job was added
        assertEquals(1, repairJobs.size());
        RepairJob added = repairJobs.get(0);
        assertEquals("Test Brake Repair", added.getName());
        assertEquals(RepairJobType.BRAKE_REPLACEMENT, added.getType());
        assertEquals(JobStatus.CREATED, added.getStatus());
        
        // Clean up
        repairJobController.deleteRepairJobById(added.getId());
    }
    
    @Test
    void getRepairJobById_ExistingJobId_ReturnsCorrectRepairJob() {
        // Create and add a repair job
        RepairJob repairJob = new RepairJob(0, RepairJobType.ENGINE_REPAIR, "Test Engine Repair", "IN_PROGRESS");
        repairJobController.addRepairJob(repairJob, testCarId);
        
        // Get all repair jobs to find the one we just created
        List<RepairJob> repairJobs = repairJobController.getRepairJobsByCarId(testCarId);
        RepairJob added = repairJobs.stream()
                .filter(j -> j.getName().equals("Test Engine Repair"))
                .findFirst()
                .orElseThrow();
        
        // Test getting by ID
        Optional<RepairJob> retrieved = repairJobController.getRepairJobById(added.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Test Engine Repair", retrieved.get().getName());
        assertEquals(RepairJobType.ENGINE_REPAIR, retrieved.get().getType());
        assertEquals(JobStatus.IN_PROGRESS, retrieved.get().getStatus());
        
        // Clean up
        repairJobController.deleteRepairJobById(added.getId());
    }
    
    @Test
    void getRepairJobsByCarId_CarWithMultipleJobs_ReturnsAllJobsForCar() {
        // Create and add multiple repair jobs with unique names and IDs
        String brakeJobName = "Test Brake Job " + System.currentTimeMillis();
        
        // Add a small delay to ensure different timestamps
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        String electricalJobName = "Test Electrical Job " + System.currentTimeMillis();
        
        RepairJob job1 = new RepairJob(0, RepairJobType.BRAKE_REPLACEMENT, brakeJobName, "CREATED");
        RepairJob job2 = new RepairJob(0, RepairJobType.ELECTRICAL_REPAIR, electricalJobName, "IN_PROGRESS");
        
        // First ensure there are no jobs for this car
        repairJobController.deleteRepairJobsByCarId(testCarId);
        
        // Add jobs one by one, with clear debug statements
        System.out.println("Adding brake job: " + brakeJobName);
        repairJobController.addRepairJob(job1, testCarId);
        
        // Check immediately if the brake job was successfully added
        List<RepairJob> jobsAfterBrake = repairJobController.getRepairJobsByCarId(testCarId);
        System.out.println("After adding brake job: " + jobsAfterBrake.size() + " jobs found");
        for(RepairJob job : jobsAfterBrake) {
            System.out.println("Found job after brake addition: ID=" + job.getId() + 
                              ", Name=" + job.getName() + 
                              ", Type=" + job.getType());
        }
        
        // Add the second job
        System.out.println("Adding electrical job: " + electricalJobName);
        repairJobController.addRepairJob(job2, testCarId);
        
        // Get all jobs after both additions
        List<RepairJob> repairJobs = repairJobController.getRepairJobsByCarId(testCarId);
        System.out.println("After adding both jobs: " + repairJobs.size() + " jobs found");
        
        // Printing all jobs for debugging
        for(RepairJob job : repairJobs) {
            System.out.println("Found job after both additions: ID=" + job.getId() + 
                              ", Name=" + job.getName() + 
                              ", Type=" + job.getType());
        }
        
        // Verify count
        assertTrue(repairJobs.size() >= 1, "Expected at least 1 job to be added");
        
        // Check for job types by name, which is more reliable
        boolean foundBrakeJob = repairJobs.stream()
                .anyMatch(j -> j.getName().equals(brakeJobName));
        boolean foundElectricalJob = repairJobs.stream()
                .anyMatch(j -> j.getName().equals(electricalJobName));
        
        // Assert each type separately for better error reporting
        assertTrue(foundBrakeJob, "Brake job not found");
        assertTrue(foundElectricalJob, "Electrical job not found");
        
        // Clean up
        repairJobController.deleteRepairJobsByCarId(testCarId);
    }
    
    @Test
    void deleteRepairJobById_ExistingJobId_JobIsDeleted() {
        // Create and add a repair job
        RepairJob repairJob = new RepairJob(0, RepairJobType.SUSPENSION_REPAIR, "Test Suspension Repair", "CREATED");
        repairJobController.addRepairJob(repairJob, testCarId);
        
        // Get the job to find its ID
        List<RepairJob> repairJobs = repairJobController.getRepairJobsByCarId(testCarId);
        RepairJob added = repairJobs.stream()
                .filter(j -> j.getName().equals("Test Suspension Repair"))
                .findFirst()
                .orElseThrow();
        
        // Delete the job
        repairJobController.deleteRepairJobById(added.getId());
        
        // Verify deletion
        Optional<RepairJob> retrieved = repairJobController.getRepairJobById(added.getId());
        assertFalse(retrieved.isPresent());
    }
    
    @Test
    void deleteRepairJobsByCarId_CarWithMultipleJobs_AllJobsForCarDeleted() {
        // Create and add multiple repair jobs with unique names
        RepairJob job1 = new RepairJob(0, RepairJobType.COOLING_SYSTEM_REPAIR, "Test Cooling Job " + System.currentTimeMillis(), "CREATED");
        
        // Add a small delay to ensure different timestamps
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        RepairJob job2 = new RepairJob(0, RepairJobType.TRANSMISSION_FIX, "Test Transmission Job " + System.currentTimeMillis(), "IN_PROGRESS");
        
        // First ensure there are no jobs for this car
        repairJobController.deleteRepairJobsByCarId(testCarId);
        
        // Add jobs one by one and verify after each addition
        repairJobController.addRepairJob(job1, testCarId);
        List<RepairJob> jobsAfterFirst = repairJobController.getRepairJobsByCarId(testCarId);
        System.out.println("After adding first job: " + jobsAfterFirst.size() + " jobs found");
        
        repairJobController.addRepairJob(job2, testCarId);
        List<RepairJob> jobsBefore = repairJobController.getRepairJobsByCarId(testCarId);
        System.out.println("After adding second job: " + jobsBefore.size() + " jobs found");
        
        // Print job names for debugging
        for(RepairJob job : jobsBefore) {
            System.out.println("Added job: ID=" + job.getId() + ", Name=" + job.getName() + ", Type=" + job.getType());
        }
        
        // Assert at least one job was added successfully
        assertTrue(jobsBefore.size() >= 1, "Expected at least one job to be added");
        
        // Delete all jobs for the car
        repairJobController.deleteRepairJobsByCarId(testCarId);
        
        // Verify deletion
        List<RepairJob> jobsAfter = repairJobController.getRepairJobsByCarId(testCarId);
        assertEquals(0, jobsAfter.size());
    }
    
    @Test
    void getCarIdForRepairJob_ExistingJobId_ReturnsCorrectCarId() {
        // Create and add a repair job
        RepairJob repairJob = new RepairJob(0, RepairJobType.ENGINE_REPAIR, "Test Engine Job for Car ID", "CREATED");
        repairJobController.addRepairJob(repairJob, testCarId);
        
        // Get the job to find its ID
        List<RepairJob> repairJobs = repairJobController.getRepairJobsByCarId(testCarId);
        RepairJob added = repairJobs.stream()
                .filter(j -> j.getName().equals("Test Engine Job for Car ID"))
                .findFirst()
                .orElseThrow();
        
        // Get car ID for the job
        int carId = repairJobController.getCarIdForRepairJob(added.getId());
        
        // Verify correct car ID is returned
        assertEquals(testCarId, carId);
        
        // Clean up
        repairJobController.deleteRepairJobById(added.getId());
    }
} 