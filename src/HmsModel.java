import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main Model class for the Healthcare Management System.
 */
public class HmsModel {
    private HashMap<String, Patient> patients;
    private HashMap<String, Clinician> clinicians;
    private HashMap<String, Facility> facilities;
    private HashMap<String, Appointment> appointments;
    private HashMap<String, Prescription> prescriptions;
    private HashMap<String, Referral> referrals;

    // Singleton that manages referral queue
    private ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";
    private static final String REFERRAL_EMAIL_FILE = "referral_emails.txt";
    private static final String REFERRAL_PRINT_FILE = "referral_prints.txt";
    private static final String PRESCRIPTION_PRINT_FILE = "prescription_prints.txt";

    // ID counters (initialised after load)
    private int nextReferralNumber = 1;
    private int nextPrescriptionNumber = 1;
    private int nextAppointmentNumber = 1;

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
        for (int i = 1; i < lines.size(); i++) { //this gave me a challenge as there is a header on the assignment files!
            Patient patient = Patient.fromCSV(lines.get(i));
            if (patient == null) continue;
            patients.put(patient.getPatientId(), patient);
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
            Clinician clinician = Clinician.fromCSV(lines.get(i));
            if (clinician == null) continue;
            clinicians.put(clinician.getClinicianId(), clinician);
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
            Facility facility = Facility.fromCSV(lines.get(i));
            if (facility == null) continue;
            facilities.put(facility.getFacilityId(), facility);
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
            Appointment appointment = Appointment.fromCSV(lines.get(i));
            if (appointment == null) continue;
            appointments.put(appointment.getAppointmentId(), appointment);
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
            Prescription prescription = Prescription.fromCSV(lines.get(i));
            if (prescription == null) continue;
            prescriptions.put(prescription.getPrescriptionId(), prescription);
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

    public void updatePrescription(Prescription prescription) {
        if (prescription == null) return;
        prescriptions.put(prescription.getPrescriptionId(), prescription);
        saveReferrals();
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<Prescription>(prescriptions.values());
    }

    // ========== Referral Management (Singleton) ==========

    private void loadReferrals() {
        ArrayList<String> lines = CSVHandler.readLines(REFERRALS_FILE);
        for (int i = 1; i < lines.size(); i++) {
            Referral referral = Referral.fromCSV(lines.get(i));
            if (referral == null) continue;
            referrals.put(referral.getReferralId(), referral);
        }
    }

    private void saveReferrals() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated,communication_method");

        for (Referral referral : referrals.values()) {
            lines.add(referral.toCSV());
        }

        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public void addReferral(Referral referral) {
        if (referral == null) return;

        referrals.put(referral.getReferralId(), referral);
        saveReferrals();

        // Singleton that manages referral queue / audit
        referralManager.addReferral(referral);

        // Simulation of the email by writing a readable line to a text file located in the main HMS directory.
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

    // helper methods called by Controller

    public void printReferralToFile(Referral referral) {
        if (referral == null) return;
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, formatReferralEmail(referral));
    }

    public void printPrescriptionToFile(Prescription prescription) {
        if (prescription == null) return;
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE,
                "PRESCRIPTION PRINTED | prescriptionId=" + prescription.getPrescriptionId() +
                        " patientId=" + prescription.getPatientId() +
                        " medication=" + prescription.getMedicationName() +
                        " status=" + prescription.getPrescriptionStatus());
    }

    private String formatReferralEmail(Referral referral) {
        return "REFERRAL SENT | " +
                "referralId=" + referral.getReferralId() +
                " patientId=" + referral.getPatientId() +
                " status=" + referral.getStatus() +
                " urgency=" + referral.getUrgencyLevel();
    }

    // ========== ID Generation ==========

    private void initialiseIdCounters() {
        // Referrals: generates numeric IDs extending the existing scheme
        for (String id : referrals.keySet()) {
            int number = parseTrailingNumber(id);
            if (number >= nextReferralNumber) nextReferralNumber = number + 1;
        }

        // Prescriptions: generates numeric IDs extending the existing scheme
        for (String id : prescriptions.keySet()) {
            int number = parseTrailingNumber(id);
            if (number >= nextPrescriptionNumber) nextPrescriptionNumber = number + 1;
        }

        // Appointments: generates numeric IDs extending the existing scheme
        for (String id : appointments.keySet()) {
            int number = parseTrailingNumber(id);
            if (number >= nextAppointmentNumber) nextAppointmentNumber = number + 1;
        }
    }

    private int parseTrailingNumber(String id) {
        if (id == null) return -1;
        String str = id.trim();

        // initially identifies leading letters (RX, AP, R etc)
        while (str.length() > 0 && !Character.isDigit(str.charAt(0))) {
            str = str.substring(1);
        }

        if (!str.matches("\\d+")) return -1;

        try { return Integer.parseInt(str); }
        catch (Exception e) { return -1; }
    }

    public String generateReferralId() {
        String str = String.valueOf(nextReferralNumber++);
        while (str.length() < 3) str = "0" + str;
        return "R" + str;
    }

    public String generatePrescriptionId() {
        String str = String.valueOf(nextPrescriptionNumber++);
        while (str.length() < 3) str = "0" + str;
        return "RX" + str;
    }

    public String generateAppointmentId() {
        String str = String.valueOf(nextAppointmentNumber++);
        while (str.length() < 3) str = "0" + str;
        return "AP" + str;
    }
}
