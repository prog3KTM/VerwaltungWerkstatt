package htw.prog3.KTM.model.car;

import htw.prog3.KTM.model.CarRepairComponent;
import htw.prog3.KTM.model.jobs.JobStatus;

import java.util.ArrayList;
import java.util.List;

public class Car implements CarRepairComponent {
    private final List<CarRepairComponent> jobs = new ArrayList<>();  
    private int id;           
    private String model;
    private String brand;
    private String licensePlate;
    private CarStatus carStatus;


    public Car(int id, String model, String brand, String licensePlate, String carStatus) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.carStatus = Car.CarStatus.fromString(carStatus);
    }

    public Car(int id, String model, String brand, String licensePlate, int carStatus) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.carStatus = Car.CarStatus.values()[carStatus];
    }

    public void addJob(CarRepairComponent job) {
        jobs.add(job);
    }

    public List<CarRepairComponent> getJobs() {
        return jobs;
    }

    @Override
    public void printStatus() {
        // Implement the status printing logic here if needed
    }

    public enum CarStatus {
        AVAILABLE,
        IN_SERVICE,
        SOLD;

        public static CarStatus fromString(String status) {
            try {
                return CarStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown JobStatus: " + status);
            }
        }


        public static String toString(CarStatus status) {
            return status.name();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    public CarStatus getCarStatus() {
        return  carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", carStatus=" + carStatus +
                '}';
    }
}
