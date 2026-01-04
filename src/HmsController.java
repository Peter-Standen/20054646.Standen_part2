import java.util.ArrayList;

/**
 *  Controller class that coordinates between Model and View
 */
public class HmsController {
    private HmsModel model;
    private HmsView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;

        initializeView();
        setupEventListeners();
    }

    private void initializeView() {
        handleRefreshPatients();
        handleRefreshReferrals();
        handleRefreshPrescriptions();
        handleRefreshAppointments();
    }

    private void setupEventListeners() {
        view.setRefreshPatientsListener(new Runnable() {
            public void run() {
                handleRefreshPatients(); }
        });

        view.setRefreshReferralsListener(new Runnable() {
            public void run() {
                handleRefreshReferrals(); }
        });

        view.setRefreshPrescriptionsListener(new Runnable() {
            public void run() {
                handleRefreshPrescriptions(); }
        });

        view.setRefreshAppointmentsListener(new Runnable() {
            public void run() {
                handleRefreshAppointments(); }
        });

        view.setCreateReferralListener(new CreateReferralListener() {
            public void onCreateReferral(String patientId, String referringFacilityId, String referredToFacilityId,
                                         String referringClinicianId, String referredToClinicianId, String urgency,
                                         String reason, String summary, String investigations, String appointmentId,
                                         String notes) {
                handleCreateReferral(patientId, referringFacilityId, referredToFacilityId, referringClinicianId,
                        referredToClinicianId, urgency, reason, summary, investigations, appointmentId, notes);
            }
        });

        view.setUpdateReferralListener(new UpdateReferralListener() {
            public void onUpdateReferralStatus(String referralId, String newStatus) {
                handleUpdateReferralStatus(referralId, newStatus);
            }
        });

        view.setPrintReferralListener(new PrintReferralListener() {
            public void onPrintReferral(String referralId) {
                handlePrintReferral(referralId);
            }
        });

        view.setCreatePrescriptionListener(new CreatePrescriptionListener() {
            public void onCreatePrescription(String patientId, String clinicianId, String appointmentId,
                                             String medicationName, String dosage, String frequency,
                                             String durationDays, String quantity, String instructions,
                                             String pharmacyName, String status){
                handleCreatePrescription(patientId, clinicianId, appointmentId, medicationName, dosage, frequency,
                        durationDays, quantity, instructions, pharmacyName, status);
            }
        });

        view.setUpdatePrescriptionListener(new UpdatePrescriptionListener() {
            public void onUpdatePrescriptionStatus(String prescriptionId, String newStatus) {
                handleUpdatePrescriptionStatus(prescriptionId, newStatus);
            }
        });

        view.setPrintPrescriptionListener(new PrintPrescriptionListener() {
            public void onPrintPrescription(String prescriptionId) {
                handlePrintPrescription(prescriptionId);
            }
        });

        view.setCreateAppointmentListener(new CreateAppointmentListener() {
            public void onCreateAppointment(String patientId, String clinicianId, String date) {
                handleCreateAppointment(patientId, clinicianId, date);
            }
        });

        view.setUpdateAppointmentListener(new UpdateAppointmentListener() {
            public void onUpdateAppointmentStatus(String appointmentId, String newStatus) {
                handleUpdateAppointmentStatus(appointmentId, newStatus);
            }
        });

        view.setCancelAppointmentListener(new CancelAppointmentListener() {
            public void onCancelAppointment(String appointmentId) {
                handleCancelAppointment(appointmentId);
            }
        });

        view.setOnCloseListener(new Runnable() {
            public void run() {
                handleSaveData();
            }
        });
    }

    // ========== Patient Management Handlers ==========
    private void handleRefreshPatients() {
        view.refreshPatientsTable(getAllPatients(), model);
    }

    // ========== Referral Management Handlers ==========

    private void handleRefreshReferrals() {
        view.refreshReferralsTable(getAllReferrals(), model);
    }

    private void handleCreateReferral(String patientId, String referringFacilityId, String referredToFacilityId,
                                      String referringClinicianId, String referredToClinicianId, String urgency,
                                      String reason, String summary, String investigations, String appointmentId,
                                      String notes) {

        boolean ok = model.createReferral(patientId, referringClinicianId, referredToClinicianId,
                referringFacilityId, referredToFacilityId,
                urgency, reason, summary, investigations, appointmentId, notes);

        if (ok) {
            view.showSuccessMessage("Referral created successfully!");
            handleRefreshReferrals();
        } else {
            view.showErrorMessage("Could not create referral (CSV parse failed)");
        }
    }

    private void handleUpdateReferralStatus(String referralId, String newStatus) {
        Referral referral = model.getReferralById(referralId);
        if (referral == null) {
            view.showErrorMessage("Referral not found: " + referralId);
            return;
        }

        try {
            referral.setStatus(newStatus);
        } catch (Exception e) {
            view.showErrorMessage("Could not update referral status: " + e.getMessage());
            return;
        }

        referral.setLastUpdated(new java.util.Date());
        model.updateReferral(referral);
        handleRefreshReferrals();
    }

    private void handlePrintReferral(String referralId) {
        Referral referral = model.getReferralById(referralId);
        if (referral == null) {
            view.showErrorMessage("Referral not found: " + referralId);
            return;
        }

        model.printReferralToFile(referral);
        view.showSuccessMessage("Referral printed to file.");
    }

    // ========== Prescription Management Handlers ==========
    private void handleCreatePrescription(String patientId, String clinicianId, String appointmentId,
                                          String medicationName, String dosage, String frequency, String durationDays,
                                          String quantity, String instructions, String pharmacyName, String status) {

        boolean ok = model.createPrescription(patientId, clinicianId, appointmentId,
                medicationName, dosage, frequency, durationDays,
                quantity, instructions, pharmacyName, status);

        if (ok) {
            view.showSuccessMessage("Prescription created successfully!");
            handleRefreshPrescriptions();
        } else {
            view.showErrorMessage("Could not create prescription (CSV parse failed)");
        }
    }

    private void handleUpdatePrescriptionStatus(String prescriptionId, String newStatus) {
        Prescription prescription = model.getPrescriptionById(prescriptionId);
        if (prescription == null) {
            view.showErrorMessage("Prescription not found: " + prescriptionId);
            return;
        }

        try {
            prescription.setPrescriptionStatus(
                    PrescriptionStatus.valueOf(newStatus)
            );
        } catch (Exception e) {
            view.showErrorMessage("Could not update prescription status: " + e.getMessage());
            return;
        }
        model.updatePrescription(prescription);
        handleRefreshPrescriptions();
    }

    private void handlePrintPrescription(String prescriptionId) {
        Prescription prescription = model.getPrescriptionById(prescriptionId);
        if (prescription == null) {
            view.showErrorMessage("Prescription not found: " + prescriptionId);
            return;
        }

        model.printPrescriptionToFile(prescription);
        view.showSuccessMessage("Prescription printed to file.");
    }

    private void handleRefreshPrescriptions() {
        view.refreshPrescriptionsTable(model.getAllPrescriptions(), model);
    }

    // ========== Appointment Management Handlers ==========
    private void handleRefreshAppointments() {
        view.refreshAppointmentsTable(getAllAppointments(), model);
    }

    private void handleCreateAppointment(String patientId, String clinicianId, String date) {

        boolean ok = model.createAppointment(patientId, clinicianId, date);

        if (ok) {
            view.showSuccessMessage("Appointment created successfully!");
            handleRefreshAppointments();
        } else {
            view.showErrorMessage("Could not create appointment (CSV parse failed)");
        }
    }

    private void handleUpdateAppointmentStatus(String appointmentId, String newStatus) {
        Appointment appointment = model.getAppointmentById(appointmentId);
        if (appointment == null) {
            view.showErrorMessage("Appointment not found: " + appointmentId);
            return;
        }

        try {
            appointment.setStatus(newStatus);
        } catch (Exception e) {
            view.showErrorMessage("Could not update appointment status: " + e.getMessage());
            return;
        }

        appointment.setLastModified(new java.util.Date());
        model.updateAppointment(appointment);
        handleRefreshAppointments();
    }

    private void handleCancelAppointment(String appointmentId) {
        boolean ok = model.cancelAppointment(appointmentId);
        if (!ok) {
            view.showErrorMessage("Could not cancel appointment: " + appointmentId);
            return;
        }
        handleRefreshAppointments();
    }

    // ========== Data Persistence ==========

    private void handleSaveData() {
        model.saveAllData();
    }

    // ========== Data Access Methods for View ==========

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<Patient>(model.getAllPatients());
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<Referral>(model.getAllReferrals());
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<Prescription>(model.getAllPrescriptions());
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<Appointment>(model.getAllAppointments());
    }

    public Referral getReferral(String referralId) {
        return model.getReferralById(referralId);
    }

    public Prescription getPrescription(String prescriptionId) {
        return model.getPrescriptionById(prescriptionId);
    }

    public Appointment getAppointment(String appointmentId) {
        return model.getAppointmentById(appointmentId);
    }
}

// ========== Event Listener Interfaces ==========

interface CreateReferralListener {
    void onCreateReferral(String patientId, String referringFacilityId, String referredToFacilityId,
                          String referringClinicianId, String referredToClinicianId, String urgency, String reason,
                          String summary, String investigations, String appointmentId, String notes);
}

interface UpdateReferralListener {
    void onUpdateReferralStatus(String referralId, String newStatus);
}

interface PrintReferralListener {
    void onPrintReferral(String referralId);
}

interface CreatePrescriptionListener {
    void onCreatePrescription(String patientId, String clinicianId, String appointmentId, String medicationName,
                              String dosage, String frequency, String durationDays, String quantity,
                              String instructions, String pharmacyName, String status);
}

interface UpdatePrescriptionListener {
    void onUpdatePrescriptionStatus(String prescriptionId, String newStatus);
}

interface PrintPrescriptionListener {
    void onPrintPrescription(String prescriptionId);
}

interface CreateAppointmentListener {
    void onCreateAppointment(String patientId, String clinicianId, String date);
}

interface UpdateAppointmentListener {
    void onUpdateAppointmentStatus(String appointmentId, String newStatus);
}

interface CancelAppointmentListener {
    void onCancelAppointment(String appointmentId);
}
