import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Main View class for the Healthcare Management System.
 *
 */
public class HmsView extends JFrame {

    private HmsController controller;

    // Navigation listeners (same idea as Bookshop)
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

    // Edit listener (admin)
    private Runnable editReferralListener;

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
    private JButton adminRoleBtn;
    private JButton consultantRoleBtn;
    private JButton patientRoleBtn;

    // Admin buttons
    private JButton adminLoadPatientsBtn;
    private JButton adminLoadReferralsBtn;
    private JButton adminLoadPrescriptionsBtn;
    private JButton adminLoadAppointmentsBtn;
    private JButton adminCreateAppointmentBtn;
    private JButton adminEditReferralBtn;
    private JButton adminBackBtn;

    // Consultant buttons
    private JButton consultantLoadReferralsBtn;
    private JButton consultantCreateReferralBtn;
    private JButton consultantCreatePrescriptionBtn;
    private JButton consultantPrintReferralBtn;
    private JButton consultantPrintPrescriptionBtn;
    private JButton consultantBackBtn;

    // Patient buttons
    private JButton patientLoadPrescriptionsBtn;
    private JButton patientLoadAppointmentsBtn;
    private JButton patientCreateAppointmentBtn;
    private JButton patientBackBtn;

    public HmsView() {
        super("Healthcare Management System - MVC Architecture");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        wireWindowClose();
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
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("Select User View:"));

        adminRoleBtn = new JButton("Administrator");
        consultantRoleBtn = new JButton("Consultant");
        patientRoleBtn = new JButton("Patient");

        p.add(adminRoleBtn);
        p.add(consultantRoleBtn);
        p.add(patientRoleBtn);

        adminRoleBtn.addActionListener(e -> { if (selectRoleListener != null) selectRoleListener.onSelectRole("ADMIN"); });
        consultantRoleBtn.addActionListener(e -> { if (selectRoleListener != null) selectRoleListener.onSelectRole("CONSULTANT"); });
        patientRoleBtn.addActionListener(e -> { if (selectRoleListener != null) selectRoleListener.onSelectRole("PATIENT"); });

