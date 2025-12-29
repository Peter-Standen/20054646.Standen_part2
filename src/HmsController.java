import javax.swing.JOptionPane;
import java.util.Date;

/**
 * Main Controller class that coordinates between Model and View
 */
public class HmsController {

    private HmsModel model;
    private HmsView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;

        // Role selection / navigation
        view.setSelectRoleListener(new SelectRoleListener() {
            public void onSelectRole(String role) { handleSelectRole(role); }
        });
        view.setBackToRoleSelectListener(new BackToRoleSelectListener() {
            public void onBack() { view.showRoleSelectView(); }
        });

        // Admin load buttons
        view.setLoadPatientsListener(new Runnable() {
            public void run() { handleLoadPatients(); }
        });
        view.setLoadReferralsListener(new Runnable() {
            public void run() { handleLoadReferrals(); }
        });
        view.setLoadPrescriptionsListener(new Runnable() {
            public void run() { handleLoadPrescriptions(); }
        });
        view.setLoadAppointmentsListener(new Runnable() {
            public void run() { handleLoadAppointments(); }
        });

        // Create buttons
        view.setCreateReferralListener(new Runnable() {
            public void run() { handleCreateReferral(); }
        });
        view.setCreatePrescriptionListener(new Runnable() {
            public void run() { handleCreatePrescription(); }
        });
        view.setCreateAppointmentListener(new Runnable() {
            public void run() { handleCreateAppointment(); }
        });

        // Edit referral (admin and consultant)
        view.setEditReferralListener(new Runnable() {
            public void run() { handleEditReferral(); }
        });

        // Edit prescription (admin and consultant)
        view.setEditPrescriptionListener(new Runnable() {
            public void run() { handleEditPrescription(); }
        });

        // Print selected to file (consultant)
        view.setPrintSelectedReferralListener(new Runnable() {
            public void run() { handlePrintSelectedReferral(); }
        });
        view.setPrintSelectedPrescriptionListener(new Runnable() {
            public void run() { handlePrintSelectedPrescription(); }
        });

