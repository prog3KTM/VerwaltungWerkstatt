package htw.prog3.KTM.model.auto;

import htw.prog3.KTM.model.CarRepairComponent;
import java.util.ArrayList;
import java.util.List;

public class Auto implements CarRepairComponent {
    private final List<CarRepairComponent> jobs = new ArrayList<>();  // Cars can have multiple repair jobs or service jobs
    private String id;           // Changed from int to String
    private String model;
    private String brand;
    private String licensePlate;
    private AutoStatus autoStatus;

    // Updated constructor to accept a String for the ID
    public Auto(String id, String model, String brand, String licensePlate, AutoStatus autoStatus) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.autoStatus = autoStatus;
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

    public enum AutoStatus {
        AVAILABLE(1),
        IN_SERVICE(2),
        SOLD(3);

        private final int code;

        AutoStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static AutoStatus fromCode(int code) {
            for (AutoStatus status : values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown AutoStatus code: " + code);
        }
    }

    // Getter and setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter for model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter and setter for brand
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter and setter for licensePlate
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    // Getter and setter for autoStatus
    public AutoStatus getAutoStatus() {
        return autoStatus;
    }

    public void setAutoStatus(AutoStatus autoStatus) {
        this.autoStatus = autoStatus;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", autoStatus=" + autoStatus +
                '}';
    }
}