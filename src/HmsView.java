import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HmsView extends JFrame {

    private HmsController controller;

    private final JButton loadPatientsBtn = new JButton("Load Patients");
    private final JButton loadReferralsBtn = new JButton("Load Referrals");
    private final JButton loadPrescriptionsBtn = new JButton("Load Prescriptions");

    private final JTable mainTable = new JTable();

    public HmsView() {
        super("Healthcare Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(loadPatientsBtn);
        top.add(loadReferralsBtn);
        top.add(loadPrescriptionsBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(mainTable), BorderLayout.CENTER);

        mainTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"First Name", "Last Name", "Email", "Phone"}
        ));

        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    public void setController(HmsController controller) {
        this.controller = controller;
    }

    public JButton getLoadPatientsBtn() { return loadPatientsBtn; }
    public JButton getLoadReferralsBtn() { return loadReferralsBtn; }
    public JButton getLoadPrescriptionsBtn() { return loadPrescriptionsBtn; }

    public JTable getMainTable() { return mainTable; }

    public void showPatients(List<Patient> patients) {
        String[] columns = {"First Name", "Last Name", "Email", "Phone"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (Patient p : patients) {
            tm.addRow(new Object[]{
                    p.getFirstName(),
                    p.getLastName(),
                    p.getEmail(),
                    p.getPhoneNumber()
            });
        }

        mainTable.setModel(tm);
    }

    public void showReferrals(List<Referral> referrals) {
        String[] columns = {"Referral ID", "Patient ID", "Status", "Urgency"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (Referral r : referrals) {
            tm.addRow(new Object[]{
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getStatus(),
                    r.getUrgencyLevel()
            });
        }

        mainTable.setModel(tm);
    }

    public void showPrescriptions(List<Prescription> prescriptions) {
        String[] columns = {"Prescription ID", "Medication", "Status", "Pharmacy"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (Prescription p : prescriptions) {
            tm.addRow(new Object[]{
                    p.getPrescriptionId(),
                    p.getMedicationName(),
                    p.getPrescriptionStatus(),
                    p.getPharmacyName()
            });
        }

        mainTable.setModel(tm);
    }
}
