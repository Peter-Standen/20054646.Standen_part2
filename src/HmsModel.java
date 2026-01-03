import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Main Model class for the Healthcare Management System.
 */
public class HmsModel {

    // Mirrors BookshopModel fields and structure
    private HashMap<String, Patient> patients;
    private HashMap<String, Clinician> clinicians;
    private HashMap<String, Facility> facilities;
    private HashMap<String, Appointment> appointments;
    private HashMap<String, Prescription> prescriptions;
    private HashMap<String, Referral> referrals;

    // singleton that manages referral queue
    private ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";

    // print simulation files (txt)
    private static final String REFERRAL_EMAIL_FILE = "referral_emails.txt";
    private static final String REFERRAL_PRINT_FILE = "referral_prints.txt";
    private static final String PRESCRIPTION_PRINT_FILE = "prescription_prints.txt";

    // nice readable timestamp for printouts
    private static final SimpleDateFormat PRINT_DF = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    // ID counters (initialised after load)
    private int nextReferralNumber = 1;
    private int nextPrescriptionNumber = 1;
    private int nextAppointmentNumber = 1;

    public HmsModel() {

        // Mirrors BookshopModel constructor style
        patients = new HashMap<String, Patient>();
        clinicians = new HashMap<String, Clinician>();
        facilities = new HashMap<String, Facility>();
        appointments = new HashMap<String, Appointment>();
        prescriptions = new HashMap<String, Prescription>();
        referrals = new HashMap<String, Referral>();

        referralManager = ReferralManager.getInstance();

        // create print files if missing (keeps everything simple and robust)
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

    // =====================================================================================
    // Bookshop-style "get all" methods (controller uses these)
    // =====================================================================================

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

    // =====================================================================================
    // Basic lookups (controller uses these for update/print)
    // =====================================================================================

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

    // =====================================================================================
    // Patient Management
    // =====================================================================================

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

    // =====================================================================================
    // Clinician Management
    // =====================================================================================

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

    // =====================================================================================
    // Facility Management
    // =====================================================================================

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

    // =====================================================================================
    // Appointment Management
    // =====================================================================================

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

    public void updateAppointment(Appointment appointment) {
        if (appointment == null) return;
        appointments.put(appointment.getAppointmentId(), appointment);
        saveAppointments();
    }

    public boolean cancelAppointment(String appointmentId) {
        if (appointmentId == null || appointmentId.trim().isEmpty()) return false;

        Appointment appointment = appointments.get(appointmentId.trim());
        if (appointment == null) return false;

        // keep it simple: status and last modified update then save
        try {
            appointment.setStatus("CANCELLED");
        } catch (Exception e) {
            // if your Appointment uses an enum, controller can handle that path instead
        }

        appointment.setLastModified(new Date());

        saveAppointments();
        return true;
    }

    // =====================================================================================
    // Prescription Management
    // =====================================================================================

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
        savePrescriptions();
    }

    // =====================================================================================
    // Referral Management (Singleton)
    // =====================================================================================

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

        // Singleton that manages referral queue / audit
        referralManager.addReferral(referral);

