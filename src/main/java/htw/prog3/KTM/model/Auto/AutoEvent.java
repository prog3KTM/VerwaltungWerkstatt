package htw.prog3.KTM.model.Auto;

public enum AutoEvent {
    RECEIVE_CAR,          // Car has been received
    START_ASSESSMENT,     // Assessment starts for the car
    START_REPAIR,         // Repair work officially starts
    COMPLETE_REPAIR,      // Car repair is complete
    INSPECT_CAR,          // Car goes for inspection after repair
    READY_FOR_PICKUP,     // Car is ready to be picked up by the customer
    DELIVER_CAR,          // Car is delivered to the customer
    PAUSE_REPAIR,         // Repair process is paused
    CANCEL_REPAIR         // Repair job is canceled
}
