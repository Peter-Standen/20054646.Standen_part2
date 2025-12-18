package HealthcareManagementSystem.service;

import HealthcareManagementSystem.data.AppointmentCsvRepository;
import HealthcareManagementSystem.data.PrescriptionCsvRepository;

public class PrescriptionService {

    private final PrescriptionCsvRepository prescriptionRepo;
    private final AppointmentCsvRepository appointmentRepo;

    public PrescriptionService(PrescriptionCsvRepository prescriptionRepo,
                               AppointmentCsvRepository appointmentRepo) {
        this.prescriptionRepo = prescriptionRepo;
        this.appointmentRepo = appointmentRepo;
    }
}
