import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main Model class for the Healthcare Management System.
 *
 */
public class HmsModel {

    private HashMap<String, Patient> patients;
    private HashMap<String, Clinician> clinicians;
    private HashMap<String, Facility> facilities;
    private HashMap<String, Appointment> appointments;
    private HashMap<String, Prescription> prescriptions;
    private HashMap<String, Referral> referrals;

    // Singleton that manages referral queue / audit (I referenced the Bookshop, OrderManager was referenced here)
    private ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";

    // Evidence/audit style outputs
    private static final String REFERRAL_EMAIL_FILE = "referral_emails.txt";
    private static final String REFERRAL_PRINT_FILE = "referral_prints.txt";
    private static final String PRESCRIPTION_PRINT_FILE = "prescription_prints.txt";

    // ID counters (initialised after load)
    private int nextReferralNumber = 1;       // numeric IDs like 001, 002, 003
    private int nextPrescriptionNumber = 1;   // IDs like RX001
    private int nextAppointmentNumber = 1;    // IDs like AP001

    public HmsModel() {
        patients = new HashMap<String, Patient>();
        clinicians = new HashMap<String, Clinician>();
        facilities = new HashMap<String, Facility>();
        appointments = new HashMap<String, Appointment>();
        prescriptions = new HashMap<String, Prescription>();
        referrals = new HashMap<String, Referral>();

        referralManager = ReferralManager.getInstance();

        loadAllData();
        initialiseIdCounters();
    }

    public void loadAllData() {
        loadPatients();
        loadClinicians();
        loadFacilities();
        loadAppointments();
        loadPrescriptions();
        loadReferrals();
    }

    public void saveAllData() {
        savePatients();
        saveClinicians();
        saveFacilities();
        saveAppointments();
        savePrescriptions();
        saveReferrals();
    }

    // ========== Patient Management ==========

    private void loadPatients() {
        ArrayList<String> lines = CSVHandler.readLines(PATIENTS_FILE);
        for (int i = 1; i < lines.size(); i++) {
            Patient p = Patient.fromCSV(lines.get(i));
            if (p == null) continue;
            patients.put(p.getPatientId(), p);
        }
    }

    private void savePatients() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address,postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id");

        ArrayList<Patient> list = new ArrayList<Patient>(patients.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }

