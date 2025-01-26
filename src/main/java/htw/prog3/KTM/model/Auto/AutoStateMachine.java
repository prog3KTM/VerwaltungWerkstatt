package htw.prog3.KTM.model.Auto;

import htw.prog3.KTM.model.InvalidTransitionException;
import htw.prog3.KTM.model.StateMachine;

import java.util.Map;

// CarStateMachine using the fluent builder configuration
public class AutoStateMachine implements StateMachine<AutoStatus, AutoEvent> {

    private AutoStatus currentState;
    private final Map<AutoStatus, Map<AutoEvent, AutoStatus>> transitions;

    // Constructor with initial state and transitions from the builder
    public AutoStateMachine(AutoStatus initialState, Map<AutoStatus, Map<AutoEvent, AutoStatus>> transitions) {
        this.currentState = initialState;
        this.transitions = transitions;
    }

    @Override
    public  AutoStatus getCurrentState() {
        return currentState;
    }

    @Override
    public void transition(AutoEvent event) throws InvalidTransitionException {
        if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(event)) {
            currentState = transitions.get(currentState).get(event);
        } else {
            throw new InvalidTransitionException("Invalid transition: " + currentState + " -> " + event);
        }
    }
}