        return p;
    }

    private JPanel buildAdminPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("Admin View"));

        adminLoadPatientsBtn = new JButton("Load Patients");
        adminLoadReferralsBtn = new JButton("Load Referrals");
        adminEditReferralBtn = new JButton("Edit Referral");
        adminLoadPrescriptionsBtn = new JButton("Load Prescriptions");
        adminEditReferralBtn = new JButton("Edit Referral");
        adminLoadAppointmentsBtn = new JButton("Load Appointments");
        adminCreateAppointmentBtn = new JButton("Create Appointment");
        adminBackBtn = new JButton("Back");

        p.add(adminLoadPatientsBtn);
        p.add(adminLoadReferralsBtn);
        p.add(adminEditReferralBtn);
        p.add(adminLoadPrescriptionsBtn);
        p.add(adminLoadAppointmentsBtn);
        p.add(adminCreateAppointmentBtn);
        p.add(adminBackBtn);

        adminLoadPatientsBtn.addActionListener(e -> { if (loadPatientsListener != null) loadPatientsListener.run(); });
        adminLoadReferralsBtn.addActionListener(e -> { if (loadReferralsListener != null) loadReferralsListener.run(); });
        adminEditReferralBtn.addActionListener(e -> { if (!hasSelectedRow()) { showSelectRowMessage(); return; } if (editReferralListener != null) editReferralListener.run(); });

        adminLoadPrescriptionsBtn.addActionListener(e -> { if (loadPrescriptionsListener != null) loadPrescriptionsListener.run(); });

        adminLoadAppointmentsBtn.addActionListener(e -> { if (loadAppointmentsListener != null) loadAppointmentsListener.run(); });
        adminCreateAppointmentBtn.addActionListener(e -> { if (createAppointmentListener != null) createAppointmentListener.run(); });
        adminBackBtn.addActionListener(e -> { if (backToRoleSelectListener != null) backToRoleSelectListener.onBack(); });

        return p;
    }

    private JPanel buildConsultantPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("Consultant View"));

        consultantLoadReferralsBtn = new JButton("Load Referrals");
        consultantCreateReferralBtn = new JButton("Create Referral");
        consultantEditReferralBtn = new JButton("Edit Referral");
        consultantPrintReferralBtn = new JButton("Print Referral to File");
        consultantCreatePrescriptionBtn = new JButton("Create Prescription");
        consultantEditPrescriptionBtn = new JButton("Edit Prescription");
        consultantPrintPrescriptionBtn = new JButton("Print Prescription to File");
        consultantBackBtn = new JButton("Back");

        p.add(consultantLoadReferralsBtn);
        p.add(consultantCreateReferralBtn);
        p.add(consultantEditReferralBtn);
        p.add(consultantPrintReferralBtn);
        p.add(consultantCreatePrescriptionBtn);
        p.add(consultantEditPrescriptionBtn);
        p.add(consultantPrintPrescriptionBtn);
        p.add(consultantBackBtn);

        consultantLoadReferralsBtn.addActionListener(e -> { if (loadReferralsListener != null) loadReferralsListener.run(); });
        consultantCreateReferralBtn.addActionListener(e -> { if (createReferralListener != null) createReferralListener.run(); });
        consultantEditReferralBtn.addActionListener(e -> { if (!hasSelectedRow()) { showSelectRowMessage(); return; } if (editReferralListener != null) editReferralListener.run(); });
        consultantPrintReferralBtn.addActionListener(e -> { if (!hasSelectedRow()) { showSelectRowMessage(); return; } if (printSelectedReferralListener != null) printSelectedReferralListener.run(); });
        consultantEditPrescriptionBtn.addActionListener(e -> { if (!hasSelectedRow()) { showSelectRowMessage(); return; } if (editReferralListener != null) editReferralListener.run(); });
            consultantPrintPrescriptionBtn.addActionListener(e -> { if (!hasSelectedRow()) { showSelectRowMessage(); return; } if (printSelectedPrescriptionListener != null) printSelectedPrescriptionListener.run();});
        consultantBackBtn.addActionListener(e -> { if (backToRoleSelectListener != null) backToRoleSelectListener.onBack(); });

        return p;
    }

    private JPanel buildPatientPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("Patient View"));

        patientLoadPrescriptionsBtn = new JButton("Load Prescriptions");
        patientLoadAppointmentsBtn = new JButton("Load Appointments");
        patientCreateAppointmentBtn = new JButton("Create Appointment");
        patientBackBtn = new JButton("Back");

        p.add(patientLoadPrescriptionsBtn);
        p.add(patientLoadAppointmentsBtn);
        p.add(patientCreateAppointmentBtn);
        p.add(patientBackBtn);

        patientLoadPrescriptionsBtn.addActionListener(e -> { if (loadPrescriptionsListener != null) loadPrescriptionsListener.run(); });
        patientLoadAppointmentsBtn.addActionListener(e -> { if (loadAppointmentsListener != null) loadAppointmentsListener.run(); });
        patientCreateAppointmentBtn.addActionListener(e -> { if (createAppointmentListener != null) createAppointmentListener.run(); });
        patientBackBtn.addActionListener(e -> { if (backToRoleSelectListener != null) backToRoleSelectListener.onBack(); });

        return p;
    }

    private void wireWindowClose() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (onCloseListener != null) onCloseListener.run();
                System.exit(0);
            }
        });
    }

    // ===== Navigation helpers =====

    public void showRoleSelectView() { cardLayout.show(cards, "ROLE_SELECT"); }
    public void showAdminView() { cardLayout.show(cards, "ADMIN"); }
    public void showConsultantView() { cardLayout.show(cards, "CONSULTANT"); }
    public void showPatientView() { cardLayout.show(cards, "PATIENT"); }

    // ===== Listener setters (Bookshop style) =====

    public void setSelectRoleListener(SelectRoleListener l) { this.selectRoleListener = l; }
    public void setBackToRoleSelectListener(BackToRoleSelectListener l) { this.backToRoleSelectListener = l; }

    public void setLoadPatientsListener(Runnable l) { this.loadPatientsListener = l; }
    public void setLoadReferralsListener(Runnable l) { this.loadReferralsListener = l; }
    public void setLoadPrescriptionsListener(Runnable l) { this.loadPrescriptionsListener = l; }
    public void setLoadAppointmentsListener(Runnable l) { this.loadAppointmentsListener = l; }

    public void setCreateReferralListener(Runnable l) { this.createReferralListener = l; }
    public void setCreatePrescriptionListener(Runnable l) { this.createPrescriptionListener = l; }
    public void setCreateAppointmentListener(Runnable l) { this.createAppointmentListener = l; }

    public void setEditReferralListener(Runnable l) { this.editReferralListener = l; }

    public void setPrintSelectedReferralListener(Runnable l) { this.printSelectedReferralListener = l; }
    public void setPrintSelectedPrescriptionListener(Runnable l) { this.printSelectedPrescriptionListener = l; }

    public void setOnCloseListener(Runnable l) { this.onCloseListener = l; }

    // ===== Table selection helpers =====

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
        Object v = mainTable.getValueAt(row, idColumnIndex);
        return v == null ? null : v.toString();
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

    // ===== Table displays =====

    public void showPatients(List<Patient> patients) {
        String[] columns = {
                "Patient ID", "First Name", "Last Name", "DOB", "NHS No", "Gender",
                "Phone", "Email", "Address", "Postcode",
                "Emergency Name", "Emergency Phone", "Registration Date", "GP Surgery ID"
        };

        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);

            tm.addRow(new Object[] {
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getDateOfBirth(),
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getPhoneNumber(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getPostcode(),
                    p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(),
                    p.getRegistrationDate(),
                    p.getGpSurgeryId()
            });
        }

        mainTable.setModel(tm);
    }

    public void showReferrals(List<Referral> referrals) {
        String[] columns = {"Referral ID", "Patient ID", "Status", "Urgency"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (int i = 0; i < referrals.size(); i++) {
            Referral r = referrals.get(i);
            tm.addRow(new Object[]{ r.getReferralId(), r.getPatientId(), r.getStatus(), r.getUrgencyLevel() });
        }

        mainTable.setModel(tm);
    }

    public void showPrescriptions(List<Prescription> prescriptions) {
        String[] columns = {"Prescription ID", "Medication", "Status", "Pharmacy"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (int i = 0; i < prescriptions.size(); i++) {
            Prescription p = prescriptions.get(i);
            tm.addRow(new Object[]{ p.getPrescriptionId(), p.getMedicationName(), p.getPrescriptionStatus(), p.getPharmacyName() });
        }

        mainTable.setModel(tm);
    }

    public void showAppointments(List<Appointment> appointments) {
        String[] columns = {"Appointment ID", "Patient ID", "Date", "Status"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            tm.addRow(new Object[]{ a.getAppointmentId(), a.getPatientId(), a.getAppointmentDate(), a.getStatus() });
        }

        mainTable.setModel(tm);
    }

    // text prompts to assist the user

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

    // Simple input holders (kept tiny and dumb)
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
