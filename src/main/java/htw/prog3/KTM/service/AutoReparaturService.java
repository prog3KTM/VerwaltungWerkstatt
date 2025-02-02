package htw.prog3.KTM.service;

import htw.prog3.KTM.model.auto.AutoEvent;
import htw.prog3.KTM.model.auto.AutoStatus;
import htw.prog3.KTM.model.auto.AutoStateMachine;
import htw.prog3.KTM.model.auto.AutoStateMachineBuilder;
import htw.prog3.KTM.model.InvalidTransitionException;

public class AutoReparaturService {

    private final AutoStateMachine autoStateMachine;

    public AutoReparaturService() {
        // Initialize the car state machine using the builder
        autoStateMachine = new AutoStateMachineBuilder()
                .withInitialState(AutoStatus.RECEIVED)
                .addTransition(AutoStatus.RECEIVED, AutoEvent.START_ASSESSMENT, AutoStatus.IN_ASSESSMENT)
                .addTransition(AutoStatus.IN_ASSESSMENT, AutoEvent.START_REPAIR, AutoStatus.REPAIR_IN_PROGRESS)
                .addTransition(AutoStatus.REPAIR_IN_PROGRESS, AutoEvent.COMPLETE_REPAIR, AutoStatus.READY_FOR_REPAIR)
                .addTransition(AutoStatus.READY_FOR_REPAIR, AutoEvent.DELIVER_CAR, AutoStatus.DELIVERED)
                .addTransition(AutoStatus.REPAIR_IN_PROGRESS, AutoEvent.PAUSE_REPAIR, AutoStatus.ON_HOLD)
                .addTransition(AutoStatus.ON_HOLD, AutoEvent.START_REPAIR, AutoStatus.REPAIR_IN_PROGRESS)
                .addTransition(AutoStatus.ON_HOLD, AutoEvent.CANCEL_REPAIR, AutoStatus.CANCELLED)
                .build();  // Build the state machine
    }

    public AutoStatus getCurrentCarStatus() {
        return autoStateMachine.getCurrentState();
    }

    public void startAssessment() throws InvalidTransitionException {
        autoStateMachine.transition(AutoEvent.START_ASSESSMENT);
    }

    public void startRepair() throws InvalidTransitionException {
        autoStateMachine.transition(AutoEvent.START_REPAIR);
    }

    public void completeRepair() throws InvalidTransitionException {
        autoStateMachine.transition(AutoEvent.COMPLETE_REPAIR);
    }

    public void deliverCar() throws InvalidTransitionException {
        autoStateMachine.transition(AutoEvent.DELIVER_CAR);
    }

    public void pauseRepair() throws InvalidTransitionException {
        autoStateMachine.transition(AutoEvent.PAUSE_REPAIR);
    }

    public void cancelRepair() throws InvalidTransitionException {
        autoStateMachine.transition(AutoEvent.CANCEL_REPAIR);
    }
}