package HealthcareManagementSystem.view;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {

    private final JButton loadPatientsBtn = new JButton("Load Patients");
    private final JTable patientsTable = new JTable();

    public AdminUI() {
        super("Healthcare Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(loadPatientsBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(patientsTable), BorderLayout.CENTER);

        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    public JButton getLoadPatientsBtn() { return loadPatientsBtn; }
    public JTable getPatientsTable() { return patientsTable; }
}
