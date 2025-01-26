package htw.prog3.KTM.model;


public interface StateMachine<TState, TEvent> {
    TState getCurrentState();

    void transition(TEvent event) throws InvalidTransitionException;
}

