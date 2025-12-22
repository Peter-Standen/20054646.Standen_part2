import java.util.ArrayList;

/**
 * Controller class that coordinates between Model and View
 */
public class HmsController {
    private HmsModel model;
    private HmsView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;

        initializeView();
    }

    private void initializeView() {

        // Role selection
        view.setSelectRoleListener(new SelectRoleListener() {
            public void onSelectRole(String roleName) {
                handleSelectRole(roleName);
            }
        });

        // Back navigation (role screens -> role select)
        view.setBackToRoleSelectListener(new BackToRoleSelectListener() {
            public void onBack() {
                view.showRoleSelectView();
            }
        });

        // Data loading actions
        view.setLoadPatientsListener(new LoadPatientsListener() {
            public void onLoadPatients() {
                handleLoadPatients();
            }
        });

        view.setLoadReferralsListener(new LoadReferralsListener() {
            public void onLoadReferrals() {
                handleLoadReferrals();
            }
        });

        view.setLoadPrescriptionsListener(new LoadPrescriptionsListener() {
            public void onLoadPrescriptions() {
                handleLoadPrescriptions();
            }
        });

        // Close hook
        view.setOnCloseListener(new OnCloseListener() {
            public void onClose() {
                handleSaveData();
            }
        });
    }

    // ========== Role Navigation ==========

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

    // ========== Load / Show Data ==========

    private void handleLoadPatients() {
        ArrayList<Patient> patients = model.getAllPatients();
        view.showPatients(patients);
    }

    private void handleLoadReferrals() {
        ArrayList<Referral> referrals = model.getAllReferrals();
        view.showReferrals(referrals);
    }

    private void handleLoadPrescriptions() {
        ArrayList<Prescription> prescriptions = model.getAllPrescriptions();
        view.showPrescriptions(prescriptions);
    }

    // ========== Data Persistence ==========

    private void handleSaveData() {
        model.saveAllData();
    }

    // ========== Data Access Methods for View (bookshop pattern) ==========

    public ArrayList<Patient> getAllPatients() {
        return model.getAllPatients();
    }

    public ArrayList<Referral> getAllReferrals() {
        return model.getAllReferrals();
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return model.getAllPrescriptions();
    }
}

/* ===== Listener Interfaces (same pattern as bookshop controller) ===== */

interface SelectRoleListener {
    void onSelectRole(String roleName);
}

interface BackToRoleSelectListener {
    void onBack();
}

interface LoadPatientsListener {
    void onLoadPatients();
}

interface LoadReferralsListener {
    void onLoadReferrals();
}

interface LoadPrescriptionsListener {
    void onLoadPrescriptions();
}

interface OnCloseListener {
    void onClose();
}
