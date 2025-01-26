package htw.prog3.KTM.controller;

import htw.prog3.KTM.model.WerkstattInformation.WerkstattInformation;
import htw.prog3.KTM.service.WerkstattInformationService;

public class WerkstattInformationController {


    private final WerkstattInformationService werkstattInformationService;

    public WerkstattInformationController(WerkstattInformationService werkstattInformationService) {
        this.werkstattInformationService = werkstattInformationService;
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

    public void save(WerkstattInformation werkstattInformation) {
        werkstattInformationService.save(werkstattInformation);
    }

}
