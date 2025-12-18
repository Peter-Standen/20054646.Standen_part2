package HealthcareManagementSystem;

import HealthcareManagementSystem.controller.AdminController;
import HealthcareManagementSystem.data.AppointmentCsvRepository;
import HealthcareManagementSystem.data.ClinicianCsvRepository;
import HealthcareManagementSystem.data.PatientCsvRepository;
import HealthcareManagementSystem.data.PrescriptionCsvRepository;
import HealthcareManagementSystem.data.ReferralCsvRepository;
import HealthcareManagementSystem.service.PatientRecordService;
import HealthcareManagementSystem.service.PrescriptionService;
import HealthcareManagementSystem.service.ReferralsManager;
import HealthcareManagementSystem.view.AdminUI;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {

            PatientCsvRepository patientRepo = new PatientCsvRepository("data/patients.csv");
            ClinicianCsvRepository clinicianRepo = new ClinicianCsvRepository("data/clinicians.csv");
            AppointmentCsvRepository appointmentRepo = new AppointmentCsvRepository("data/appointments.csv");
            PrescriptionCsvRepository prescriptionRepo = new PrescriptionCsvRepository("data/prescriptions.csv");
            ReferralCsvRepository referralRepo = new ReferralCsvRepository("data/referrals.csv");

            PatientRecordService patientService = new PatientRecordService(patientRepo);
            PrescriptionService prescriptionService = new PrescriptionService(prescriptionRepo, appointmentRepo);
            ReferralsManager referralsManager = ReferralsManager.getInstance(referralRepo);

            AdminUI view = new AdminUI();
            AdminController controller = new AdminController(view, patientService, prescriptionService, referralsManager);

            view.setVisible(true);
        });
    }
}
