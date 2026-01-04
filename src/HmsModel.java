import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

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

    // Singleton manager (Referral Queue)
    private ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";

    // Print simulation files
    private static final String REFERRAL_EMAIL_FILE = "referral_emails.txt";
    private static final String REFERRAL_PRINT_FILE = "referral_prints.txt";
    private static final String PRESCRIPTION_PRINT_FILE = "prescription_prints.txt";
    private static final SimpleDateFormat PRINT_DF = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

        CSVHandler.createFileIfNotExists(REFERRAL_EMAIL_FILE);
        CSVHandler.createFileIfNotExists(REFERRAL_PRINT_FILE);
        CSVHandler.createFileIfNotExists(PRESCRIPTION_PRINT_FILE);

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
        for (int i = 1; i < lines.size(); i++) { // skip header row
            Patient patient = Patient.fromCSV(lines.get(i));
            if (patient == null) continue;
            patients.put(patient.getPatientId(), patient);
        }
    }

    private void savePatients() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address," +
                "postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id");

        ArrayList<Patient> list = new ArrayList<Patient>(patients.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(PATIENTS_FILE, lines);
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
        lines.add("clinician_id,first_name,last_name,title,speciality,gmc_number,phone_number,email,workplace_id," +
                "workplace_type,employment_status,start_date");

        ArrayList<Clinician> list = new ArrayList<Clinician>(clinicians.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(CLINICIANS_FILE, lines);
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
        lines.add("facility_id,facility_name,facility_type,address,postcode,phone_number,email,opening_hours," +
                "manager_name,capacity,specialities_offered");

        ArrayList<Facility> list = new ArrayList<Facility>(facilities.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(FACILITIES_FILE, lines);
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
        lines.add("appointment_id,patient_id,clinician_id,facility_id,appointment_date,appointment_time," +
                "duration_minutes,appointment_type,status,reason_for_visit,notes,created_date,last_modified");

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

    public void updateAppointment(Appointment appointment) {
        if (appointment == null) return;
        appointments.put(appointment.getAppointmentId(), appointment);
        saveAppointments();
    }

    public boolean cancelAppointment(String appointmentId) {
        if (appointmentId == null || appointmentId.trim().isEmpty()) return false;

        Appointment appointment = appointments.get(appointmentId.trim());
        if (appointment == null) return false;

        try {
            appointment.setStatus("CANCELLED");
        } catch (Exception e) {
        }

        appointment.setLastModified(new Date());
        saveAppointments();
        return true;
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
        lines.add("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name," +
                "dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date");

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
        savePrescriptions();
    }

    // ========== Referral Management ==========

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

        ArrayList<Referral> list = new ArrayList<Referral>(referrals.values());
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public void addReferral(Referral referral) {
        if (referral == null) return;

        referrals.put(referral.getReferralId(), referral);
        saveReferrals();

        referralManager.addReferral(referral);
        CSVHandler.appendLine(REFERRAL_EMAIL_FILE, formatReferralEmailSummary(referral));
    }

    public void updateReferral(Referral referral) {
        if (referral == null) return;
        referrals.put(referral.getReferralId(), referral);
        saveReferrals();
    }

    // ========== Get All Methods ==========

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<Patient>(patients.values());
    }

    public ArrayList<Clinician> getAllClinicians() {
        return new ArrayList<Clinician>(clinicians.values());
    }

    public ArrayList<Facility> getAllFacilities() {
        return new ArrayList<Facility>(facilities.values());
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<Appointment>(appointments.values());
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<Prescription>(prescriptions.values());
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<Referral>(referrals.values());
    }

    // ========== Lookups ==========

    public Patient getPatient(String patientId) {
        if (patientId == null) return null;
        return patients.get(patientId.trim());
    }

    public Clinician getClinician(String clinicianId) {
        if (clinicianId == null) return null;
        return clinicians.get(clinicianId.trim());
    }

    public Facility getFacility(String facilityId) {
        if (facilityId == null) return null;
        return facilities.get(facilityId.trim());
    }

    public Appointment getAppointmentById(String appointmentId) {
        if (appointmentId == null) return null;
        return appointments.get(appointmentId.trim());
    }

    public Prescription getPrescriptionById(String prescriptionId) {
        if (prescriptionId == null) return null;
        return prescriptions.get(prescriptionId.trim());
    }

    public Referral getReferralById(String referralId) {
        if (referralId == null) return null;
        return referrals.get(referralId.trim());
    }

    // ========== User Friendly Labels ==========

    public String formatPatientLabel(String patientId) {
        if (patientId == null) return "";
        Patient patient = patients.get(patientId.trim());
        if (patient == null) return patientId;
        return patient.getPatientId() + " - " + patient.getFirstName() + " " + patient.getLastName();
    }

    public String formatClinicianLabel(String clinicianId) {
        if (clinicianId == null) return "";
        Clinician clinician = clinicians.get(clinicianId.trim());
        if (clinician == null) return clinicianId;

        String title = "";
        try {
            Object ttitle = clinician.getTitle();
            if (ttitle != null) title = ttitle.toString() + " ";
        } catch (Exception e) {
        }

        return clinician.getClinicianId() + " - " + title + clinician.getFirstName() + " " + clinician.getLastName();
    }

    public String formatFacilityLabel(String facilityId) {
        if (facilityId == null) return "";
        Facility facility = facilities.get(facilityId.trim());
        if (facility == null) return facilityId;
        return facility.getFacilityId() + " - " + facility.getFacilityName();
    }

    // ========== Create Files ==========

    public boolean createPrescription(String patientId, String clinicianId,
                                      String appointmentId, String medicationName, String dosage,
                                      String frequency, String durationDays, String quantity,
                                      String instructions, String pharmacyName, String status) {

        String prescriptionId = generatePrescriptionId();

        String csv =
                safe(prescriptionId) + "," + safe(patientId) + "," + safe(clinicianId) + "," +
                        safe(appointmentId) + "," + "" + "," + safe(medicationName) + "," + safe(dosage) + "," +
                        safe(frequency) + "," + safe(durationDays) + "," + safe(quantity) + "," +
                        safe(instructions) + "," + safe(pharmacyName) + "," + safe(status) + "," + "" + "," + "";

        Prescription prescription = Prescription.fromCSV(csv);
        if (prescription == null) return false;

        prescriptions.put(prescription.getPrescriptionId(), prescription);
        savePrescriptions();
        return true;
    }

    public boolean createAppointment(String patientId, String clinicianId, String date) {

        String appointmentId = generateAppointmentId();

        String csv =
                safe(appointmentId) + "," + safe(patientId) + "," + safe(clinicianId) + "," +
                        "," + safe(date) + "," +
                        "," + "," + "," + "SCHEDULED," + "," + "," + "," + "";

        Appointment appointment = Appointment.fromCSV(csv);
        if (appointment == null) return false;

        appointments.put(appointment.getAppointmentId(), appointment);
        saveAppointments();
        return true;
    }

    public boolean createReferral(String patientId, String referringClinicianId,
                                  String referredToClinicianId, String referringFacilityId,
                                  String referredToFacilityId, String urgency, String reason, String summary,
                                  String investigations, String appointmentId, String notes) {

        String referralId = generateReferralId();

        String csv =
                safe(referralId) + "," +                           // referral_id
                        safe(patientId) + "," +                            // patient_id
                        safe(referringClinicianId) + "," +                 // referring_clinician_id
                        safe(referredToClinicianId) + "," +                // referred_to_clinician_id
                        safe(referringFacilityId) + "," +                  // referring_facility_id
                        safe(referredToFacilityId) + "," +                 // referred_to_facility_id
                        "" + "," +                                         // referral_date (blank = parser may default)
                        safe(urgency) + "," +                              // urgency_level
                        safe(reason) + "," +                               // referral_reason
                        safe(summary) + "," +                              // clinical_summary
                        safe(investigations) + "," +                       // requested_investigations
                        "NEW" + "," +                                      // status
                        safe(appointmentId) + "," +                        // appointment_id
                        safe(notes) + "," +                                // notes
                        "" + "," +                                         // created_date
                        "" + "," +                                         // last_updated
                        "";                                                // communication_method

        Referral referral = Referral.fromCSV(csv);
        if (referral == null) return false;

        referrals.put(referral.getReferralId(), referral);
        saveReferrals();

        referralManager.addReferral(referral);
        CSVHandler.appendLine(REFERRAL_EMAIL_FILE, formatReferralEmailSummary(referral));

        return true;
    }



    // ========== Printing (Simulation) ==========

    public void printReferralToFile(Referral referral) {
        if (referral == null) return;

        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "============================================================");
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "REFERRAL LETTER");
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Printed: " + PRINT_DF.format(new Date()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "------------------------------------------------------------");
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Referral ID: " + safe(referral.getReferralId()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Patient: " + formatPatientLabel(referral.getPatientId()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "From Clinician: " + formatClinicianLabel(referral.getReferringClinicianId()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "To Clinician: " + formatClinicianLabel(referral.getReferredToClinicianId()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "From Facility: " + formatFacilityLabel(referral.getReferringFacilityId()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "To Facility: " + formatFacilityLabel(referral.getReferredToFacilityId()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Referral Date: " + formatDate(referral.getReferralDate()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Urgency: " + safe(referral.getUrgencyLevel()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Reason: " + safe(referral.getReferralReason()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Clinical Summary: " + safe(referral.getClinicalSummary()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Requested Investigations: " + safe(referral.getRequestedInvestigations()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Status: " + safe(referral.getStatus()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Appointment ID: " + safe(referral.getAppointmentId()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Notes: " + safe(referral.getNotes()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Created: " + formatDate(referral.getCreatedDate()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "Last Updated: " + formatDate(referral.getLastUpdated()));
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "============================================================");
        CSVHandler.appendLine(REFERRAL_PRINT_FILE, "");
    }

    public void printPrescriptionToFile(Prescription prescription) {
        if (prescription == null) return;

        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "============================================================");
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "PRESCRIPTION");
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Printed: " + PRINT_DF.format(new Date()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "------------------------------------------------------------");
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Prescription ID: " + safe(prescription.getPrescriptionId()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Patient: " + formatPatientLabel(prescription.getPatientId()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Clinician: " + formatClinicianLabel(prescription.getClinicianId()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Appointment ID: " + safe(prescription.getAppointmentId()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Prescription Date: " + formatDate(prescription.getPrescriptionDate()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Medication: " + safe(prescription.getMedicationName()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Dosage: " + safe(prescription.getDosage()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Frequency: " + safe(prescription.getFrequency()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Duration (days): " + safeInt(prescription.getDurationDays()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Quantity: " + safe(prescription.getQuantity()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Instructions: " + safe(prescription.getInstructions()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Pharmacy: " + safe(prescription.getPharmacyName()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Status: " + safeEnum(prescription.getPrescriptionStatus()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Issue Date: " + formatDate(prescription.getIssueDate()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "Collection Date: " + formatDate(prescription.getCollectionDate()));
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "============================================================");
        CSVHandler.appendLine(PRESCRIPTION_PRINT_FILE, "");
    }

    private String formatReferralEmailSummary(Referral referral) {
        return "REFERRAL SENT | id=" + safe(referral.getReferralId()) +
                " patient=" + formatPatientLabel(referral.getPatientId()) +
                " urgency=" + safe(referral.getUrgencyLevel()) +
                " status=" + safe(referral.getStatus());
    }

    // ========== ID Gemnerator ==========

    private void initialiseIdCounters() {
        for (String id : referrals.keySet()) {
            int number = parseTrailingNumber(id);
            if (number >= nextReferralNumber) nextReferralNumber = number + 1;
        }

        for (String id : prescriptions.keySet()) {
            int number = parseTrailingNumber(id);
            if (number >= nextPrescriptionNumber) nextPrescriptionNumber = number + 1;
        }

        for (String id : appointments.keySet()) {
            int number = parseTrailingNumber(id);
            if (number >= nextAppointmentNumber) nextAppointmentNumber = number + 1;
        }
    }

    private int parseTrailingNumber(String id) {
        if (id == null) return -1;
        String str = id.trim();

        while (str.length() > 0 && !Character.isDigit(str.charAt(0))) { // https://www.geeksforgeeks.org/java/character-isdigit-method-in-java-with-examples/
            str = str.substring(1);
        }

        if (!str.matches("\\d+")) return -1;

        try {
            return Integer.parseInt(str); // https://www.geeksforgeeks.org/java/integer-valueof-vs-integer-parseint-with-examples/
        } catch (Exception e) {
            return -1;
        }
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

    // ========== Helpers ==========

    private String formatDate(Date date) {
        if (date == null) return "";
        return PRINT_DF.format(date);
    }

    private String safe(String value) {
        if (value == null) return "";
        return value.trim();
    }

    private String safeEnum(Object value) {
        if (value == null) return "";
        return value.toString();
    }

    private String safeInt(Integer value) {
        if (value == null) return "";
        return String.valueOf(value);
    }
}
