package htw.prog3.KTM.service;

import htw.prog3.KTM.model.car.CarEvent;
import htw.prog3.KTM.model.car.CarStatus;
import htw.prog3.KTM.model.car.CarStateMachine;
import htw.prog3.KTM.model.car.CarStateMachineBuilder;
import htw.prog3.KTM.model.InvalidTransitionException;

public class CarRepairService {

    private final CarStateMachine carStateMachine;

    public CarRepairService() {
        // Initialize the car state machine using the builder
        carStateMachine = new CarStateMachineBuilder()
                .withInitialState(CarStatus.RECEIVED)
                .addTransition(CarStatus.RECEIVED, CarEvent.START_ASSESSMENT, CarStatus.IN_ASSESSMENT)
                .addTransition(CarStatus.IN_ASSESSMENT, CarEvent.START_REPAIR, CarStatus.REPAIR_IN_PROGRESS)
                .addTransition(CarStatus.REPAIR_IN_PROGRESS, CarEvent.COMPLETE_REPAIR, CarStatus.READY_FOR_REPAIR)
                .addTransition(CarStatus.READY_FOR_REPAIR, CarEvent.DELIVER_CAR, CarStatus.DELIVERED)
                .addTransition(CarStatus.REPAIR_IN_PROGRESS, CarEvent.PAUSE_REPAIR, CarStatus.ON_HOLD)
                .addTransition(CarStatus.ON_HOLD, CarEvent.START_REPAIR, CarStatus.REPAIR_IN_PROGRESS)
                .addTransition(CarStatus.ON_HOLD, CarEvent.CANCEL_REPAIR, CarStatus.CANCELLED)
                .build();  // Build the state machine
    }

    public CarStatus getCurrentCarStatus() {
        return carStateMachine.getCurrentState();
    }

    public void startAssessment() throws InvalidTransitionException {
        carStateMachine.transition(CarEvent.START_ASSESSMENT);
    }

    public void startRepair() throws InvalidTransitionException {
        carStateMachine.transition(CarEvent.START_REPAIR);
    }

    public void completeRepair() throws InvalidTransitionException {
        carStateMachine.transition(CarEvent.COMPLETE_REPAIR);
    }

    public void deliverCar() throws InvalidTransitionException {
        carStateMachine.transition(CarEvent.DELIVER_CAR);
    }

    public void pauseRepair() throws InvalidTransitionException {
        carStateMachine.transition(CarEvent.PAUSE_REPAIR);
    }

    public void cancelRepair() throws InvalidTransitionException {
        carStateMachine.transition(CarEvent.CANCEL_REPAIR);
    }
}