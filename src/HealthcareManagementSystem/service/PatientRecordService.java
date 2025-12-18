package HealthcareManagementSystem.service;

import HealthcareManagementSystem.data.PatientCsvRepository;
import HealthcareManagementSystem.model.Patient;

import java.util.List;

public class PatientRecordService {

    private final PatientCsvRepository repo;

    public PatientRecordService(PatientCsvRepository repo) {
        this.repo = repo;
    }

    public List<Patient> getAllPatients() {
        return repo.loadAll();
    }
}
