package HealthcareManagementSystem.controller;

import HealthcareManagementSystem.service.PatientRecordService;
import HealthcareManagementSystem.service.PrescriptionService;
import HealthcareManagementSystem.service.ReferralsManager;
import HealthcareManagementSystem.view.AdminUI;

public class AdminController {

    private final AdminUI view;
    private final PatientRecordService patientService;
    private final PrescriptionService prescriptionService;
    private final ReferralsManager referralsManager;

    public AdminController(AdminUI view,
                           PatientRecordService patientService,
                           PrescriptionService prescriptionService,
                           ReferralsManager referralsManager) {
        this.view = view;
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.referralsManager = referralsManager;
    }
}
