package HealthcareManagementSystem.view;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {

    private JButton loadPatientsBtn = new JButton("Load Patients");
    private JTable patientsTable = new JTable();

    public AdminUI() {
        super("Healthcare Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(loadPatientsBtn, BorderLayout.NORTH);
        add(new JScrollPane(patientsTable), BorderLayout.CENTER);
    }

    public JButton getLoadPatientsBtn() {
        return loadPatientsBtn;
    }

    public JTable getPatientsTable() {
        return patientsTable;
    }
}
