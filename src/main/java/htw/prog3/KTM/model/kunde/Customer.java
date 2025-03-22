package htw.prog3.KTM.model.kunde;

import htw.prog3.KTM.model.car.Car;

import java.util.ArrayList;
import java.util.List;


public class Customer {
    private int id;
    private String name;
    private String address;
    private String phone;
    private List<Car> cars;


    public Customer(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.cars = new ArrayList<>();
    }

    public List<Car> getAutos() {
        return cars;
    }

    public void addAuto(Car car) {
        this.cars.add(car);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
