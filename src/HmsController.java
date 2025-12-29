import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;

/**
 * Main Controller class for the Healthcare Management System.
 * Mirrors the Bookshop example:
 * - Wires View listeners
 * - Calls Model to load/save/update
 * - Instructs View what to show
 */
public class HmsController {

    private HmsModel model;
    private HmsView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;

        // Role selection / navigation
        view.setSelectRoleListener(new SelectRoleListener() {
            public void onSelectRole(String role) {
                handleSelectRole(role);
            }
        });

        view.setBackToRoleSelectListener(new BackToRoleSelectListener() {
            public void onBack() {
                view.showRoleSelectView();
            }
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

        // Edit referral (admin)
        view.setEditReferralListener(new Runnable() {
            public void run() { handleEditReferral(); }
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

    private void handleLoadPatients() {
        List<Patient> list = model.getAllPatients();
        view.showPatients(list);
    }

    private void handleLoadReferrals() {
        List<Referral> list = model.getAllReferrals();
        view.showReferrals(list);
    }

    private void handleLoadPrescriptions() {
        List<Prescription> list = model.getAllPrescriptions();
        view.showPrescriptions(list);
    }

    private void handleLoadAppointments() {
        List<Appointment> list = model.getAllAppointments();
        view.showAppointments(list);
    }

    private void handleCreateReferral() {
        HmsView.ReferralInput input = view.promptForReferral();
        if (input == null) return;

        Integer urgency = null;
        try { urgency = Integer.parseInt(input.urgency.trim()); }
        catch (Exception ignored) { urgency = null; }

        String referralId = model.generateReferralId();

        // Build a Referral with the info we have (remaining fields defaulted)
        Referral r = new Referral(
                referralId,
                input.patientId,
                "",                 // referringClinicianId
                "",                 // referredToClinicianId
                input.facilityId,   // referringFacilityId
                "",                 // referredToFacilityId
                new Date(),         // referralDate
                urgency,
                "",                 // referralReason
                input.summary,
                "",                 // requestedInvestigations
                "In Progress",      // status
                "",                 // appointmentId
                "",                 // notes
                new Date(),         // createdDate
                new Date(),         // lastUpdated
                null                // communicationMethod
        );

        model.addReferral(r);
        handleLoadReferrals();
    }

    private void handleCreatePrescription() {
        HmsView.PrescriptionInput input = view.promptForPrescription();
        if (input == null) return;

        String prescriptionId = model.generatePrescriptionId();

        Prescription p = Prescription.fromCSV(
                prescriptionId + "," +
                        input.patientId + "," +
                        "," +                  // clinicianId
                        "," +                  // appointmentId
                        "," +                  // prescriptionDate
                        input.medicationName + "," +
                        "," +                  // dosage
                        "," +                  // frequency
                        "," +                  // durationDays
                        "," +                  // quantity
                        "," +                  // instructions
                        input.pharmacyName + "," +
                        input.status + "," +
                        "," +                  // issueDate
                        ""                     // collectionDate
        );

        if (p == null) {
            JOptionPane.showMessageDialog(view, "Could not create prescription (invalid input).");
            return;
        }

        model.addPrescription(p);
        handleLoadPrescriptions();
    }

    private void handleCreateAppointment() {
        HmsView.AppointmentInput input = view.promptForAppointment();
        if (input == null) return;

        String appointmentId = model.generateAppointmentId();

        Appointment a = Appointment.fromCSV(
                appointmentId + "," +
                        input.patientId + "," +
                        input.clinicianId + "," +
                        "," +              // facilityId
                        input.date + "," +
                        "," +              // time
                        "," +              // duration
                        "," +              // type
                        "NEW," +           // status
                        "," +              // reason
                        "," +              // notes
                        "," +              // createdDate
                        ""                 // lastModified
        );

        if (a == null) {
            JOptionPane.showMessageDialog(view, "Could not create appointment (invalid input).");
            return;
        }

        model.addAppointment(a);
        handleLoadAppointments();
    }

    private void handleEditReferral() {
        if (view.getTableColumnCount() == 0 || !"Referral ID".equals(view.getTableColumnName(0))) {
            JOptionPane.showMessageDialog(view, "Please load referrals first, then select a referral row.");
            return;
        }

        String referralId = view.getSelectedIdFromTable(0);
        if (referralId == null || referralId.trim().isEmpty()) return;

        Referral r = model.getReferralById(referralId);
        if (r == null) {
            JOptionPane.showMessageDialog(view, "Referral not found: " + referralId);
            return;
        }

        String newStatus = JOptionPane.showInputDialog(view, "Enter new status for Referral " + referralId + ":", r.getStatus());
        if (newStatus == null) return;

        r.setStatus(newStatus.trim());
        r.setLastUpdated(new Date());

        model.updateReferral(r);
        handleLoadReferrals();
    }

    private void handlePrintSelectedReferral() {
        String referralId = view.getSelectedIdFromTable(0);
        if (referralId == null || referralId.trim().isEmpty()) return;

        Referral r = model.getReferralById(referralId);
        if (r == null) {
            JOptionPane.showMessageDialog(view, "Referral not found: " + referralId);
            return;
        }

        model.printReferralToFile(r);
        JOptionPane.showMessageDialog(view, "Referral written to file.");
    }

    private void handlePrintSelectedPrescription() {
        String prescriptionId = view.getSelectedIdFromTable(0);
        if (prescriptionId == null || prescriptionId.trim().isEmpty()) return;

        Prescription p = model.getPrescriptionById(prescriptionId);
        if (p == null) {
            JOptionPane.showMessageDialog(view, "Prescription not found: " + prescriptionId);
            return;
        }

        model.printPrescriptionToFile(p);
        JOptionPane.showMessageDialog(view, "Prescription written to file.");
    }

    private void handleClose() {
        // Same intent as Bookshop example: save on exit
        model.saveAllData();
    }
}

/**
 * Listener used by the View to signal the selected role.
 */
interface SelectRoleListener {
    void onSelectRole(String role);
}

/**
 * Listener used by the View to go back to role select.
 */
interface BackToRoleSelectListener {
    void onBack();
}
