package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;
import htw.prog3.KTM.service.WerkstattInformationService;

public class WerkstattInformationController {

    private final WerkstattInformationService werkstattInformationService;

    public WerkstattInformationController(DatabaseManager databaseManager) {
        this.werkstattInformationService = new WerkstattInformationService(databaseManager);
    }

    public String getName() {
        return werkstattInformationService.getName();
    }

    public String getEmail() {
        return werkstattInformationService.getEmail();
    }

    public int getPhoneNumber() {
        return werkstattInformationService.getPhone();
    }

    public String getAddress() {
        return werkstattInformationService.getLocation();
    }

    public String getWebsite() {
        return werkstattInformationService.getWebsite();
    }

    public String getVAT() {
        return werkstattInformationService.getVat();
    }

    public String getBusinessNumber() {
        return werkstattInformationService.getBusinessRegNumber();
    }

    public String getIban() {
        return werkstattInformationService.getIban();
    }

    public WerkstattInformation getWerkstattInformation() {
        return werkstattInformationService.getWerkstattInformation();
    }

    public void delete() {
        werkstattInformationService.delete();
    }

    public boolean ifInformationExists() {
        return werkstattInformationService.ifInformationExists();
    }

    public void save(WerkstattInformation werkstattInformation) {
        werkstattInformationService.save(werkstattInformation);
    }

}
