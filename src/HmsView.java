import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * View class for the Healthcare Management System
 * Handles all GUI components and user interface
 */
public class HmsView extends JFrame {
    private HmsController controller;
    private JTabbedPane tabbedPane;

    // ===== Listener References =====

    // Navigation listeners
    private SelectRoleListener selectRoleListener;
    private BackToRoleSelectListener backToRoleSelectListener;

    // Load listeners
    private Runnable loadPatientsListener;
    private Runnable loadReferralsListener;
    private Runnable loadPrescriptionsListener;
    private Runnable loadAppointmentsListener;

    // Create listeners
    private Runnable createReferralListener;
    private Runnable createPrescriptionListener;
    private Runnable createAppointmentListener;

    // Edit listener
    private Runnable editReferralListener;
    private Runnable editPrescriptionListener;
    private Runnable editAppointmentListener;

    // Cancel listener
    private Runnable cancelAppointmentListener;

    // Print selected record listeners (consultant)
    private Runnable printSelectedReferralListener;
    private Runnable printSelectedPrescriptionListener;

    // Window close
    private Runnable onCloseListener;

    // UI layout
    private CardLayout cardLayout;
    private JPanel cards;
    private JTable mainTable;

    // Role buttons
    private JButton adminRoleButton;
    private JButton consultantRoleButton;
    private JButton patientRoleButton;

    // Admin buttons
    private JButton adminLoadPatientsButton;
    private JButton adminLoadReferralsButton;
    private JButton adminLoadPrescriptionsButton;
    private JButton adminLoadAppointmentsButton;
    private JButton adminCreateAppointmentButton;
    private JButton adminEditAppointmentButton;
    private JButton adminCancelAppointmentButton;
    private JButton adminEditReferralButton;
    private JButton adminBackButton;

    // Consultant buttons
    private JButton consultantLoadReferralsButton;
    private JButton consultantCreateReferralButton;
    private JButton consultantPrintReferralButton;
    private JButton consultantEditReferralButton;
    private JButton consultantLoadPrescriptionButton;
    private JButton consultantCreatePrescriptionButton;
    private JButton consultantPrintPrescriptionButton;
    private JButton consultantEditPrescriptionButton;
    private JButton consultantBackButton;

    // Patient buttons
    private JButton patientLoadPrescriptionsButton;
    private JButton patientLoadAppointmentsButton;
    private JButton patientCreateAppointmentButton;
    private JButton patientEditAppointmentButton;
    private JButton patientCancelAppointmentButton;
    private JButton patientBackButton;

