package htw.prog3.KTM.unit;

import htw.prog3.KTM.model.CarRepairComponent;
import htw.prog3.KTM.model.car.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("1", "Model S", "Tesla", "ABC-123", "1");
    }

    @Test
    void testCarCreation() {
        assertEquals("1", car.getId());
        assertEquals("Model S", car.getModel());
        assertEquals("Tesla", car.getBrand());
        assertEquals("ABC-123", car.getLicensePlate());
        assertEquals(Car.CarStatus.AVAILABLE, car.getCarStatus());
    }

    @Test
    void testSettersAndGetters() {
        car.setId("2");
        car.setModel("Model 3");
        car.setBrand("Tesla");
        car.setLicensePlate("XYZ-789");
        car.setCarStatus(Car.CarStatus.IN_SERVICE);

        assertEquals("2", car.getId());
        assertEquals("Model 3", car.getModel());
        assertEquals("Tesla", car.getBrand());
        assertEquals("XYZ-789", car.getLicensePlate());
        assertEquals(Car.CarStatus.IN_SERVICE, car.getCarStatus());
    }

    @Test
    void testAddJob() {
        CarRepairComponent job = () -> System.out.println("Job done");
        car.addJob(job);

        assertEquals(1, car.getJobs().size());
        assertEquals(job, car.getJobs().get(0));
    }

    @Test
    void testInvalidCarStatus() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Car("1", "Model S", "Tesla", "ABC-123", "33"));
        assertFalse(exception.getMessage().contains("Unknown JobStatus"));
    }

    @Test
    void testToString() {
        String expected = "Car{id='1', model='Model S', brand='Tesla', licensePlate='ABC-123', carStatus=AVAILABLE}";
        assertEquals(expected, car.toString());
    }
}
