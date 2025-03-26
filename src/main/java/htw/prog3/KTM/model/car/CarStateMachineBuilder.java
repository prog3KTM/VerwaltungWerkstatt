package htw.prog3.KTM.model.car;

import java.util.HashMap;
import java.util.Map;


public class CarStateMachineBuilder {

    private CarStatus initialState;
    private final Map<CarStatus, Map<CarEvent, CarStatus>> transitions;

    public CarStateMachineBuilder() {
        this.transitions = new HashMap<>();
    }


    public CarStateMachineBuilder withInitialState(CarStatus state) {
        this.initialState = state;
        return this;
    }


    public CarStateMachineBuilder addTransition(CarStatus fromState, CarEvent event, CarStatus toState) {
        this.transitions
                .computeIfAbsent(fromState, k -> new HashMap<>())
                .put(event, toState);
        return this;
    }


    public CarStateMachine build() {
        if (initialState == null) {
            throw new IllegalStateException("Initial state must be defined.");
        }
        return new CarStateMachine(initialState, transitions);
    }
}