        // this is my simulation of the email by writing a readable line to a text file located in the main HMS directory.
        appendLine(REFERRAL_EMAIL_FILE, formatReferralEmailSummary(referral));
    }

    public void updateReferral(Referral referral) {
        if (referral == null) return;
        referrals.put(referral.getReferralId(), referral);
        saveReferrals();
    }

    // =====================================================================================
    // Friendly label helpers (used by the view, keeps UI readable)
    // =====================================================================================

    public String formatPatientLabel(String patientId) {
        if (patientId == null) return "";
        Patient p = patients.get(patientId.trim());
        if (p == null) return patientId;
        return p.getPatientId() + " - " + p.getFirstName() + " " + p.getLastName();
    }

    public String formatClinicianLabel(String clinicianId) {
        if (clinicianId == null) return "";
        Clinician c = clinicians.get(clinicianId.trim());
        if (c == null) return clinicianId;

        // Keep basic and robust, title might be null depending on your CSV/model
        String title = "";
        try {
            Object t = c.getTitle();
            if (t != null) title = t.toString() + " ";
        } catch (Exception e) {
            // ignore, keep it simple
        }

        return c.getClinicianId() + " - " + title + c.getFirstName() + " " + c.getLastName();
    }

    public String formatFacilityLabel(String facilityId) {
        if (facilityId == null) return "";
        Facility f = facilities.get(facilityId.trim());
        if (f == null) return facilityId;
        return f.getFacilityId() + " - " + f.getFacilityName();
    }

    // =====================================================================================
    // Create “basic” objects (Bookshop-style: keep creation logic in model)
    // =====================================================================================

    public Prescription createBasicPrescription(String prescriptionId,
                                                String patientId,
                                                String clinicianId,
                                                String appointmentId,
                                                String medicationName,
                                                String pharmacyName,
                                                String status) {

        // Use your existing Prescription.fromCSV to stay consistent with the rest of the project
        String csv =
                safe(prescriptionId) + "," +
                        safe(patientId) + "," +
                        safe(clinicianId) + "," +
                        safe(appointmentId) + "," +
                        "," + // prescription_date
                        safe(medicationName) + "," +
                        "," + // dosage
                        "," + // frequency
                        "," + // duration_days
                        "," + // quantity
                        "," + // instructions
                        safe(pharmacyName) + "," +
                        safe(status) + "," +
                        "," + // issue_date
                        "";   // collection_date

        return Prescription.fromCSV(csv);
    }

    public Appointment createBasicAppointment(String appointmentId,
                                              String patientId,
                                              String clinicianId,
                                              String date) {

        String csv =
                safe(appointmentId) + "," +
                        safe(patientId) + "," +
                        safe(clinicianId) + "," +
                        "," +            // facility_id
                        safe(date) + "," +
                        "," +            // time
                        "," +            // duration
                        "," +            // type
                        "SCHEDULED," +   // status
                        "," +            // reason
                        "," +            // notes
                        "," +            // created_date
                        "";              // last_modified

        return Appointment.fromCSV(csv);
    }

    public Referral createBasicReferral(String referralId,
                                        String patientId,
                                        String referringClinicianId,
                                        String referredToClinicianId,
                                        String referringFacilityId,
                                        String referredToFacilityId,
                                        String urgency,
                                        String reason,
                                        String summary) {

        // referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,
        // referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,
        // requested_investigations,status,appointment_id,notes,created_date,last_updated,communication_method
        String csv =
                safe(referralId) + "," +
                        safe(patientId) + "," +
                        safe(referringClinicianId) + "," +
                        safe(referredToClinicianId) + "," +
                        safe(referringFacilityId) + "," +
                        safe(referredToFacilityId) + "," +
                        "," +                  // referral_date
                        safe(urgency) + "," +
                        safe(reason) + "," +
                        safe(summary) + "," +
                        "," +                  // requested_investigations
                        "NEW," +               // status
                        "," +                  // appointment_id
                        "," +                  // notes
                        "," +                  // created_date
                        "," +                  // last_updated
                        "";                    // communication_method

        return Referral.fromCSV(csv);
    }

    public PrescriptionStatus parsePrescriptionStatus(String input) {
        if (input == null) return null;
        String s = input.trim();
        if (s.isEmpty()) return null;

        try {
            // your enum values look like Draft, Issued, Dispensed, Collected, Cancelled
            String normalised = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            return PrescriptionStatus.valueOf(normalised);
        } catch (Exception e) {
            return null;
        }
    }

    // =====================================================================================
    // Print to File Helpers (Reader Friendly)
    // =====================================================================================

    public void printReferralToFile(Referral referral) {
        if (referral == null) return;

        // write a small, readable "letter" block as multiple lines
        appendLine(REFERRAL_PRINT_FILE, "============================================================");
        appendLine(REFERRAL_PRINT_FILE, "REFERRAL LETTER");
        appendLine(REFERRAL_PRINT_FILE, "Printed: " + PRINT_DF.format(new Date()));
        appendLine(REFERRAL_PRINT_FILE, "------------------------------------------------------------");
        appendLine(REFERRAL_PRINT_FILE, "Referral ID: " + safe(referral.getReferralId()));
        appendLine(REFERRAL_PRINT_FILE, "Patient: " + formatPatientLabel(referral.getPatientId()));
        appendLine(REFERRAL_PRINT_FILE, "From Clinician: " + formatClinicianLabel(referral.getReferringClinicianId()));
        appendLine(REFERRAL_PRINT_FILE, "To Clinician: " + formatClinicianLabel(referral.getReferredToClinicianId()));
        appendLine(REFERRAL_PRINT_FILE, "From Facility: " + formatFacilityLabel(referral.getReferringFacilityId()));
        appendLine(REFERRAL_PRINT_FILE, "To Facility: " + formatFacilityLabel(referral.getReferredToFacilityId()));
        appendLine(REFERRAL_PRINT_FILE, "Referral Date: " + formatDate(referral.getReferralDate()));
        appendLine(REFERRAL_PRINT_FILE, "Urgency: " + safe(referral.getUrgencyLevel()));
        appendLine(REFERRAL_PRINT_FILE, "Reason: " + safe(referral.getReferralReason()));
        appendLine(REFERRAL_PRINT_FILE, "Clinical Summary: " + safe(referral.getClinicalSummary()));
        appendLine(REFERRAL_PRINT_FILE, "Requested Investigations: " + safe(referral.getRequestedInvestigations()));
        appendLine(REFERRAL_PRINT_FILE, "Status: " + safe(referral.getStatus()));
        appendLine(REFERRAL_PRINT_FILE, "Appointment ID: " + safe(referral.getAppointmentId()));
        appendLine(REFERRAL_PRINT_FILE, "Notes: " + safe(referral.getNotes()));
        appendLine(REFERRAL_PRINT_FILE, "Created: " + formatDate(referral.getCreatedDate()));
        appendLine(REFERRAL_PRINT_FILE, "Last Updated: " + formatDate(referral.getLastUpdated()));
        appendLine(REFERRAL_PRINT_FILE, "============================================================");
        appendLine(REFERRAL_PRINT_FILE, ""); // blank line spacer
    }

    public void printPrescriptionToFile(Prescription prescription) {
        if (prescription == null) return;

        appendLine(PRESCRIPTION_PRINT_FILE, "============================================================");
        appendLine(PRESCRIPTION_PRINT_FILE, "PRESCRIPTION");
        appendLine(PRESCRIPTION_PRINT_FILE, "Printed: " + PRINT_DF.format(new Date()));
        appendLine(PRESCRIPTION_PRINT_FILE, "------------------------------------------------------------");
        appendLine(PRESCRIPTION_PRINT_FILE, "Prescription ID: " + safe(prescription.getPrescriptionId()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Patient: " + formatPatientLabel(prescription.getPatientId()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Clinician: " + formatClinicianLabel(prescription.getClinicianId()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Appointment ID: " + safe(prescription.getAppointmentId()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Prescription Date: " + formatDate(prescription.getPrescriptionDate()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Medication: " + safe(prescription.getMedicationName()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Dosage: " + safe(prescription.getDosage()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Frequency: " + safe(prescription.getFrequency()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Duration (days): " + safeInt(prescription.getDurationDays()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Quantity: " + safe(prescription.getQuantity()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Instructions: " + safe(prescription.getInstructions()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Pharmacy: " + safe(prescription.getPharmacyName()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Status: " + safeEnum(prescription.getPrescriptionStatus()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Issue Date: " + formatDate(prescription.getIssueDate()));
        appendLine(PRESCRIPTION_PRINT_FILE, "Collection Date: " + formatDate(prescription.getCollectionDate()));
        appendLine(PRESCRIPTION_PRINT_FILE, "============================================================");
        appendLine(PRESCRIPTION_PRINT_FILE, "");
    }

    private String formatReferralEmailSummary(Referral r) {
        return "REFERRAL SENT | id=" + safe(r.getReferralId()) +
                " patient=" + formatPatientLabel(r.getPatientId()) +
                " urgency=" + safe(r.getUrgencyLevel()) +
                " status=" + safe(r.getStatus());
    }

    // =====================================================================================
    // ID Generation (same approach you already had)
    // =====================================================================================

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

    // =====================================================================================
    // Small helpers (keeps model robust and avoids null mess)
    // =====================================================================================

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

    private void appendLine(String filename, String line) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(line);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            System.out.println("Could not append to file: " + filename);
            System.out.println(e.getMessage());
        }
    }
}