    public HmsView() {
        setTitle("Healthcare Management System - MVC Architecture");
        setSize(1600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    public void setController(HmsController controller) {
        this.controller = controller;
        showRoleSelectView();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        cards.add(buildRoleSelectPanel(), "ROLE_SELECT");
        cards.add(buildAdminPanel(), "ADMIN");
        cards.add(buildConsultantPanel(), "CONSULTANT");
        cards.add(buildPatientPanel(), "PATIENT");

        add(cards, BorderLayout.NORTH);

        mainTable = new JTable();
        mainTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
        add(new JScrollPane(mainTable), BorderLayout.CENTER);
    }

    private JPanel buildRoleSelectPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        buttonsPanel.add(new JLabel("Select User View:"));

        adminRoleButton = new JButton("Administrator");
        consultantRoleButton = new JButton("Consultant");
        patientRoleButton = new JButton("Patient");

        buttonsPanel.add(adminRoleButton);
        buttonsPanel.add(consultantRoleButton);
        buttonsPanel.add(patientRoleButton);

        adminRoleButton.addActionListener(e -> {
            if (selectRoleListener != null) selectRoleListener.onSelectRole("ADMIN"); });
        consultantRoleButton.addActionListener(e -> {
            if (selectRoleListener != null) selectRoleListener.onSelectRole("CONSULTANT"); });
        patientRoleButton.addActionListener(e -> {
            if (selectRoleListener != null) selectRoleListener.onSelectRole("PATIENT"); });

        return buttonsPanel;
    }

    private JPanel buildAdminPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        buttonsPanel.add(new JLabel("Admin View"));

        adminLoadPatientsButton = new JButton("Load Patients");
        adminLoadReferralsButton = new JButton("Load Referrals");
        adminEditReferralButton = new JButton("Edit Referral");
        adminLoadPrescriptionsButton = new JButton("Load Prescriptions");
        adminEditReferralButton = new JButton("Edit Referral");
        adminLoadAppointmentsButton = new JButton("Load Appointments");
        adminCreateAppointmentButton = new JButton("Create Appointment");
        adminEditAppointmentButton = new JButton("Edit Appointment");
        adminCancelAppointmentButton = new JButton("Cancel Appointment");
        adminBackButton = new JButton("Back");

        buttonsPanel.add(adminLoadPatientsButton);
        buttonsPanel.add(adminLoadReferralsButton);
        buttonsPanel.add(adminEditReferralButton);
        buttonsPanel.add(adminLoadPrescriptionsButton);
        buttonsPanel.add(adminLoadAppointmentsButton);
        buttonsPanel.add(adminCreateAppointmentButton);
        buttonsPanel.add(adminEditAppointmentButton);
        buttonsPanel.add(adminCancelAppointmentButton);
        buttonsPanel.add(adminBackButton);

        adminLoadPatientsButton.addActionListener(e -> {
            if (loadPatientsListener != null) loadPatientsListener.run(); });
        adminLoadReferralsButton.addActionListener(e -> {
            if (loadReferralsListener != null) loadReferralsListener.run(); });
        adminEditReferralButton.addActionListener(e -> {
            if (!hasSelectedRow()) { showSelectRowMessage(); return; }
            if (editReferralListener != null) editReferralListener.run(); });

        adminLoadPrescriptionsButton.addActionListener(e -> {
            if (loadPrescriptionsListener != null) loadPrescriptionsListener.run(); });

        adminLoadAppointmentsButton.addActionListener(e -> {
            if (loadAppointmentsListener != null) loadAppointmentsListener.run(); });
        adminCreateAppointmentButton.addActionListener(e -> {
            if (createAppointmentListener != null) createAppointmentListener.run(); });
        adminEditAppointmentButton.addActionListener(e -> {
            if (editAppointmentListener != null) editAppointmentListener.run(); });
        adminCancelAppointmentButton.addActionListener(e -> {
            if (cancelAppointmentListener != null) cancelAppointmentListener.run(); });

        adminBackButton.addActionListener(e -> {
            if (backToRoleSelectListener != null) backToRoleSelectListener.onBack(); });

        return buttonsPanel;
    }

    private JPanel buildConsultantPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(new JLabel("Consultant View"));

        consultantLoadReferralsButton = new JButton("Load Referrals");
        consultantCreateReferralButton = new JButton("Create Referral");
        consultantEditReferralButton = new JButton("Edit Referral");
        consultantPrintReferralButton = new JButton("Print Referral to File");
        consultantLoadPrescriptionButton = new JButton("Load Prescription");
        consultantCreatePrescriptionButton = new JButton("Create Prescription");
        consultantEditPrescriptionButton = new JButton("Edit Prescription");
        consultantPrintPrescriptionButton = new JButton("Print Prescription to File");
        consultantBackButton = new JButton("Back");

        buttonsPanel.add(consultantLoadReferralsButton);
        buttonsPanel.add(consultantCreateReferralButton);
        buttonsPanel.add(consultantEditReferralButton);
        buttonsPanel.add(consultantPrintReferralButton);
        buttonsPanel.add(consultantLoadPrescriptionButton);
        buttonsPanel.add(consultantCreatePrescriptionButton);
        buttonsPanel.add(consultantEditPrescriptionButton);
        buttonsPanel.add(consultantPrintPrescriptionButton);
        buttonsPanel.add(consultantBackButton);

