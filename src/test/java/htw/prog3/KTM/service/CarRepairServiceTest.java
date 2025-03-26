package htw.prog3.KTM.service;

import htw.prog3.KTM.model.InvalidTransitionException;
import htw.prog3.KTM.model.car.CarStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarRepairServiceTest {

    private CarRepairService carRepairService;

    @BeforeEach
    void setUp() {
        carRepairService = new CarRepairService();
    }

    @Test
    void initialState_ShouldBeReceived() {
        // Assert
        assertEquals(CarStatus.RECEIVED, carRepairService.getCurrentCarStatus());
    }

    @Test
    void startAssessment_FromReceivedState_TransitionsToInAssessment() throws InvalidTransitionException {
        // Act
        carRepairService.startAssessment();
        
        // Assert
        assertEquals(CarStatus.IN_ASSESSMENT, carRepairService.getCurrentCarStatus());
    }

    @Test
    void startRepair_FromInAssessmentState_TransitionsToRepairInProgress() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment();
        
        // Act
        carRepairService.startRepair();
        
        // Assert
        assertEquals(CarStatus.REPAIR_IN_PROGRESS, carRepairService.getCurrentCarStatus());
    }

    @Test
    void completeRepair_FromRepairInProgressState_TransitionsToReadyForRepair() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment();
        carRepairService.startRepair();
        
        // Act
        carRepairService.completeRepair();
        
        // Assert
        assertEquals(CarStatus.READY_FOR_REPAIR, carRepairService.getCurrentCarStatus());
    }

    @Test
    void deliverCar_FromReadyForRepairState_TransitionsToDelivered() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment();
        carRepairService.startRepair();
        carRepairService.completeRepair();
        
        // Act
        carRepairService.deliverCar();
        
        // Assert
        assertEquals(CarStatus.DELIVERED, carRepairService.getCurrentCarStatus());
    }

    @Test
    void pauseRepair_FromRepairInProgressState_TransitionsToOnHold() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment();
        carRepairService.startRepair();
        
        // Act
        carRepairService.pauseRepair();
        
        // Assert
        assertEquals(CarStatus.ON_HOLD, carRepairService.getCurrentCarStatus());
    }

    @Test
    void startRepair_FromOnHoldState_TransitionsToRepairInProgress() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment();
        carRepairService.startRepair();
        carRepairService.pauseRepair();
        
        // Act
        carRepairService.startRepair();
        
        // Assert
        assertEquals(CarStatus.REPAIR_IN_PROGRESS, carRepairService.getCurrentCarStatus());
    }

    @Test
    void cancelRepair_FromOnHoldState_TransitionsToCancelled() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment();
        carRepairService.startRepair();
        carRepairService.pauseRepair();
        
        // Act
        carRepairService.cancelRepair();
        
        // Assert
        assertEquals(CarStatus.CANCELLED, carRepairService.getCurrentCarStatus());
    }

    @Test
    void startAssessment_FromInvalidState_ThrowsInvalidTransitionException() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment(); // Now in IN_ASSESSMENT state
        
        // Act & Assert
        assertThrows(InvalidTransitionException.class, () -> carRepairService.startAssessment());
    }

    @Test
    void completeRepair_FromInvalidState_ThrowsInvalidTransitionException() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment(); // Now in IN_ASSESSMENT state
        
        // Act & Assert
        assertThrows(InvalidTransitionException.class, () -> carRepairService.completeRepair());
    }

    @Test
    void deliverCar_FromInvalidState_ThrowsInvalidTransitionException() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment(); // Now in IN_ASSESSMENT state
        
        // Act & Assert
        assertThrows(InvalidTransitionException.class, () -> carRepairService.deliverCar());
    }

    @Test
    void pauseRepair_FromInvalidState_ThrowsInvalidTransitionException() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment(); // Now in IN_ASSESSMENT state
        
        // Act & Assert
        assertThrows(InvalidTransitionException.class, () -> carRepairService.pauseRepair());
    }

    @Test
    void cancelRepair_FromInvalidState_ThrowsInvalidTransitionException() throws InvalidTransitionException {
        // Arrange
        carRepairService.startAssessment(); // Now in IN_ASSESSMENT state
        
        // Act & Assert
        assertThrows(InvalidTransitionException.class, () -> carRepairService.cancelRepair());
    }
} 