        // Close behaviour
        view.setOnCloseListener(new Runnable() {
            public void run() { handleClose(); }
        });
    }

    // ========== Role Selection Navigation Handlers ==========

    private void handleSelectRole(String role) {
        if ("ADMIN".equals(role)) {
            view.showAdminView();
        } else if ("CONSULTANT".equals(role)) {
            view.showConsultantView();
        } else if ("PATIENT".equals(role)) {
            view.showPatientView();
        } else {
            view.showRoleSelectView();
        }
    }

    // ========== Patient Management Handlers ==========

    private void handleLoadPatients() {
        view.showPatients(model.getAllPatients());
    }

    // ========== Referral Management Handlers ==========

    private void handleLoadReferrals() {
        view.showReferrals(model.getAllReferrals());
    }

    private void handleCreateReferral() {
        HmsView.ReferralInput input = view.promptForReferral();
        if (input == null) return;

        Integer urgency = null;
        try { urgency = Integer.parseInt(input.urgency.trim()); }
        catch (Exception ignored) { urgency = null; }

        String referralId = model.generateReferralId();

        Referral referral = new Referral(
                referralId,
                input.patientId,
                "",
                "",
                input.facilityId,
                "",
                new Date(),
                urgency,
                "",
                input.summary,
                "",
                "In Progress",
                "",
                "",
                new Date(),
                new Date(),
                null
        );

        model.addReferral(referral);
        handleLoadReferrals();
    }

    private void handleEditReferral() {
        if (view.getTableColumnCount() == 0 || !"Referral ID".equals(view.getTableColumnName(0))) {
            JOptionPane.showMessageDialog(view, "Please load referrals first, then select a referral row.");
            return;
        }

        String referralId = view.getSelectedIdFromTable(0);
        if (referralId == null || referralId.trim().isEmpty()) return;

        Referral referral = model.getReferralById(referralId);
        if (referral == null) {
            JOptionPane.showMessageDialog(view, "Referral not found: " + referralId);
            return;
        }

        String newStatus = JOptionPane.showInputDialog(
                view,
                "Enter new status for Referral " + referralId + ":",
                referral.getStatus()
        );
        if (newStatus == null) return;

        referral.setStatus(newStatus.trim());
        referral.setLastUpdated(new Date());

        model.updateReferral(referral);
        handleLoadReferrals();
    }

    private void handlePrintSelectedReferral() {
        String referralId = view.getSelectedIdFromTable(0);
        if (referralId == null || referralId.trim().isEmpty()) return;

        Referral referral = model.getReferralById(referralId);
        if (referral == null) {
            JOptionPane.showMessageDialog(view, "Referral not found: " + referralId);
            return;
        }

        model.printReferralToFile(referral);
        JOptionPane.showMessageDialog(view, "Referral written to file.");
    }

    // ========== Prescription Management Handlers ==========

    private void handleLoadPrescriptions() {
        view.showPrescriptions(model.getAllPrescriptions());
    }

    private void handleCreatePrescription() {
        HmsView.PrescriptionInput input = view.promptForPrescription();
        if (input == null) return;

        String prescriptionId = model.generatePrescriptionId();

        Prescription prescription = Prescription.fromCSV(
                prescriptionId + "," +
                        input.patientId + "," +
                        "," +
                        "," +
                        "," +
                        input.medicationName + "," +
                        "," +
                        "," +
                        "," +
                        "," +
                        "," +
                        input.pharmacyName + "," +
                        input.status + "," +
                        "," +
                        ""
        );

        if (prescription == null) {
            JOptionPane.showMessageDialog(view, "Could not create prescription (invalid input).");
            return;
        }

        model.addPrescription(prescription);
        handleLoadPrescriptions();
    }

    private void handleEditPrescription() {
        if (view.getTableColumnCount() == 0 || !"Prescription ID".equals(view.getTableColumnName(0))) {
            JOptionPane.showMessageDialog(view, "Please load prescriptions first, then select a prescription row.");
            return;
        }

        String prescriptionId = view.getSelectedIdFromTable(0);
        if (prescriptionId == null || prescriptionId.trim().isEmpty()) return;

        Prescription prescription = model.getPrescriptionById(prescriptionId.trim());
        if (prescription == null) {
            JOptionPane.showMessageDialog(view, "Prescription not found: " + prescriptionId);
            return;
        }

        String current = (prescription.getPrescriptionStatus() == null)
                ? ""
                : prescription.getPrescriptionStatus().toString();

        String newStatus = JOptionPane.showInputDialog(
                view,
                "Enter new status for Prescription " + prescriptionId + " (Draft, Issued, Dispensed, Collected, Cancelled):",
                current
        );
        if (newStatus == null) return;
        if (newStatus.trim().isEmpty()) return;

        PrescriptionStatus prescriptionStatus;
        try {
            String normalised =
                    newStatus.trim().substring(0, 1).toUpperCase()
                            + newStatus.trim().substring(1).toLowerCase();

            prescriptionStatus = PrescriptionStatus.valueOf(normalised);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(view,
                    "Invalid status. Use: Draft, Issued, Dispensed, Collected, Cancelled");
            return;
        }

        prescription.setPrescriptionStatus(prescriptionStatus);
        model.updatePrescription(prescription);
        handleLoadPrescriptions();
    }

    private void handlePrintSelectedPrescription() {
        String prescriptionId = view.getSelectedIdFromTable(0);
        if (prescriptionId == null || prescriptionId.trim().isEmpty()) return;

        Prescription prescription = model.getPrescriptionById(prescriptionId);
        if (prescription == null) {
            JOptionPane.showMessageDialog(view, "Prescription not found: " + prescriptionId);
            return;
        }

        model.printPrescriptionToFile(prescription);
        JOptionPane.showMessageDialog(view, "Prescription written to file.");
    }

    // ========== Appointment Management Handlers ==========

    private void handleLoadAppointments() {
        view.showAppointments(model.getAllAppointments());
    }

    private void handleCreateAppointment() {
        HmsView.AppointmentInput input = view.promptForAppointment();
        if (input == null) return;

        String appointmentId = model.generateAppointmentId();

        Appointment a = Appointment.fromCSV(
                appointmentId + "," +
                        input.patientId + "," +
                        input.clinicianId + "," +
                        "," +
                        input.date + "," +
                        "," +
                        "," +
                        "," +
                        "NEW," +
                        "," +
                        "," +
                        "," +
                        ""
        );

        if (a == null) {
            JOptionPane.showMessageDialog(view, "Could not create appointment (invalid input).");
            return;
        }

        model.addAppointment(a);
        handleLoadAppointments();
    }

    private void handleClose() {
        model.saveAllData();
    }
}

// ========== Event Listener Interfaces ==========

interface SelectRoleListener {
    void onSelectRole(String role);
}

interface BackToRoleSelectListener {
    void onBack();
}
