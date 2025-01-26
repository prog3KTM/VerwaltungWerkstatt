package htw.prog3.KTM.model.Auto;

public class Auto {
    private int id;
    private String model;
    private String brand;
    private String licensePlate;
    private AutoStatus autoStatus;

    public Auto(int id, String model, String brand, String licensePlate, AutoStatus autoStatus) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.autoStatus = autoStatus;
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

    public AutoStatus getAutoStatus() {
        return autoStatus;
    }

    public void setAutoStatus(AutoStatus autoStatus) {
        this.autoStatus = autoStatus;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", autostatus='" + autoStatus + '\'' +
                '}';
    }
}