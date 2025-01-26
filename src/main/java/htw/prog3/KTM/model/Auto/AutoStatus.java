package htw.prog3.KTM.model.Auto;

public enum AutoStatus{
    RECEIVED,            // Car has been dropped off at the shop
    IN_ASSESSMENT,       // Car is being inspected for repair needs
    READY_FOR_REPAIR,
    REPAIR_IN_PROGRESS,  // Car is ready for the repair work
    AWAITING_PICKUP,      // Car is ready for pickup
    DELIVERED,           // Car has been delivered to the customer
    ON_HOLD,             // Repair process is paused
    CANCELLED            // Repair process canceled
}