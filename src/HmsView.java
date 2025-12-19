
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HmsView extends JFrame {

    private HmsController controller;

    private final JButton loadPatientsBtn = new JButton("Load Patients");
    private final JTable patientsTable = new JTable();

    public HmsView() {
        super("Healthcare Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(loadPatientsBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(patientsTable), BorderLayout.CENTER);

        // initialise an empty table model so it looks sane on startup
        patientsTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"First Name", "Last Name", "Email", "Phone"}
        ));

        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    public JButton getLoadPatientsBtn() {
        return loadPatientsBtn;
    }

    public JTable getPatientsTable() {
        return patientsTable;
    }

    public void setController(HmsController controller) {
        this.controller = controller;
    }

    // Called by controller after model loads patients
    public void showPatients(List<Patient> patients) {
        String[] columns = {"First Name", "Last Name", "Email", "Phone"};
        DefaultTableModel tm = new DefaultTableModel(columns, 0);

        for (Patient p : patients) {
            tm.addRow(new Object[] {
                    p.getFirstName(),
                    p.getLastName(),
                    p.getEmail(),
                    p.getPhoneNumber()
            });
        }

        patientsTable.setModel(tm);
    }
}
