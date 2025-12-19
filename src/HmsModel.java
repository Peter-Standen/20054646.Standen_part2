import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HmsModel {

    // Repositories (persistence)
    private final PatientCsvRepository patientRepo;
    private final ReferralCsvRepository referralRepo;
    private final PrescriptionCsvRepository prescriptionRepo;
    private final AppointmentCsvRepository appointmentRepo;

    // In-memory state (model owns the data)
    private List<Patient> patients = new ArrayList<>();
    private final Queue<Referral> referralQueue = new LinkedList<>();

    public HmsModel(PatientCsvRepository patientRepo,
                    ReferralCsvRepository referralRepo,
                    PrescriptionCsvRepository prescriptionRepo,
                    AppointmentCsvRepository appointmentRepo) {
        this.patientRepo = patientRepo;
        this.referralRepo = referralRepo;
        this.prescriptionRepo = prescriptionRepo;
        this.appointmentRepo = appointmentRepo;
    }

    // -------------------------
    // PatientRecordService logic
    // -------------------------
    public void loadPatients() {
        patients = patientRepo.loadAll();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    // -------------------------
    // ReferralsManager logic
    // -------------------------
    public Referral createReferral(Referral referral) {
        referralQueue.add(referral);
        referralRepo.append(referral);

        writeEmailIfNotExists(referral);
        writeEhrUpdateIfNotExists(referral);
        appendAudit(referral, "CREATED");

        return referral;
    }

    // Keep these private, they are domain side-effects
    private void writeEmailIfNotExists(Referral referral) { }
    private void writeEhrUpdateIfNotExists(Referral referral) { }
    private void appendAudit(Referral referral, String action) { }

    // -------------------------
    // PrescriptionService logic
    // -------------------------
    // Right now your PrescriptionService has no methods,
    // so this is just a placeholder for when you add features.
    // Example stub:
    // public void createPrescription(Prescription p) { ... }
}
