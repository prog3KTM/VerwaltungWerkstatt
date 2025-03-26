package htw.prog3.KTM.controller;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.model.car.Car;
import htw.prog3.KTM.model.car.Car.CarStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CarControllerTest {

    private static CarController carController;
    private static AppConfig appConfig;

    @BeforeAll
    static void setUp() {
        appConfig = new AppConfig();
        carController = appConfig.getCarController();
    }
    
    @BeforeEach
    void clearData() {
        // Delete existing test cars
        List<Car> cars = carController.getAllCars();
        for (Car car : cars) {
            if (car.getModel().startsWith("Test")) {
                carController.deleteCarById(car.getId());
            }
        }
    }
    
    // Use enum directly
    private static Car car_testdata = new Car(0, "Test Model", "Test Brand", "TEST-123", CarStatus.AVAILABLE.toString());

    @Test
    void addCar_ValidCar_CarAddedSuccessfully() {
        // Initial count
        int initialCount = carController.getAllCars().size();
        
        // Create a unique test car for this test
        String uniqueId = String.valueOf(System.currentTimeMillis());
        Car testCar = new Car(0, "Test Model " + uniqueId, "Test Brand", 
                             "TEST-" + uniqueId.substring(uniqueId.length() - 4), 
                             CarStatus.AVAILABLE.toString());
        
        carController.addCar(testCar);
        
        // Get updated list
        List<Car> cars = carController.getAllCars();
        assertEquals(initialCount + 1, cars.size());
        
        // Verify the car exists
        Optional<Car> added = cars.stream()
                .filter(c -> c.getModel().equals(testCar.getModel()) && c.getLicensePlate().equals(testCar.getLicensePlate()))
                .findFirst();
        assertTrue(added.isPresent());
        
        // Clean up
        carController.deleteCarById(added.get().getId());
    }

    @Test
    void getCarById_ExistingCarId_ReturnsCorrectCar() {
        // Add a test car first with a unique ID
        String uniqueId = String.valueOf(System.currentTimeMillis());
        Car testCar = new Car(0, "Test Model " + uniqueId, "Test Brand", 
                             "TEST-" + uniqueId.substring(uniqueId.length() - 4), 
                             CarStatus.AVAILABLE.toString());
        
        carController.addCar(testCar);
        
        // Find the car by querying all cars and filtering
        List<Car> cars = carController.getAllCars();
        Car added = cars.stream()
                .filter(c -> c.getModel().equals(testCar.getModel()) && c.getLicensePlate().equals(testCar.getLicensePlate()))
                .findFirst()
                .orElseThrow();
        
        // Get the car by ID
        Car car = carController.getCarById(added.getId());
        assertNotNull(car);
        assertEquals(testCar.getModel(), car.getModel());
        assertEquals(testCar.getBrand(), car.getBrand());
        assertEquals(testCar.getLicensePlate(), car.getLicensePlate());
        assertEquals(CarStatus.AVAILABLE, car.getCarStatus());
        
        // Clean up
        carController.deleteCarById(added.getId());
    }

    @Test
    void getAllCars_AfterAddingCar_ReturnsListIncludingNewCar() {
        // Initial count
        int initialCount = carController.getAllCars().size();
        
        // Add a car with unique identifier
        String uniqueId = String.valueOf(System.currentTimeMillis());
        Car testCar = new Car(0, "Test Model " + uniqueId, "Test Brand", 
                             "TEST-" + uniqueId.substring(uniqueId.length() - 4), 
                             CarStatus.AVAILABLE.toString());
        
        carController.addCar(testCar);
        
        // Get updated list
        List<Car> cars = carController.getAllCars();
        assertEquals(initialCount + 1, cars.size());
        
        // Clean up
        Optional<Car> added = cars.stream()
                .filter(c -> c.getModel().equals(testCar.getModel()) && c.getLicensePlate().equals(testCar.getLicensePlate()))
                .findFirst();
        if (added.isPresent()) {
            carController.deleteCarById(added.get().getId());
        }
    }

    @Test
    void updateCar_WithNewDetails_CarIsUpdatedSuccessfully() {
        // Add a test car first with unique identifier
        String uniqueId = String.valueOf(System.currentTimeMillis());
        Car testCar = new Car(0, "Test Model " + uniqueId, "Test Brand", 
                             "TEST-" + uniqueId.substring(uniqueId.length() - 4), 
                             CarStatus.AVAILABLE.toString());
        
        carController.addCar(testCar);
        
        // Find the car by querying all cars and filtering
        List<Car> cars = carController.getAllCars();
        Car added = cars.stream()
                .filter(c -> c.getModel().equals(testCar.getModel()) && c.getLicensePlate().equals(testCar.getLicensePlate()))
                .findFirst()
                .orElseThrow();
        
        // Update the car
        Car updatedCar = new Car(added.getId(), "Test Updated Model", "Updated Brand", "TEST-456", CarStatus.IN_SERVICE.toString());
        carController.updateCar(updatedCar);
        
        // Get the updated car
        Car car = carController.getCarById(added.getId());
        assertEquals("Test Updated Model", car.getModel());
        assertEquals("Updated Brand", car.getBrand());
        assertEquals("TEST-456", car.getLicensePlate());
        assertEquals(CarStatus.IN_SERVICE, car.getCarStatus());
        
        // Clean up
        carController.deleteCarById(added.getId());
    }

    @Test
    void deleteCarById_ExistingCarId_CarIsDeleted() {
        // Add a test car first with unique identifier
        String uniqueId = String.valueOf(System.currentTimeMillis());
        Car testCar = new Car(0, "Test Model " + uniqueId, "Test Brand", 
                             "TEST-" + uniqueId.substring(uniqueId.length() - 4), 
                             CarStatus.AVAILABLE.toString());
        
        carController.addCar(testCar);
        
        // Find the car by querying all cars and filtering
        List<Car> cars = carController.getAllCars();
        Car added = cars.stream()
                .filter(c -> c.getModel().equals(testCar.getModel()) && c.getLicensePlate().equals(testCar.getLicensePlate()))
                .findFirst()
                .orElseThrow();
        
        // Delete the car
        carController.deleteCarById(added.getId());
        
        // Verify deletion
        Car car = carController.getCarById(added.getId());
        assertNull(car);
    }
} 