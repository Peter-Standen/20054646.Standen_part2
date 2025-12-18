package HealthcareManagementSystem.controller;

import HealthcareManagementSystem.model.Patient;
import HealthcareManagementSystem.service.PatientRecordService;
import HealthcareManagementSystem.view.AdminUI;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminController {

    private final AdminUI view;
    private final PatientRecordService patientService;

    public AdminController(AdminUI view, PatientRecordService patientService) {
        this.view = view;
        this.patientService = patientService;

        view.getLoadPatientsBtn().addActionListener(e -> loadPatients());
    }

    private void loadPatients() {
        List<Patient> patients = patientService.getAllPatients();

        String[] cols = {"UserNumber", "FirstName", "LastName", "Email", "Phone"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Patient p : patients) {
            model.addRow(new Object[] {
                    p.getUserNumber(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getEmail(),
                    p.getPhoneNumber()
            });
        }

        view.getPatientsTable().setModel(model);
    }
}
