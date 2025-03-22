package htw.prog3.KTM.service;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;
import htw.prog3.KTM.repository.WorkshopInformationRepository;

public class WorkshopinformationService {
    private final WorkshopInformationRepository workshopInformationRepository;

    public WorkshopinformationService(DatabaseManager databaseManager) {
        workshopInformationRepository = new WorkshopInformationRepository(databaseManager);
    }

    public String getName() {
        return workshopInformationRepository.getWerkstattInformation().name();
    }

    public String getEmail() {
        return workshopInformationRepository.getWerkstattInformation().email();
    }

    public String getLocation() {
        return workshopInformationRepository.getWerkstattInformation().location();
    }

    public Integer getPhone() {
        return workshopInformationRepository.getWerkstattInformation().phone();
    }

    public String getWebsite() {
        return workshopInformationRepository.getWerkstattInformation().website();
    }

    public String getVat() {
        return workshopInformationRepository.getWerkstattInformation().vat();
    }

    public String getBusinessRegNumber() {
        return workshopInformationRepository.getWerkstattInformation().businessRegNumber();
    }

    public WorkshopInformation getWerkstattInformation() {
        return workshopInformationRepository.getWerkstattInformation();
    }

    public String getIban() {
        return workshopInformationRepository.getWerkstattInformation().iban();
    }

    public void save(WorkshopInformation workshopInformation) {
        workshopInformationRepository.save(workshopInformation);
    }

    public boolean ifInformationExists() {
        return workshopInformationRepository.ifInformationExists();
    }

    public void delete() {
        workshopInformationRepository.delete();
    }
}
