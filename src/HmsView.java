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

    // Listener fields (bookshop-style)
    private SelectRoleListener selectRoleListener;
    private BackToRoleSelectListener backToRoleSelectListener;
    private LoadPatientsListener loadPatientsListener;
    private LoadReferralsListener loadReferralsListener;
    private LoadPrescriptionsListener loadPrescriptionsListener;
    private OnCloseListener onCloseListener;

    // Cards for role-based views
    private CardLayout cardLayout;
    private JPanel cards;

    // Shared table
    private JTable mainTable;

    // Role select screen buttons
    private JButton adminRoleBtn;
    private JButton consultantRoleBtn;
    private JButton patientRoleBtn;

    // Admin buttons
    private JButton adminLoadPatientsBtn;
    private JButton adminLoadReferralsBtn;
    private JButton adminLoadPrescriptionsBtn;
    private JButton adminBackBtn;

    // Consultant buttons
    private JButton consultantLoadReferralsBtn;
    private JButton consultantBackBtn;

    // Patient buttons
    private JButton patientLoadPrescriptionsBtn;
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
        // Bookshop pattern: view does initial setup once controller exists
        showRoleSelectView();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Build screens
        JPanel roleSelectPanel = buildRoleSelectPanel();
        JPanel adminPanel = buildAdminPanel();
        JPanel consultantPanel = buildConsultantPanel();
        JPanel patientPanel = buildPatientPanel();

        cards.add(roleSelectPanel, "ROLE_SELECT");
        cards.add(adminPanel, "ADMIN");
        cards.add(consultantPanel, "CONSULTANT");
        cards.add(patientPanel, "PATIENT");

        add(cards, BorderLayout.NORTH);

        // Shared table in center
        mainTable = new JTable();
        add(new JScrollPane(mainTable), BorderLayout.CENTER);

        // Start with empty model
        mainTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
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

        adminRoleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectRoleListener != null) selectRoleListener.onSelectRole("ADMIN");
            }
        });

        consultantRoleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectRoleListener != null) selectRoleListener.onSelectRole("CONSULTANT");
            }
        });

        patientRoleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectRoleListener != null) selectRoleListener.onSelectRole("PATIENT");
            }
        });

        return p;
    }

    private JPanel buildAdminPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("Admin View"));

        adminLoadPatientsBtn = new JButton("Load Patients");
        adminLoadReferralsBtn = new JButton("Load Referrals");
        adminLoadPrescriptionsBtn = new JButton("Load Prescriptions");
        adminBackBtn = new JButton("Back");

        p.add(adminLoadPatientsBtn);
        p.add(adminLoadReferralsBtn);
        p.add(adminLoadPrescriptionsBtn);
        p.add(adminBackBtn);

        adminLoadPatientsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loadPatientsListener != null) loadPatientsListener.onLoadPatients();
            }
        });

        adminLoadReferralsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loadReferralsListener != null) loadReferralsListener.onLoadReferrals();
            }
        });

        adminLoadPrescriptionsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loadPrescriptionsListener != null) loadPrescriptionsListener.onLoadPrescriptions();
            }
        });

        adminBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (backToRoleSelectListener != null) backToRoleSelectListener.onBack();
            }
        });

        return p;
    }

    private JPanel buildConsultantPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("Consultant View"));

        consultantLoadReferralsBtn = new JButton("Load Referrals");
        consultantBackBtn = new JButton("Back");

        p.add(consultantLoadReferralsBtn);
        p.add(consultantBackBtn);

        consultantLoadReferralsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loadReferralsListener != null) loadReferralsListener.onLoadReferrals();
            }
        });

        consultantBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (backToRoleSelectListener != null) backToRoleSelectListener.onBack();
            }
        });

        return p;
    }

    private JPanel buildPatientPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("Patient View"));

        patientLoadPrescriptionsBtn = new JButton("Load Prescriptions");
        patientBackBtn = new JButton("Back");

        p.add(patientLoadPrescriptionsBtn);
        p.add(patientBackBtn);

        patientLoadPrescriptionsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loadPrescriptionsListener != null) loadPrescriptionsListener.onLoadPrescriptions();
            }
        });

        patientBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (backToRoleSelectListener != null) backToRoleSelectListener.onBack();
            }
        });

        return p;
    }

    private void wireWindowClose() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (onCloseListener != null) onCloseListener.onClose();
                System.exit(0);
            }
        });
    }

    // ===== Card switching (called by controller) =====

    public void showRoleSelectView() {
        cardLayout.show(cards, "ROLE_SELECT");
    }

    public void showAdminView() {
        cardLayout.show(cards, "ADMIN");
    }

    public void showConsultantView() {
        cardLayout.show(cards, "CONSULTANT");
    }

    public void showPatientView() {
        cardLayout.show(cards, "PATIENT");
    }

    // ===== Listener setters (bookshop style) =====

    public void setSelectRoleListener(SelectRoleListener l) {
        this.selectRoleListener = l;
    }

    public void setBackToRoleSelectListener(BackToRoleSelectListener l) {
        this.backToRoleSelectListener = l;
    }

    public void setLoadPatientsListener(LoadPatientsListener l) {
        this.loadPatientsListener = l;
    }

    public void setLoadReferralsListener(LoadReferralsListener l) {
        this.loadReferralsListener = l;
    }

    public void setLoadPrescriptionsListener(LoadPrescriptionsListener l) {
        this.loadPrescriptionsListener = l;
    }

    public void setOnCloseListener(OnCloseListener l) {
        this.onCloseListener = l;
    }

    // ===== Table display methods (same pattern as bookshop tables) =====

    public void showPatients(List<Patient> patients) {
        String[] columns = {"First Name", "Last Name", "Email", "Phone"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            Object[] row = {
                    p.getFirstName(),
                    p.getLastName(),
                    p.getEmail(),
                    p.getPhoneNumber()
            };
            tm.addRow(row);
        }

        mainTable.setModel(tm);
    }

    public void showReferrals(List<Referral> referrals) {
        String[] columns = {"Referral ID", "Patient ID", "Status", "Urgency"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (int i = 0; i < referrals.size(); i++) {
            Referral r = referrals.get(i);
            Object[] row = {
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getStatus(),
                    r.getUrgencyLevel()
            };
            tm.addRow(row);
        }

        mainTable.setModel(tm);
    }

    public void showPrescriptions(List<Prescription> prescriptions) {
        String[] columns = {"Prescription ID", "Medication", "Status", "Pharmacy"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (int i = 0; i < prescriptions.size(); i++) {
            Prescription p = prescriptions.get(i);
            Object[] row = {
                    p.getPrescriptionId(),
                    p.getMedicationName(),
                    p.getPrescriptionStatus(),
                    p.getPharmacyName()
            };
            tm.addRow(row);
        }

        mainTable.setModel(tm);
    }
}