        CSVHandler.writeLines(PATIENTS_FILE, lines);
    }

    public void addPatient(Patient patient) {
        if (patient == null) return;
        patients.put(patient.getPatientId(), patient);
        savePatients();
    }

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<Patient>(patients.values());
    }

    // ========== Clinician Management ==========

    private void loadClinicians() {
        ArrayList<String> lines = CSVHandler.readLines(CLINICIANS_FILE);
        for (int i = 1; i < lines.size(); i++) {
            Clinician c = Clinician.fromCSV(lines.get(i));
            if (c == null) continue;
            clinicians.put(c.getClinicianId(), c);
        }
    }

    private void saveClinicians() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("clinician_id,first_name,last_name,title,speciality,gmc_number,phone_number,email,workplace_id,workplace_type,employment_status,start_date");

        ArrayList<Clinician> list = new ArrayList<Clinician>(clinicians.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }

        CSVHandler.writeLines(CLINICIANS_FILE, lines);
    }

    public ArrayList<Clinician> getAllClinicians() {
        return new ArrayList<Clinician>(clinicians.values());
    }

    // ========== Facility Management ==========

    private void loadFacilities() {
        ArrayList<String> lines = CSVHandler.readLines(FACILITIES_FILE);
        for (int i = 1; i < lines.size(); i++) {
            Facility f = Facility.fromCSV(lines.get(i));
            if (f == null) continue;
            facilities.put(f.getFacilityId(), f);
        }
    }

    private void saveFacilities() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("facility_id,facility_name,facility_type,address,postcode,phone_number,email,opening_hours,manager_name,capacity,specialities_offered");

        ArrayList<Facility> list = new ArrayList<Facility>(facilities.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }

        CSVHandler.writeLines(FACILITIES_FILE, lines);
    }

    public ArrayList<Facility> getAllFacilities() {
        return new ArrayList<Facility>(facilities.values());
    }

    // ========== Appointment Management ==========

    private void loadAppointments() {
        ArrayList<String> lines = CSVHandler.readLines(APPOINTMENTS_FILE);
        for (int i = 1; i < lines.size(); i++) {
            Appointment a = Appointment.fromCSV(lines.get(i));
            if (a == null) continue;
            appointments.put(a.getAppointmentId(), a);
        }
    }

    private void saveAppointments() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("appointment_id,patient_id,clinician_id,facility_id,appointment_date,appointment_time,duration_minutes,appointment_type,status,reason_for_visit,notes,created_date,last_modified");

        ArrayList<Appointment> list = new ArrayList<Appointment>(appointments.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }

        CSVHandler.writeLines(APPOINTMENTS_FILE, lines);
    }

    public void addAppointment(Appointment appointment) {
        if (appointment == null) return;
        appointments.put(appointment.getAppointmentId(), appointment);
        saveAppointments();
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<Appointment>(appointments.values());
    }

    // ========== Prescription Management ==========

    private void loadPrescriptions() {
        ArrayList<String> lines = CSVHandler.readLines(PRESCRIPTIONS_FILE);
        for (int i = 1; i < lines.size(); i++) {
            Prescription p = Prescription.fromCSV(lines.get(i));
            if (p == null) continue;
            prescriptions.put(p.getPrescriptionId(), p);
        }
    }

    private void savePrescriptions() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date");

        ArrayList<Prescription> list = new ArrayList<Prescription>(prescriptions.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }

        CSVHandler.writeLines(PRESCRIPTIONS_FILE, lines);
    }

    public void addPrescription(Prescription prescription) {
        if (prescription == null) return;
        prescriptions.put(prescription.getPrescriptionId(), prescription);
        savePrescriptions();
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<Prescription>(prescriptions.values());
    }

    // ========== Referral Management (uses Singleton) ==========

    private void loadReferrals() {
        ArrayList<String> lines = CSVHandler.readLines(REFERRALS_FILE);
        for (int i = 1; i < lines.size(); i++) {
            Referral r = Referral.fromCSV(lines.get(i));
            if (r == null) continue;
            referrals.put(r.getReferralId(), r);
        }
    }

    private void saveReferrals() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated,communication_method");

        for (Referral r : referrals.values()) {
            lines.add(r.toCSV());
        }

        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public void addReferral(Referral referral) {
        if (referral == null) return;

        referrals.put(referral.getReferralId(), referral);
        saveReferrals();

        // Singleton that manages referral queue / audit
        referralManager.addReferral(referral);

        // Simulate email by writing a readable line to a text file
        CSVHandler.appendLine(REFERRAL_EMAIL_FILE, formatReferralEmail(referral));
    }

    public void updateReferral(Referral referral) {
        if (referral == null) return;
        referrals.put(referral.getReferralId(), referral);
        saveReferrals();
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<Referral>(referrals.values());
    }

    public Referral getReferralById(String referralId) {
        if (referralId == null) return null;
        return referrals.get(referralId);
    }

    public Prescription getPrescriptionById(String prescriptionId) {
        if (prescriptionId == null) return null;
        return prescriptions.get(prescriptionId);
    }

    // Evidence/audit style helpers (called by Controller)

    public void printReferralToFile(Referral r) {
        if (r == null) return;
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, formatReferralEmail(r));
    }

    public void printPrescriptionToFile(Prescription p) {
        if (p == null) return;
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE,
                "PRESCRIPTION PRINTED | prescriptionId=" + p.getPrescriptionId() +
                        " patientId=" + p.getPatientId() +
                        " medication=" + p.getMedicationName() +
                        " status=" + p.getPrescriptionStatus());
    }

    private String formatReferralEmail(Referral r) {
        return "REFERRAL SENT | " +
                "referralId=" + r.getReferralId() +
                " patientId=" + r.getPatientId() +
                " status=" + r.getStatus() +
                " urgency=" + r.getUrgencyLevel();
    }

    // ========== ID Generation ==========

    private void initialiseIdCounters() {
        // Referrals: numeric IDs like 001, or sometimes R001 (handle both safely)
        for (String id : referrals.keySet()) {
            int n = parseTrailingNumber(id);
            if (n >= nextReferralNumber) nextReferralNumber = n + 1;
        }

        // Prescriptions: IDs like RX001
        for (String id : prescriptions.keySet()) {
            int n = parseTrailingNumber(id);
            if (n >= nextPrescriptionNumber) nextPrescriptionNumber = n + 1;
        }

        // Appointments: IDs like AP001
        for (String id : appointments.keySet()) {
            int n = parseTrailingNumber(id);
            if (n >= nextAppointmentNumber) nextAppointmentNumber = n + 1;
        }
    }

    private int parseTrailingNumber(String id) {
        if (id == null) return -1;
        String s = id.trim();

        // strip leading letters (RX, AP, R etc)
        while (s.length() > 0 && !Character.isDigit(s.charAt(0))) {
            s = s.substring(1);
        }

        if (!s.matches("\\d+")) return -1;

        try { return Integer.parseInt(s); }
        catch (Exception e) { return -1; }
    }

    public String generateReferralId() {
        String s = String.valueOf(nextReferralNumber++);
        while (s.length() < 3) s = "0" + s;
        return s;
    }

    public String generatePrescriptionId() {
        String s = String.valueOf(nextPrescriptionNumber++);
        while (s.length() < 3) s = "0" + s;
        return "RX" + s;
    }

    public String generateAppointmentId() {
        String s = String.valueOf(nextAppointmentNumber++);
        while (s.length() < 3) s = "0" + s;
        return "AP" + s;
    }
}
