import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HmsModel {

    private final PatientCsvRepository patientRepo;
    private final ReferralCsvRepository referralRepo;
    private final PrescriptionCsvRepository prescriptionRepo;
    private final AppointmentCsvRepository appointmentRepo;

    private List<Patient> patients = new ArrayList<>();
    private final Queue<Referral> referralQueue = new LinkedList<>();

    // ✅ Minimal addition: default constructor for HmsApplication
    public HmsModel() {
        this(
                new PatientCsvRepository("patients.csv"),
                new ReferralCsvRepository("referrals.csv"),
                new PrescriptionCsvRepository("prescriptions.csv"),
                new AppointmentCsvRepository("appointments.csv")
        );
    }

    public HmsModel(PatientCsvRepository patientRepo,
                    ReferralCsvRepository referralRepo,
                    PrescriptionCsvRepository prescriptionRepo,
                    AppointmentCsvRepository appointmentRepo) {
        this.patientRepo = patientRepo;
        this.referralRepo = referralRepo;
        this.prescriptionRepo = prescriptionRepo;
        this.appointmentRepo = appointmentRepo;
    }

    public void loadPatients() {
        patients = patientRepo.loadAll();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public Referral createReferral(Referral referral) {
        referralQueue.add(referral);
        referralRepo.append(referral);

        writeEmailIfNotExists(referral);
        writeEhrUpdateIfNotExists(referral);
        appendAudit(referral, "CREATED");

        return referral;
    }

    private void writeEmailIfNotExists(Referral referral) { }
    private void writeEhrUpdateIfNotExists(Referral referral) { }
    private void appendAudit(Referral referral, String action) { }
}
