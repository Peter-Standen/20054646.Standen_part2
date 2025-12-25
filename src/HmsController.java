import java.util.ArrayList;

/**
 * Controller class that coordinates between Model and View
 *
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
        view.showRoleSelectView();
    }

    private void setupEventListeners() {

        view.setSelectRoleListener(new SelectRoleListener() {
            public void onSelectRole(String roleName) {
                handleSelectRole(roleName);
            }
        });

        view.setBackToRoleSelectListener(new BackToRoleSelectListener() {
            public void onBack() {
                view.showRoleSelectView();
            }
        });

        view.setLoadPatientsListener(new Runnable() {
            public void run() {
                handleLoadPatients();
            }
        });

        view.setLoadReferralsListener(new Runnable() {
            public void run() {
                handleLoadReferrals();
            }
        });

        view.setLoadPrescriptionsListener(new Runnable() {
            public void run() {
                handleLoadPrescriptions();
            }
        });

        view.setLoadAppointmentsListener(new Runnable() {
            public void run() {
                handleLoadAppointments();
            }
        });

        view.setCreateReferralListener(new Runnable() {
            public void run() {
                handleCreateReferral();
            }
        });

        view.setCreatePrescriptionListener(new Runnable() {
            public void run() {
                handleCreatePrescription();
            }
        });

        view.setCreateAppointmentListener(new Runnable() {
            public void run() {
                handleCreateAppointment();
            }
        });

        view.setOnCloseListener(new Runnable() {
            public void run() {
                handleSaveData();
            }
        });
    }

    private void handleSelectRole(String roleName) {
        if ("ADMIN".equals(roleName)) {
            view.showAdminView();
        } else if ("CONSULTANT".equals(roleName)) {
            view.showConsultantView();
        } else if ("PATIENT".equals(roleName)) {
            view.showPatientView();
        } else {
            view.showRoleSelectView();
        }
    }

    private void handleLoadPatients() {
        view.showPatients(model.getAllPatients());
    }

    private void handleLoadReferrals() {
        view.showReferrals(model.getAllReferrals());
    }

    private void handleLoadPrescriptions() {
        view.showPrescriptions(model.getAllPrescriptions());
    }

    private void handleLoadAppointments() {
        view.showAppointments(model.getAllAppointments());
    }

    private void handleCreateReferral() {
        ReferralInput in = view.promptForReferral();
        if (in == null) return;

        String referralId = model.generateReferralId();
        String patientId = in.patientId;

        String referringClinicianId = "C1";
        String referredToClinicianId = "";

        String referringFacilityId = "";
        String referredToFacilityId = in.facilityId;

        java.util.Date now = new java.util.Date();

        Integer urgencyLevel = parseUrgency(in.urgency);
        String referralReason = "General Referral";
        String clinicalSummary = in.summary;
        String requestedInvestigations = "";
        String status = "NEW";

        String appointmentId = "";
        String notes = "";

        java.util.Date createdDate = now;
        java.util.Date lastUpdated = now;

        CommunicationMethod communicationMethod = null;

        Referral r = new Referral(
                referralId,
                patientId,
                referringClinicianId,
                referredToClinicianId,
                referringFacilityId,
                referredToFacilityId,
                now,
                urgencyLevel,
                referralReason,
                clinicalSummary,
                requestedInvestigations,
                status,
                appointmentId,
                notes,
                createdDate,
                lastUpdated,
                communicationMethod
        );

        model.addReferral(r);
        view.showReferrals(model.getAllReferrals());
    }

    private void handleCreatePrescription() {
        PrescriptionInput in = view.promptForPrescription();
        if (in == null) return;

        String prescriptionId = model.generatePrescriptionId();

        // Creation using fromCSV
        String line = prescriptionId + "," + in.patientId + "," + in.medicationName + "," + in.status + "," + in.pharmacyName;
        Prescription p = Prescription.fromCSV(line);

        model.addPrescription(p);
        view.showPrescriptions(model.getAllPrescriptions());
    }

    private void handleCreateAppointment() {
        AppointmentInput in = view.promptForAppointment();
        if (in == null) return;

        String appointmentId = model.generateAppointmentId();
        String clinicianId = (in.clinicianId == null || in.clinicianId.trim().isEmpty()) ? "C1" : in.clinicianId.trim();

        // Creation using fromCSV
        String line = appointmentId + "," + in.patientId + "," + clinicianId + "," + in.date + "," + "BOOKED";
        Appointment a = Appointment.fromCSV(line);

        model.addAppointment(a);
        view.showAppointments(model.getAllAppointments());
    }

    private Integer parseUrgency(String s) {
        if (s == null) return 1;

        String x = s.trim().toLowerCase();
        if ("high".equals(x) || "3".equals(x)) return 3;
        if ("medium".equals(x) || "2".equals(x)) return 2;
        if ("low".equals(x) || "1".equals(x)) return 1;

        return 1;
    }

    private void handleSaveData() {
        model.saveAllData();
    }
}

/* ===== Listener Interfaces ===== */

interface SelectRoleListener {
    void onSelectRole(String roleName);
}

interface BackToRoleSelectListener {
    void onBack();
}
