package htw.prog3.KTM.model.Auto;

import java.util.HashMap;
import java.util.Map;

// Fluent Builder for CarStateMachine
public class AutoStateMachineBuilder {

    private AutoStatus initialState;
    private final Map<AutoStatus, Map<AutoEvent, AutoStatus>> transitions;

    public AutoStateMachineBuilder() {
        this.transitions = new HashMap<>();
    }

    // Set the initial state of the car
    public AutoStateMachineBuilder withInitialState(AutoStatus state) {
        this.initialState = state;
        return this;
    }

    // Add a state transition (fromState -> event -> toState)
    public AutoStateMachineBuilder addTransition(AutoStatus fromState, AutoEvent event, AutoStatus toState) {
        this.transitions
                .computeIfAbsent(fromState, k -> new HashMap<>())
                .put(event, toState);
        return this;
    }

    // Build and return the CarStateMachine
    public AutoStateMachine build() {
        if (initialState == null) {
            throw new IllegalStateException("Initial state must be defined.");
        }
        return new AutoStateMachine(initialState, transitions);
    }
}