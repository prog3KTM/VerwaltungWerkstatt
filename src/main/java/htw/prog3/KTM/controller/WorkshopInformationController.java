package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;
import htw.prog3.KTM.service.WorkshopinformationService;

public class WorkshopInformationController {

    private final WorkshopinformationService workshopinformationService;

    public WorkshopInformationController(DatabaseManager databaseManager) {
        this.workshopinformationService = new WorkshopinformationService(databaseManager);
    }

    public String getName() {
        return workshopinformationService.getName();
    }

    public String getEmail() {
        return workshopinformationService.getEmail();
    }

    public int getPhoneNumber() {
        return workshopinformationService.getPhone();
    }

    public String getAddress() {
        return workshopinformationService.getLocation();
    }

    public String getWebsite() {
        return workshopinformationService.getWebsite();
    }

    public String getVAT() {
        return workshopinformationService.getVat();
    }

    public String getBusinessNumber() {
        return workshopinformationService.getBusinessRegNumber();
    }

    public String getIban() {
        return workshopinformationService.getIban();
    }

    public WorkshopInformation getWerkstattInformation() {
        return workshopinformationService.getWerkstattInformation();
    }

    public void delete() {
        workshopinformationService.delete();
    }

    public boolean ifInformationExists() {
        return workshopinformationService.ifInformationExists();
    }

    public void save(WorkshopInformation workshopInformation) {
        workshopinformationService.save(workshopInformation);
    }

}