        consultantLoadReferralsButton.addActionListener(e -> {
            if (loadReferralsListener != null) loadReferralsListener.run(); });
        consultantCreateReferralButton.addActionListener(e -> {
            if (createReferralListener != null) createReferralListener.run(); });
        consultantEditReferralButton.addActionListener(e -> {
            if (!hasSelectedRow()) { showSelectRowMessage(); return; }
            if (editReferralListener != null) editReferralListener.run(); });
        consultantPrintReferralButton.addActionListener(e -> {
            if (!hasSelectedRow()) { showSelectRowMessage(); return; }
            if (printSelectedReferralListener != null) printSelectedReferralListener.run(); });
        consultantLoadPrescriptionButton.addActionListener(e -> {
            if (loadPrescriptionsListener != null) loadPrescriptionsListener.run(); });
        consultantEditPrescriptionButton.addActionListener(e -> {
            if (!hasSelectedRow()) { showSelectRowMessage(); return; }
            if (editPrescriptionListener != null) editPrescriptionListener.run(); });
        consultantPrintPrescriptionButton.addActionListener(e -> {
            if (!hasSelectedRow()) { showSelectRowMessage(); return; }
            if (printSelectedPrescriptionListener != null) printSelectedPrescriptionListener.run();});
        consultantBackButton.addActionListener(e -> {
            if (backToRoleSelectListener != null) backToRoleSelectListener.onBack(); });

