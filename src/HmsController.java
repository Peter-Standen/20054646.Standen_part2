import javax.swing.JOptionPane;

/**
 * Main Controller class that coordinates between Model and View
 */
public class HmsController {

    private HmsModel model;
    private HmsView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;

        // this initialises the UI first, then wires the listeners
        initializeView();
        setupEventListeners();
    }

    // this initialises the method
    private void initializeView() {
        handleRefreshPatients();
        handleRefreshReferrals();
        handleRefreshPrescriptions();
        handleRefreshAppointments();
    }

    // and the listener wiring method
    private void setupEventListeners() {

        // refresh buttons
        view.setRefreshPatientsListener(new Runnable() {
            public void run() { handleRefreshPatients(); }
        });

        view.setRefreshReferralsListener(new Runnable() {
            public void run() { handleRefreshReferrals(); }
        });

        view.setRefreshPrescriptionsListener(new Runnable() {
            public void run() { handleRefreshPrescriptions(); }
        });

        view.setRefreshAppointmentsListener(new Runnable() {
            public void run() { handleRefreshAppointments(); }
        });

        // referral actions
        view.setCreateReferralListener(new HmsView.CreateReferralListener() {
            public void onCreateReferral(String patientId,
                                         String referringFacilityId,
                                         String referredToFacilityId,
                                         String referringClinicianId,
                                         String referredToClinicianId,
                                         String urgency,
                                         String reason,
                                         String summary) {
                handleCreateReferral(patientId, referringFacilityId, referredToFacilityId,
                        referringClinicianId, referredToClinicianId, urgency, reason, summary);
            }
        });

        view.setUpdateReferralListener(new HmsView.UpdateReferralListener() {
            public void onUpdateReferralStatus(String referralId, String newStatus) {
                handleUpdateReferralStatus(referralId, newStatus);
            }
        });

        view.setPrintReferralListener(new HmsView.PrintReferralListener() {
            public void onPrintReferral(String referralId) {
                handlePrintReferral(referralId);
            }
        });

        // prescription actions
        view.setCreatePrescriptionListener(new HmsView.CreatePrescriptionListener() {
            public void onCreatePrescription(String patientId,
                                             String clinicianId,
                                             String appointmentId,
                                             String medicationName,
                                             String pharmacyName,
                                             String status) {
                handleCreatePrescription(patientId, clinicianId, appointmentId, medicationName, pharmacyName, status);
            }
        });

        view.setUpdatePrescriptionListener(new HmsView.UpdatePrescriptionListener() {
            public void onUpdatePrescriptionStatus(String prescriptionId, String newStatus) {
                handleUpdatePrescriptionStatus(prescriptionId, newStatus);
            }
        });

        view.setPrintPrescriptionListener(new HmsView.PrintPrescriptionListener() {
            public void onPrintPrescription(String prescriptionId) {
                handlePrintPrescription(prescriptionId);
            }
        });

        // appointment actions
        view.setCreateAppointmentListener(new HmsView.CreateAppointmentListener() {
            public void onCreateAppointment(String patientId, String clinicianId, String date) {
                handleCreateAppointment(patientId, clinicianId, date);
            }
        });

        view.setUpdateAppointmentListener(new HmsView.UpdateAppointmentListener() {
            public void onUpdateAppointmentStatus(String appointmentId, String newStatus) {
                handleUpdateAppointmentStatus(appointmentId, newStatus);
            }
        });

        view.setCancelAppointmentListener(new HmsView.CancelAppointmentListener() {
            public void onCancelAppointment(String appointmentId) {
                handleCancelAppointment(appointmentId);
            }
        });

        // close listener
        view.setOnCloseListener(new Runnable() {
            public void run() {
                model.saveAllData();
            }
        });
    }

    // =========================
    // Refresh handlers
    // =========================

    private void handleRefreshPatients() {
        view.refreshPatientsTable(model.getAllPatients(), model);
    }

    private void handleRefreshReferrals() {
        view.refreshReferralsTable(model.getAllReferrals(), model);
    }

    private void handleRefreshPrescriptions() {
        view.refreshPrescriptionsTable(model.getAllPrescriptions(), model);
    }

    private void handleRefreshAppointments() {
        view.refreshAppointmentsTable(model.getAllAppointments(), model);
    }

    // =========================
    // Referral handlers
    // =========================

    private void handleCreateReferral(String patientId,
                                      String referringFacilityId,
                                      String referredToFacilityId,
                                      String referringClinicianId,
                                      String referredToClinicianId,
                                      String urgency,
                                      String reason,
                                      String summary) {

        String referralId = model.generateReferralId();

        Referral referral = model.createBasicReferral(referralId,
                patientId, referringClinicianId, referredToClinicianId,
                referringFacilityId, referredToFacilityId,
                urgency, reason, summary);

        if (referral == null) {
            JOptionPane.showMessageDialog(view, "Could not create referral (CSV parse failed)");
            return;
        }

        model.addReferral(referral);
        handleRefreshReferrals();
    }

    private void handleUpdateReferralStatus(String referralId, String newStatus) {
        Referral referral = model.getReferralById(referralId);
        if (referral == null) {
            JOptionPane.showMessageDialog(view, "Referral not found: " + referralId);
            return;
        }

        try {
            referral.setStatus(newStatus);
        } catch (Exception e) {

        }

        referral.setLastUpdated(new java.util.Date());
        model.updateReferral(referral);
        handleRefreshReferrals();
    }

    private void handlePrintReferral(String referralId) {
        Referral referral = model.getReferralById(referralId);
        if (referral == null) {
            JOptionPane.showMessageDialog(view, "Referral not found: " + referralId);
            return;
        }

        model.printReferralToFile(referral);
        JOptionPane.showMessageDialog(view, "Referral printed to file.");
    }

    // =========================
    // Prescription handlers
    // =========================

    private void handleCreatePrescription(String patientId,
                                          String clinicianId,
                                          String appointmentId,
                                          String medicationName,
                                          String pharmacyName,
                                          String status) {

        String prescriptionId = model.generatePrescriptionId();

        Prescription prescription = model.createBasicPrescription(
                prescriptionId, patientId, clinicianId, appointmentId, medicationName, pharmacyName, status
        );

        if (prescription == null) {
            JOptionPane.showMessageDialog(view, "Could not create prescription (CSV parse failed)");
            return;
        }

        model.addPrescription(prescription);
        handleRefreshPrescriptions();
    }

    private void handleUpdatePrescriptionStatus(String prescriptionId, String newStatus) {
        Prescription prescription = model.getPrescriptionById(prescriptionId);
        if (prescription == null) {
            JOptionPane.showMessageDialog(view, "Prescription not found: " + prescriptionId);
            return;
        }

    }

    private void handlePrintPrescription(String prescriptionId) {
        Prescription prescription = model.getPrescriptionById(prescriptionId);
        if (prescription == null) {
            JOptionPane.showMessageDialog(view, "Prescription not found: " + prescriptionId);
            return;
        }

        model.printPrescriptionToFile(prescription);
        JOptionPane.showMessageDialog(view, "Prescription printed to file.");
    }

    // =========================
    // Appointment handlers
    // =========================

    private void handleCreateAppointment(String patientId, String clinicianId, String date) {
        String appointmentId = model.generateAppointmentId();

        Appointment appointment = model.createBasicAppointment(appointmentId, patientId, clinicianId, date);
        if (appointment == null) {
            JOptionPane.showMessageDialog(view, "Could not create appointment (CSV parse failed)");
            return;
        }

        model.addAppointment(appointment);
        handleRefreshAppointments();
    }

    private void handleUpdateAppointmentStatus(String appointmentId, String newStatus) {
        Appointment appointment = model.getAppointmentById(appointmentId);
        if (appointment == null) {
            JOptionPane.showMessageDialog(view, "Appointment not found: " + appointmentId);
            return;
        }

        try {
            appointment.setStatus(newStatus);
        } catch (Exception e) {
        }

        appointment.setLastModified(new java.util.Date());
        model.updateAppointment(appointment);
        handleRefreshAppointments();
    }

    private void handleCancelAppointment(String appointmentId) {
        boolean ok = model.cancelAppointment(appointmentId);
        if (!ok) {
            JOptionPane.showMessageDialog(view, "Could not cancel appointment: " + appointmentId);
            return;
        }
        handleRefreshAppointments();
    }
}
