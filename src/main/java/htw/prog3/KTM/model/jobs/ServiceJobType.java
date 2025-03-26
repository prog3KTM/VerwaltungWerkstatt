package htw.prog3.KTM.model.jobs;

public enum ServiceJobType {
    OIL_CHANGE,
    TIRE_REPLACEMENT,
    SOFTWARE_UPDATE,  
    BRAKE_REPAIR,
    ENGINE_DIAGNOSTIC,
    GENERAL_MAINTENANCE;

    public static ServiceJobType fromString(String status) {
        try {
            return ServiceJobType.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown ServiceJobType: " + status);
        }
    }

    // Convert from enum to String
    public static String toString(ServiceJobType status) {
        return status.name();
    }

}
