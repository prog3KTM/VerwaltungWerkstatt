package htw.prog3.KTM.model.Auto;

public enum AutoStatus {
    RECEIVED(0),            // Car has been dropped off at the shop
    IN_ASSESSMENT(1),       // Car is being inspected for repair needs
    READY_FOR_REPAIR(2),
    REPAIR_IN_PROGRESS(3),  // Car is ready for the repair work
    AWAITING_PICKUP(4),      // Car is ready for pickup
    DELIVERED(5),           // Car has been delivered to the customer
    ON_HOLD(6),             // Repair process is paused
    CANCELLED(7);     // Repair process canceled

    private final int value; // Field to hold the integer value

    // Constructor to set the value
    AutoStatus(int value) {
        this.value = value;
    }

    // Getter to retrieve the value
    public int getValue() {
        return value;
    }

    // Static method to get an AutoStatus from an integer
    public static AutoStatus fromValue(int value) {
        for (AutoStatus status : AutoStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AutoStatus value: " + value);
    }
}
