package htw.prog3.KTM.unit;

import htw.prog3.KTM.model.car.Car;
import htw.prog3.KTM.model.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;
    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        customer = new Customer(1, "Max Mustermann", "Musterstraße 1", "0123456789");
        car1 = new Car("1", "Model S", "Tesla", "ABC-123", "AVAILABLE");
        car2 = new Car("2", "Golf", "Volkswagen", "XYZ-456", "IN_SERVICE");
    }

    @Test
    void testCustomerCreation() {
        assertEquals(1, customer.getId());
        assertEquals("Max Mustermann", customer.getName());
        assertEquals("Musterstraße 1", customer.getAddress());
        assertEquals("0123456789", customer.getPhone());
        assertTrue(customer.getCars().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        customer.setId(2);
        customer.setName("Erika Musterfrau");
        customer.setAddress("Beispielweg 5");
        customer.setPhone("0987654321");

        assertEquals(2, customer.getId());
        assertEquals("Erika Musterfrau", customer.getName());
        assertEquals("Beispielweg 5", customer.getAddress());
        assertEquals("0987654321", customer.getPhone());
    }

    @Test
    void testAddCar() {
        customer.addCar(car1);
        customer.addCar(car2);

        List<Car> cars = customer.getCars();
        assertEquals(2, cars.size());
        assertTrue(cars.contains(car1));
        assertTrue(cars.contains(car2));
    }

    @Test
    void testAddDuplicateCar() {
        customer.addCar(car1);
        customer.addCar(car1);  // Hinzufügen des gleichen Autos erneut

        assertEquals(2, customer.getCars().size());  // Doppelte Autos werden trotzdem gespeichert
    }

    @Test
    void testEmptyPhoneNumber() {
        customer.setPhone("");
        assertEquals("", customer.getPhone());
    }

    @Test
    void testNullAddress() {
        customer.setAddress(null);
        assertNull(customer.getAddress());
    }
}
