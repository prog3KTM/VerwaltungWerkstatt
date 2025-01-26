package htw.prog3.KTM.controller;

import htw.prog3.KTM.model.InvalidTransitionException;
import htw.prog3.KTM.service.AutoReparaturService;

import java.util.logging.Logger;

public class CarRepairConsoleController {

    private final AutoReparaturService autoReparaturService;
    private static final Logger logger = Logger.getLogger(CarRepairConsoleController.class.getName());

    public CarRepairConsoleController() {
        this.autoReparaturService = new AutoReparaturService();
    }

    public void handleRepairJob() {
        try {
            logger.info("Initial car status: " + autoReparaturService.getCurrentCarStatus());

            autoReparaturService.startAssessment();
            logger.info("Car status after assessment: " + autoReparaturService.getCurrentCarStatus());

            autoReparaturService.startRepair();
            logger.info("Car status after repair started: " + autoReparaturService.getCurrentCarStatus());

            autoReparaturService.completeRepair();
            logger.info("Car status after repair completed: " + autoReparaturService.getCurrentCarStatus());

            autoReparaturService.deliverCar();
            logger.info("Car status after delivery: " + autoReparaturService.getCurrentCarStatus());

        } catch (InvalidTransitionException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CarRepairConsoleController controller = new CarRepairConsoleController();
        controller.handleRepairJob();
    }
}