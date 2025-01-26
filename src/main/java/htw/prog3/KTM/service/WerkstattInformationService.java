package htw.prog3.KTM.service;

import htw.prog3.KTM.model.WerkstattInformation.WerkstattInformation;
import htw.prog3.KTM.repository.WerkstattInformationRepository;

public class WerkstattInformationService {

    private final WerkstattInformationRepository werkstattInformationRepository;

    public WerkstattInformationService(WerkstattInformationRepository werkstattInformationRepository) {
        this.werkstattInformationRepository = werkstattInformationRepository;
    }

    public String getName() {
        return werkstattInformationRepository.getWerkstattInformation().name();
    }

    public String getEmail() {
        return werkstattInformationRepository.getWerkstattInformation().email();
    }

    public String getLocation() {
        return werkstattInformationRepository.getWerkstattInformation().email();
    }

    public Integer getPhone() {
        return werkstattInformationRepository.getWerkstattInformation().phone();
    }

    public String getWebsite() {
        return werkstattInformationRepository.getWerkstattInformation().website();
    }

    public String getVat() {
        return werkstattInformationRepository.getWerkstattInformation().vat();
    }

    public String getBusinessRegNumber() {
        return werkstattInformationRepository.getWerkstattInformation().businessRegNumber();
    }

    public String getIban() {
        return werkstattInformationRepository.getWerkstattInformation().iban();
    }

    public void save(WerkstattInformation werkstattInformation) {
        werkstattInformationRepository.save(werkstattInformation);
    }

}