        return buttonsPanel;
    }

    private JPanel buildPatientPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        buttonsPanel.add(new JLabel("Patient View"));

        patientLoadPrescriptionsButton = new JButton("Load Prescriptions");
        patientLoadAppointmentsButton = new JButton("Load Appointments");
        patientCreateAppointmentButton = new JButton("Create Appointment");
        patientEditAppointmentButton = new JButton("Edit Appointment");
        patientCancelAppointmentButton = new JButton("Cancel Appointment");
        patientBackButton = new JButton("Back");

        buttonsPanel.add(patientLoadPrescriptionsButton);
        buttonsPanel.add(patientLoadAppointmentsButton);
        buttonsPanel.add(patientCreateAppointmentButton);
        buttonsPanel.add(patientEditAppointmentButton);
        buttonsPanel.add(patientCancelAppointmentButton);
        buttonsPanel.add(patientBackButton);

        patientLoadPrescriptionsButton.addActionListener(e -> {
            if (loadPrescriptionsListener != null) loadPrescriptionsListener.run(); });
        patientLoadAppointmentsButton.addActionListener(e -> {
            if (loadAppointmentsListener != null) loadAppointmentsListener.run(); });
        patientCreateAppointmentButton.addActionListener(e -> {
            if (createAppointmentListener != null) createAppointmentListener.run(); });
        patientEditAppointmentButton.addActionListener(e -> {
            if (editAppointmentListener != null) editAppointmentListener.run(); });
        patientCancelAppointmentButton.addActionListener(e -> {
            if (cancelAppointmentListener != null) cancelAppointmentListener.run(); });

        patientBackButton.addActionListener(e -> {
            if (backToRoleSelectListener != null) backToRoleSelectListener.onBack(); });

        return buttonsPanel;
    }

    private void wireWindowClose() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (onCloseListener != null) onCloseListener.run();
                System.exit(0);
            }
        });
    }

    // navigation helpers

    public void showRoleSelectView() { cardLayout.show(cards, "ROLE_SELECT"); }
    public void showAdminView() { cardLayout.show(cards, "ADMIN"); }
    public void showConsultantView() { cardLayout.show(cards, "CONSULTANT"); }
    public void showPatientView() { cardLayout.show(cards, "PATIENT"); }

    // listener setters
    public void setSelectRoleListener(SelectRoleListener l) { this.selectRoleListener = l; }
    public void setBackToRoleSelectListener(BackToRoleSelectListener l) { this.backToRoleSelectListener = l; }

    public void setLoadPatientsListener(Runnable l) {this.loadPatientsListener = l; }
    public void setLoadReferralsListener(Runnable l) { this.loadReferralsListener = l; }
    public void setLoadPrescriptionsListener(Runnable l) { this.loadPrescriptionsListener = l; }
    public void setLoadAppointmentsListener(Runnable l) { this.loadAppointmentsListener = l; }

    public void setCreateReferralListener(Runnable l) { this.createReferralListener = l; }
    public void setCreatePrescriptionListener(Runnable l) { }
    public void setCreateAppointmentListener(Runnable l) { this.createAppointmentListener = l; }
    public void setEditReferralListener(Runnable l) { this.editReferralListener = l; }

    public void setPrintSelectedReferralListener(Runnable l) { this.printSelectedReferralListener = l; }
    public void setPrintSelectedPrescriptionListener(Runnable l) { this.printSelectedPrescriptionListener = l; }

    public void setEditPrescriptionListener(Runnable l) { this.editPrescriptionListener = l; }
    public void setEditAppointmentListener(Runnable l) { this.editAppointmentListener = l; }

    public void setCancelAppointmentListener(Runnable l) { this.cancelAppointmentListener = l; }


    public void setOnCloseListener(Runnable l) { this.onCloseListener = l; }

    // table selection helpers

    private boolean hasSelectedRow() {
        return mainTable != null && mainTable.getSelectedRow() >= 0;
    }

    private void showSelectRowMessage() {
        JOptionPane.showMessageDialog(this,
                "Please select a row in the table first.",
                "No selection",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String getSelectedIdFromTable(int idColumnIndex) {
        if (!hasSelectedRow()) return null;
        int row = mainTable.getSelectedRow();
        Object variable = mainTable.getValueAt(row, idColumnIndex);
        return variable == null ? null : variable.toString();
    }

    public int getTableColumnCount() {
        if (mainTable == null || mainTable.getModel() == null) return 0;
        return mainTable.getModel().getColumnCount();
    }

    public String getTableColumnName(int index) {
        if (mainTable == null || mainTable.getModel() == null) return null;
        if (index < 0 || index >= mainTable.getModel().getColumnCount()) return null;
        return mainTable.getModel().getColumnName(index);
    }

    // table displays

    public void showPatients(List<Patient> patients) {
        String[] columns = {
                "Patient ID", "First Name", "Last Name", "DOB", "NHS No", "Gender",
                "Phone", "Email", "Address", "Postcode", "Emergency Name",
                "Emergency Phone", "Registration Date", "GP Surgery ID"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);

            tableModel.addRow(new Object[] {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getDateOfBirth(),
                    patient.getNhsNumber(),
                    patient.getGender(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getPostcode(),
                    patient.getEmergencyContactName(),
                    patient.getEmergencyContactPhone(),
                    patient.getRegistrationDate(),
                    patient.getGpSurgeryId()
            });
        }

        mainTable.setModel(tableModel);
    }

    public void showReferrals(List<Referral> referrals) {
        String[] columns = {"Referral ID" , "Patient ID", "Referring Clinician ID", "Referred To Clinician ID"," "+
                "Referring Facility ID", "Referred To Facility ID", "Referral Date", "Urgency Level", "Referral Reason"," "+
                "Clinical Summary", "Requested Investigations", "Status", "Appointment ID", "Notes", "Created Date"," "+
                "Last Updated"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (int i = 0; i < referrals.size(); i++) {
            Referral referral = referrals.get(i);
            tableModel.addRow(new Object[]{ referral.getReferralId(), referral.getPatientId(),
                    referral.getReferringClinicianId(), referral.getReferredToClinicianId(), referral.getReferringFacilityId(),
                    referral.getReferredToFacilityId(), referral.getReferralDate(), referral.getUrgencyLevel(),
                    referral.getReferralReason(), referral.getClinicalSummary(), referral.getRequestedInvestigations(),
                    referral.getStatus(), referral.getAppointmentId(), referral.getNotes(), referral.getCreatedDate(),
                    referral.getLastUpdated() });
        }

        mainTable.setModel(tableModel);
    }

    public void showPrescriptions(List<Prescription> prescriptions) {
        String[] columns = {"Prescription ID", "Patient ID", "Clinician ID", "Appointment ID", "Prescription Date",
                "Medication Name", "Dosage", "Frequency", "Duration Days", "Quantity", "Instructions",
                "Pharmacy Name", "Status", "Issue Date", "Collection Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (int i = 0; i < prescriptions.size(); i++) {
            Prescription prescription = prescriptions.get(i);
            tableModel.addRow(new Object[]{ prescription.getPrescriptionId(), prescription.getPatientId(),
                    prescription.getClinicianId(), prescription.getAppointmentId(), prescription.getPrescriptionDate(),
                    prescription.getMedicationName(), prescription.getDosage(), prescription.getFrequency(),
                    prescription.getDurationDays(), prescription.getQuantity(), prescription.getInstructions(),
                    prescription.getPharmacyName(), prescription.getPrescriptionStatus(), prescription.getIssueDate(),
                    prescription.getCollectionDate() });
        }

        mainTable.setModel(tableModel);
    }

    public void showAppointments(List<Appointment> appointments) {
        String[] columns = {"Appointment ID", "Patient ID", "Clinician ID", "Facility ID", "Appointment Date",
                "Time", "Duration", "Appointment Type", "Status", "Reason For Visit", "Notes", "Created Date",
                "Last Modified" };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            tableModel.addRow(new Object[]{ appointment.getAppointmentId(), appointment.getPatientId(),
                    appointment.getClinicianId(), appointment.getFacilityId(), appointment.getAppointmentDate(),
                    appointment.getAppointmentTime(), appointment.getDurationMinutes(), appointment.getAppointmentType(),
                    appointment.getStatus(), appointment.getReasonForVisit(), appointment.getNotes(), appointment.getCreatedDate(),
                    appointment.getLastModified() });
        }

        mainTable.setModel(tableModel);
    }

    // advice messages to assist the user

    public ReferralInput promptForReferral() {
        String patientId = JOptionPane.showInputDialog(this, "Enter Patient ID (e.g. P001):");
        if (patientId == null) return null;

        String facilityId = JOptionPane.showInputDialog(this, "Enter Facility ID (e.g. F001):");
        if (facilityId == null) return null;

        String urgency = JOptionPane.showInputDialog(this, "Enter Urgency Level (1=Low, 2=Medium, 3=High):");
        if (urgency == null) return null;

        String summary = JOptionPane.showInputDialog(this, "Enter Clinical Summary:");
        if (summary == null) return null;

        return new ReferralInput(patientId.trim(), facilityId.trim(), urgency.trim(), summary.trim());
    }

    public PrescriptionInput promptForPrescription() {
        String patientId = JOptionPane.showInputDialog(this, "Enter Patient ID (e.g. P001):");
        if (patientId == null) return null;

        String medicationName = JOptionPane.showInputDialog(this, "Enter Medication Name:");
        if (medicationName == null) return null;

        String pharmacyName = JOptionPane.showInputDialog(this, "Enter Pharmacy Name:");
        if (pharmacyName == null) return null;

        String status = JOptionPane.showInputDialog(this, "Enter Status (e.g. NEW/ISSUED):");
        if (status == null) return null;
        if (status.trim().isEmpty()) status = "NEW";

        return new PrescriptionInput(patientId.trim(), medicationName.trim(), pharmacyName.trim(), status.trim());
    }

    public AppointmentInput promptForAppointment() {
        String patientId = JOptionPane.showInputDialog(this, "Enter Patient ID (e.g. P001):");
        if (patientId == null) return null;

        String clinicianId = JOptionPane.showInputDialog(this, "Enter Clinician ID (optional, e.g. C001):");
        if (clinicianId == null) clinicianId = "";

        String date = JOptionPane.showInputDialog(this, "Enter Appointment Date (e.g. 2025-12-22):");
        if (date == null) return null;

        return new AppointmentInput(patientId.trim(), clinicianId.trim(), date.trim());
    }

    public static class ReferralInput {
        public String patientId;
        public String facilityId;
        public String urgency;
        public String summary;

        public ReferralInput(String patientId, String facilityId, String urgency, String summary) {
            this.patientId = patientId;
            this.facilityId = facilityId;
            this.urgency = urgency;
            this.summary = summary;
        }
    }

    public static class PrescriptionInput {
        public String patientId;
        public String medicationName;
        public String pharmacyName;
        public String status;

        public PrescriptionInput(String patientId, String medicationName, String pharmacyName, String status) {
            this.patientId = patientId;
            this.medicationName = medicationName;
            this.pharmacyName = pharmacyName;
            this.status = status;
        }
    }

    public static class AppointmentInput {
        public String patientId;
        public String clinicianId;
        public String date;

        public AppointmentInput(String patientId, String clinicianId, String date) {
            this.patientId = patientId;
            this.clinicianId = clinicianId;
            this.date = date;
        }
    }
}
