package htw.prog3.KTM.controller;

import htw.prog3.KTM.model.InvalidTransitionException;
import htw.prog3.KTM.service.CarRepairService;

import java.util.logging.Logger;

public class CarRepairConsoleController {

    private final CarRepairService carRepairService;
    private static final Logger logger = Logger.getLogger(CarRepairConsoleController.class.getName());

    public CarRepairConsoleController() {
        this.carRepairService = new CarRepairService();
    }

    public void handleRepairJob() {
        try {
            logger.info("Initial car status: " + carRepairService.getCurrentCarStatus());

            carRepairService.startAssessment();
            logger.info("Car status after assessment: " + carRepairService.getCurrentCarStatus());

            carRepairService.startRepair();
            logger.info("Car status after repair started: " + carRepairService.getCurrentCarStatus());

            carRepairService.completeRepair();
            logger.info("Car status after repair completed: " + carRepairService.getCurrentCarStatus());

            carRepairService.deliverCar();
            logger.info("Car status after delivery: " + carRepairService.getCurrentCarStatus());

        } catch (InvalidTransitionException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CarRepairConsoleController controller = new CarRepairConsoleController();
        controller.handleRepairJob();
    }
}