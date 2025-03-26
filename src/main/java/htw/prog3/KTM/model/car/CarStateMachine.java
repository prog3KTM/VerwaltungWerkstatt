package htw.prog3.KTM.model.car;

import htw.prog3.KTM.model.InvalidTransitionException;
import htw.prog3.KTM.model.StateMachine;

import java.util.Map;


public class CarStateMachine implements StateMachine<CarStatus, CarEvent> {

    private CarStatus currentState;
    private final Map<CarStatus, Map<CarEvent, CarStatus>> transitions;


    public CarStateMachine(CarStatus initialState, Map<CarStatus, Map<CarEvent, CarStatus>> transitions) {
        this.currentState = initialState;
        this.transitions = transitions;
    }

    @Override
    public CarStatus getCurrentState() {
        return currentState;
    }

    @Override
    public void transition(CarEvent event) throws InvalidTransitionException {
        if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(event)) {
            currentState = transitions.get(currentState).get(event);
        } else {
            throw new InvalidTransitionException("Invalid transition: " + currentState + " -> " + event);
        }
    }
}