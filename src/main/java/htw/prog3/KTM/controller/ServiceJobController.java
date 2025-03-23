package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.car.Car;
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

    public List<ServiceJob> getServiceJobsByCarId(int carId) {
        return serviceJobRepository.findByCarId(carId);
    }

    public void addServiceJob(ServiceJob serviceJob, int carId) {
        serviceJobRepository.save(serviceJob, carId);
    }

    public Optional<ServiceJob> getServiceJobById(int jobId) {
        return serviceJobRepository.findById(jobId);
    }

    public int getCarIdForServiceJob(int jobId) {
        return 1; // serviceJobRepository.getCarIdForServiceJob(jobId);
    }

    public void updateServiceJob(ServiceJob serviceJob, int carId) {
        serviceJobRepository.save(serviceJob, carId);
    }

    public void deleteServiceJobById(int jobId) {
        serviceJobRepository.deleteById(jobId);
    }

    public void deleteServiceJobsByCarId(int carId) {
        serviceJobRepository.deleteByCarId(carId);
    }
} 