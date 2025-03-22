package htw.prog3.KTM.model.jobs;

public enum RepairJobType {
    ENGINE_REPAIR,        // Fixing engine-related issues
    TRANSMISSION_FIX,     // Repairing transmission problems
    BRAKE_REPLACEMENT,    // Replacing brakes
    SUSPENSION_REPAIR,    // Fixing suspension system
    EXHAUST_SYSTEM_FIX,   // Repairing exhaust issues
    ELECTRICAL_REPAIR,    // Fixing wiring and electronics
    COOLING_SYSTEM_REPAIR; // Fixing radiator and cooling issues

    public static RepairJobType fromString(String status) {
        try {
            return RepairJobType.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown RepairJobType: " + status);
        }
    }

    // Convert from enum to String
    public static String toString(RepairJobType status) {
        return status.name();
    }
}
