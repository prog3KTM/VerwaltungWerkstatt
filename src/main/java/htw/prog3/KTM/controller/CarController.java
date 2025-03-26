package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.car.Car;
import htw.prog3.KTM.repository.CarRepository;

import java.util.List;

public class CarController {
    private final CarRepository carRepository;

    public CarController(DatabaseManager databaseManager) {
        this.carRepository = new CarRepository(databaseManager);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    // Change method parameter to String
    public Car getCarById(String id) {
        return carRepository.findById(id).orElse(null); // Returns null if no car is found
    }

    // Change method parameter to String
    public void deleteCarById(String id) {
        carRepository.deleteById(id);
    }
    
    // Add a method to update a car
    public void updateCar(Car car) {
      carRepository.update(car);
    }


}