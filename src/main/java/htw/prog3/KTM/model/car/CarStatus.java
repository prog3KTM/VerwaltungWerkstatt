package htw.prog3.KTM.model.car;

public enum CarStatus {
    RECEIVED(0),            // Car has been dropped off at the shop
    IN_ASSESSMENT(1),       // Car is being inspected for repair needs
    READY_FOR_REPAIR(2),
    REPAIR_IN_PROGRESS(3),  // Car is ready for the repair work
    AWAITING_PICKUP(4),      // Car is ready for pickup
    DELIVERED(5),           // Car has been delivered to the customer
    ON_HOLD(6),             // Repair process is paused
    CANCELLED(7);     // Repair process canceled

    private final int value; 


    CarStatus(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }


    public static CarStatus fromValue(int value) {
        for (CarStatus status : CarStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid CarStatus value: " + value);
    }
}
