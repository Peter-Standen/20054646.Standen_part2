import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main Model class for the Healthcare Management System
 */
public class HmsModel {

    private HashMap<String, Patient> patients;
    private HashMap<String, Clinician> clinicians;
    private HashMap<String, Facility> facilities;
    private HashMap<String, Appointment> appointments;
    private HashMap<String, Prescription> prescriptions;
    private HashMap<String, Referral> referrals;

    // Singleton that manages referral queue, audit, and writes to file
    private ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";

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
        for (int i = 1; i < lines.size(); i++) {
            Patient p = Patient.fromCSV(lines.get(i));
            if (p == null) continue;
            patients.put(p.getPatientId(), p);
        }
    }

    private void savePatients() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add(
                "patient_id\tfirst_name\tlast_name\tdate_of_birth\tnhs_number\tgender\tphone_number\temail\taddress\tpostcode\temergency_contact_name\temergency_contact_phone\tregistration_date\tgp_surgery_id");

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
        lines.add("clinician_id\tfirst_name\tlast_name\ttitle\tspeciality\tgmc_number\tphone_number\temail\tworkplace_id\tworkplace_type\temployment_status\tstart_date");

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
        lines.add("facilityId\tfacilityName\tfacilityType\taddress\tpostcode\tphoneNumber\temail\topeningHours\tmanagerName\tcapacity\tspecialitiesOffered");

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

    public void addAppointment(Appointment appointment) {
        if (appointment == null) return;

        appointments.put(appointment.getAppointmentId(), appointment);
        saveAppointments();
    }

    private void saveAppointments() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("appointmentId\tpatientId\tclinicianId\tfacilityId\tappointmentDate\tappointmentTime\tdurationMinutes\tappointmentType\tstatus\treasonForVisit\tnotes\tcreatedDate\tlastModified");

        ArrayList<Appointment> list = new ArrayList<Appointment>(appointments.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(APPOINTMENTS_FILE, lines);
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

    public void addPrescription(Prescription prescription) {
        if (prescription == null) return;

        prescriptions.put(prescription.getPrescriptionId(), prescription);
        savePrescriptions();
    }

    private void savePrescriptions() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("prescriptionId\tpatientId\tclinicianId\tappointmentId\tprescriptionDate\tmedicationName\tdosage\tfrequency\tdurationDays\tquantity\tinstructions\tpharmacyName\tstatus\tissueDate\tcollectionDate");

        ArrayList<Prescription> list = new ArrayList<Prescription>(prescriptions.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(PRESCRIPTIONS_FILE, lines);
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

    public void addReferral(Referral referral) {
        if (referral == null) return;

        referrals.put(referral.getReferralId(), referral);
        saveReferrals();

        // Singleton that manages referral queue, audit, and writes to file
        referralManager.addReferral(referral);
    }

    private void saveReferrals() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("referralId\tpatientId\treferringClinicianId\treferredToClinicianId\treferringFacilityId\treferredToFacilityId\treferralDate\turgencyLevel\treferralReason\tclinicalSummary\trequestedInvestigations\tstatus\tappointmentId\tnotes\tcreatedDate\tlastUpdated");

        for (Referral r : referrals.values()) {
            lines.add(r.toCSV());
        }

        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<Referral>(referrals.values());
    }

    // ========== ID Generation ==========

    private void initialiseIdCounters() {
        for (String id : referrals.keySet()) {
            int n = Integer.parseInt(id.substring(1));
            if (n >= nextReferralNumber) nextReferralNumber = n + 1;
        }
        for (String id : prescriptions.keySet()) {
            int n = Integer.parseInt(id.substring(2));
            if (n >= nextPrescriptionNumber) nextPrescriptionNumber = n + 1;
        }
        for (String id : appointments.keySet()) {
            int n = Integer.parseInt(id.substring(2));
            if (n >= nextAppointmentNumber) nextAppointmentNumber = n + 1;
        }
    }

    public String generateReferralId() {
        return "R" + nextReferralNumber++;
    }

    public String generatePrescriptionId() {
        return "RX" + nextPrescriptionNumber++;
    }

    public String generateAppointmentId() {
        return "AP" + nextAppointmentNumber++;
    }
}
