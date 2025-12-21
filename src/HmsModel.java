import java.util.ArrayList;
import java.util.HashMap;

public class HmsModel {

    private final HashMap<String, Patient> patients;
    private final HashMap<String, Clinician> clinicians;
    private final HashMap<String, Facility> facilities;
    private final HashMap<String, Appointment> appointments;
    private final HashMap<String, Prescription> prescriptions;
    private final HashMap<String, Referral> referrals;

    // Singleton that manages referral queue, audit, and writes to file
    private ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";

    public HmsModel() {
        patients = new HashMap<String, Patient>();
        clinicians = new HashMap<String, Clinician>();
        facilities = new HashMap<String, Facility>();
        appointments = new HashMap<String, Appointment>();
        prescriptions = new HashMap<String, Prescription>();
        referrals = new HashMap<String, Referral>();

        referralManager = ReferralManager.getInstance();

        loadAllData();
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
        for (int i = 0; i < lines.size(); i++) {
            Patient p = Patient.fromCSV(lines.get(i));
            patients.put(p.getPatientId(), p);
        }
    }

    private void savePatients() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Patient> list = new ArrayList<Patient>(patients.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(PATIENTS_FILE, lines);
    }

    public void addPatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
        savePatients();
    }

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<Patient>(patients.values());
    }

    public Patient getPatient(String patientId) {
        return patients.get(patientId);
    }

    public String generatePatientId() {
        return "P" + (patients.size() + 1);
    }

    // ========== Clinician Management ==========

    private void loadClinicians() {
        ArrayList<String> lines = CSVHandler.readLines(CLINICIANS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Clinician c = Clinician.fromCSV(lines.get(i));
            clinicians.put(c.getClinicianId(), c);
        }
    }

    private void saveClinicians() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Clinician> list = new ArrayList<Clinician>(clinicians.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(CLINICIANS_FILE, lines);
    }

    public void addClinician(Clinician clinician) {
        clinicians.put(clinician.getClinicianId(), clinician);
        saveClinicians();
    }

    public ArrayList<Clinician> getAllClinicians() {
        return new ArrayList<Clinician>(clinicians.values());
    }

    public Clinician getClinician(String clinicianId) {
        return clinicians.get(clinicianId);
    }

    public String generateClinicianId() {
        return "C" + (clinicians.size() + 1);
    }

    // ========== Facility Management ==========

    private void loadFacilities() {
        ArrayList<String> lines = CSVHandler.readLines(FACILITIES_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Facility f = Facility.fromCSV(lines.get(i));
            facilities.put(f.getFacilityId(), f);
        }
    }

    private void saveFacilities() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Facility> list = new ArrayList<Facility>(facilities.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(FACILITIES_FILE, lines);
    }

    public void addFacility(Facility facility) {
        facilities.put(facility.getFacilityId(), facility);
        saveFacilities();
    }

    public ArrayList<Facility> getAllFacilities() {
        return new ArrayList<Facility>(facilities.values());
    }

    public Facility getFacility(String facilityId) {
        return facilities.get(facilityId);
    }

    public String generateFacilityId() {
        return "F" + (facilities.size() + 1);
    }

    // ========== Appointment Management ==========

    private void loadAppointments() {
        ArrayList<String> lines = CSVHandler.readLines(APPOINTMENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Appointment a = Appointment.fromCSV(lines.get(i));
            appointments.put(a.getAppointmentId(), a);
        }
    }

    private void saveAppointments() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Appointment> list = new ArrayList<Appointment>(appointments.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(APPOINTMENTS_FILE, lines);
    }

    public void addAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
        saveAppointments();
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<Appointment>(appointments.values());
    }

    public Appointment getAppointment(String appointmentId) {
        return appointments.get(appointmentId);
    }

    public String generateAppointmentId() {
        return "AP" + (appointments.size() + 1);
    }

    // ========== Prescription Management ==========

    private void loadPrescriptions() {
        ArrayList<String> lines = CSVHandler.readLines(PRESCRIPTIONS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Prescription p = Prescription.fromCSV(lines.get(i));
            prescriptions.put(p.getPrescriptionId(), p);
        }
    }

    private void savePrescriptions() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Prescription> list = new ArrayList<Prescription>(prescriptions.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(PRESCRIPTIONS_FILE, lines);
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.put(prescription.getPrescriptionId(), prescription);
        savePrescriptions();
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<Prescription>(prescriptions.values());
    }

    public Prescription getPrescription(String prescriptionId) {
        return prescriptions.get(prescriptionId);
    }

    public String generatePrescriptionId() {
        return "RX" + (prescriptions.size() + 1);
    }

    // ========== Referral Management (uses Singleton) ==========

    private void loadReferrals() {
        ArrayList<String> lines = CSVHandler.readLines(REFERRALS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Referral r = Referral.fromCSV(lines.get(i));
            referrals.put(r.getReferralId(), r);

            // optional: only do this if you want old referrals put back into the queue
            referralManager.addReferral(r);
        }
    }

        private void saveReferrals() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Referral> list = new ArrayList<Referral>(referrals.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public void addReferral(Referral referral) {
        referrals.put(referral.getReferralId(), referral);
        saveReferrals();
        referralManager.addReferral(referral);
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<Referral>(referrals.values());
    }

    public Referral getReferral(String referralId) {
        return referrals.get(referralId);
    }

    public String generateReferralId() {
        return "R" + (referrals.size() + 1);
    }
}
