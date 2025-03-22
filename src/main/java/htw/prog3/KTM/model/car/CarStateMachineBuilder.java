package htw.prog3.KTM.model.car;

import java.util.HashMap;
import java.util.Map;

// Fluent Builder for CarStateMachine
public class CarStateMachineBuilder {

    private CarStatus initialState;
    private final Map<CarStatus, Map<CarEvent, CarStatus>> transitions;

    public CarStateMachineBuilder() {
        this.transitions = new HashMap<>();
    }

    // Set the initial state of the car
    public CarStateMachineBuilder withInitialState(CarStatus state) {
        this.initialState = state;
        return this;
    }

    // Add a state transition (fromState -> event -> toState)
    public CarStateMachineBuilder addTransition(CarStatus fromState, CarEvent event, CarStatus toState) {
        this.transitions
                .computeIfAbsent(fromState, k -> new HashMap<>())
                .put(event, toState);
        return this;
    }

    // Build and return the CarStateMachine
    public CarStateMachine build() {
        if (initialState == null) {
            throw new IllegalStateException("Initial state must be defined.");
        }
        return new CarStateMachine(initialState, transitions);
    }
}