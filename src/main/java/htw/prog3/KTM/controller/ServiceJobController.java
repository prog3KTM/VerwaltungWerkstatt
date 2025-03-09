package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.jobs.ServiceJob;
import htw.prog3.KTM.repository.ServiceJobRepository;

import java.util.List;
import java.util.Optional;

public class ServiceJobController {
    private final ServiceJobRepository serviceJobRepository;

    public ServiceJobController(DatabaseManager databaseManager) {
        this.serviceJobRepository = new ServiceJobRepository(databaseManager);
    }

    public List<ServiceJob> getAllServiceJobs() {
        return serviceJobRepository.findAll();
    }

    public List<ServiceJob> getServiceJobsByAutoId(String autoId) {
        return serviceJobRepository.findByAutoId(autoId);
    }

    public void addServiceJob(ServiceJob serviceJob, String autoId) {
        serviceJobRepository.save(serviceJob, autoId);
    }

    public Optional<ServiceJob> getServiceJobById(int jobId) {
        return serviceJobRepository.findById(jobId);
    }

    public String getAutoIdForServiceJob(int jobId) {
        return serviceJobRepository.getAutoIdForServiceJob(jobId);
    }

    public void updateServiceJob(ServiceJob serviceJob, String autoId) {
        serviceJobRepository.save(serviceJob, autoId);
    }

    public void deleteServiceJobById(int jobId) {
        serviceJobRepository.deleteById(jobId);
    }

    public void deleteServiceJobsByAutoId(String autoId) {
        serviceJobRepository.deleteByAutoId(autoId);
    }
} 