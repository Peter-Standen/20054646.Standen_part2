public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {

            PatientCsvRepository patientRepo = new PatientCsvRepository("data/patients.csv");
            ClinicianCsvRepository clinicianRepo = new ClinicianCsvRepository("data/clinicians.csv");
            AppointmentCsvRepository appointmentRepo = new AppointmentCsvRepository("data/appointments.csv");
            PrescriptionCsvRepository prescriptionRepo = new PrescriptionCsvRepository("data/prescriptions.csv");
            ReferralCsvRepository referralRepo = new ReferralCsvRepository("data/referrals.csv");

            PrescriptionService prescriptionService = new PrescriptionService(prescriptionRepo, appointmentRepo);
            ReferralsManager referralsManager = ReferralsManager.getInstance(referralRepo); // Singleton

            AdminUI view = new AdminUI();
            AdminController controller = new AdminController(view, patientRepo, clinicianRepo, appointmentRepo, prescriptionService, referralsManager);

            view.setVisible(true);
        });
    }
}
