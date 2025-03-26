package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.jobs.RepairJob;
import htw.prog3.KTM.model.jobs.ServiceJob;
import htw.prog3.KTM.repository.ServiceJobRepository;

import java.util.List;
import java.util.Optional;

public class RepairJobController {
    private final ServiceJobRepository serviceJobRepository;

    public RepairJobController(DatabaseManager databaseManager) {
        this.serviceJobRepository = new ServiceJobRepository(databaseManager);
    }

    public List<RepairJob> getAllRepairJobs() {
        return serviceJobRepository.findAllRepairJobs();
    }

    public List<RepairJob> getRepairJobsByCarId(String carId) {
        return serviceJobRepository.findRepairJobByCarId(carId);
    }

    public void addRepairJob(RepairJob repairJob, String carId) {
        serviceJobRepository.save(repairJob, carId);
    }

    public Optional<RepairJob> getRepairJobById(int jobId) {
        return serviceJobRepository.findRepairJobById(jobId);
    }

    public String getCarIdForRepairJob(int jobId) {
        return serviceJobRepository.getCarIdForServiceJob(jobId);
    }

    public void updateRepairJob(RepairJob serviceJob, String carId) {
        serviceJobRepository.save(serviceJob, carId);
    }

    public void deleteRepairJobById(int jobId) {
        serviceJobRepository.deleteById(jobId);
    }

    public void deleteRepairJobsByCarId(String carId) {
        serviceJobRepository.deleteByCarId(carId);
    }
} 