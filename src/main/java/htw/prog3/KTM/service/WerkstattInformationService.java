package htw.prog3.KTM.service;

import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;
import htw.prog3.KTM.repository.WerkstattInformationRepository;
import htw.prog3.KTM.util.main;

public class WerkstattInformationService {

    private static WerkstattInformationService instance;
    private final WerkstattInformationRepository werkstattInformationRepository;

    private WerkstattInformationService() {
        werkstattInformationRepository = new WerkstattInformationRepository(main.getAppConfig().getDatabaseManager());
    }

    public String getName() {
        return werkstattInformationRepository.getWerkstattInformation().name();
    }

    public String getEmail() {
        return werkstattInformationRepository.getWerkstattInformation().email();
    }

    public String getLocation() {
        return werkstattInformationRepository.getWerkstattInformation().location();
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

    public WerkstattInformation getWerkstattInformation() {
        return werkstattInformationRepository.getWerkstattInformation();
    }

    public String getIban() {
        return werkstattInformationRepository.getWerkstattInformation().iban();
    }

    public void save(WerkstattInformation werkstattInformation) {
        werkstattInformationRepository.save(werkstattInformation);
    }

    public static WerkstattInformationService getInstance() {
        if (instance == null) { instance = new WerkstattInformationService(); }
        return instance;
    }

}
