import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * View class for the Healthcare Management System
 * Handles all GUI components and user interface
 */
public class HmsView extends JFrame {
    private HmsController controller;
    private JTabbedPane tabbedPane;

    // Patients panel components
    private JTable patientsTable;
    private DefaultTableModel patientsTableModel;

    // Referrals panel components
    private JTable referralsTable;
    private DefaultTableModel referralsTableModel;

    // Prescriptions panel components
    private JTable prescriptionsTable;
    private DefaultTableModel prescriptionsTableModel;

    // Appointments panel components
    private JTable appointmentsTable;
    private DefaultTableModel appointmentsTableModel;

    // Stored role this is used to control which tabs are visible
    private String currentRole = "";

    // Listener References

    // Refresh/load listeners
    private Runnable refreshPatientsListener;
    private Runnable refreshReferralsListener;
    private Runnable refreshPrescriptionsListener;
    private Runnable refreshAppointmentsListener;

    // Create listeners
    private CreateReferralListener createReferralListener;
    private CreatePrescriptionListener createPrescriptionListener;
    private CreateAppointmentListener createAppointmentListener;

    // Update/edit listeners
    private UpdateReferralListener updateReferralListener;
    private UpdatePrescriptionListener updatePrescriptionListener;
    private UpdateAppointmentListener updateAppointmentListener;

    // Cancel listener (appointment)
    private CancelAppointmentListener cancelAppointmentListener;

    // Print listeners (consultant)
    private PrintReferralListener printReferralListener;
    private PrintPrescriptionListener printPrescriptionListener;

    // Close listener
    private Runnable onCloseListener;

    // Role selection components, additional user selection code!
    private JPanel rolePanel;
    private JLabel roleLabel;
    private JButton adminRoleButton;
    private JButton consultantRoleButton;
    private JButton patientRoleButton;

    public HmsView() {
        setTitle("Healthcare Management System - MVC Architecture");
        setSize(1600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    public void setController(HmsController controller) {
        this.controller = controller;
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Build role selection bar (no login, just view switching), additional user selection code!
        rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roleLabel = new JLabel("Current role: (none)");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        adminRoleButton = new JButton("Admin");
        consultantRoleButton = new JButton("Consultant");
        patientRoleButton = new JButton("Patient");

        adminRoleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyRole("ADMIN");
                roleLabel.setText("Current role: ADMIN");
            }
        });

        consultantRoleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyRole("CONSULTANT");
                roleLabel.setText("Current role: CONSULTANT");
            }
        });

        patientRoleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyRole("PATIENT");
                roleLabel.setText("Current role: PATIENT");
            }
        });

        rolePanel.add(roleLabel);
        rolePanel.add(adminRoleButton);
        rolePanel.add(consultantRoleButton);
        rolePanel.add(patientRoleButton);
        add(rolePanel, BorderLayout.NORTH);

        // Default table shows everything
        tabbedPane.addTab("Patients", createPatientsPanel());
        tabbedPane.addTab("Referrals", createReferralsPanel());
        tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());
        tabbedPane.addTab("Appointments", createAppointmentsPanel());

        add(tabbedPane, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (onCloseListener != null) {
                    onCloseListener.run();
                }
            }
        });
    }

    // I have introduced role handling with tabs invisible by removal
    public void applyRole(String role) {
        if (role == null) role = "";
        currentRole = role.trim().toUpperCase();

        // Rebuild tabs in a deterministic order
        tabbedPane.removeAll();

        if ("ADMIN".equals(currentRole)) {
            tabbedPane.addTab("Patients", createPatientsPanel());
            tabbedPane.addTab("Referrals", createReferralsPanel());
            tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());
            tabbedPane.addTab("Appointments", createAppointmentsPanel());
        } else if ("CONSULTANT".equals(currentRole)) {
            tabbedPane.addTab("Referrals", createReferralsPanel());
            tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());
            tabbedPane.addTab("Appointments", createAppointmentsPanel());
        } else if ("PATIENT".equals(currentRole)) {
            tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());
            tabbedPane.addTab("Appointments", createAppointmentsPanel());
        } else {
            // Unknown role, show everything (safe fallback)
            tabbedPane.addTab("Patients", createPatientsPanel());
            tabbedPane.addTab("Referrals", createReferralsPanel());
            tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());
            tabbedPane.addTab("Appointments", createAppointmentsPanel());
        }

        // Force layout refresh
        revalidate();
        repaint();
    }

    // ========== Patient Panel ==========
    private JPanel createPatientsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Patients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = { "Patient ID", "First Name", "Last Name", "DOB", "NHS No", "Gender", "Phone", "Email",
                "Address", "Postcode", "Emergency Name", "Emergency Phone", "Registration Date", "GP Surgery ID"
        };

        patientsTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        patientsTable = new JTable(patientsTableModel);
        JScrollPane scrollPane = new JScrollPane(patientsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (refreshPatientsListener != null) {
                    refreshPatientsListener.run();
                }
            }
        });

        buttonsPanel.add(refreshButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshPatientsTable(ArrayList<Patient> patients, HmsModel model) {
        if (patientsTableModel == null) return;

        patientsTableModel.setRowCount(0);

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);

            Object[] row = {
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
            };

            patientsTableModel.addRow(row);
        }
    }

    // ========== Referrals Panel ==========
    private JPanel createReferralsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Referrals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = { "Referral ID", "Patient", "Referring Clinician", "Referred To Clinician",
                "Referring Facility", "Referred To Facility", "Referral Date", "Urgency", "Reason", "Clinical Summary",
                "Investigations", "Status", "Appointment ID", "Notes", "Created Date", "Last Updated"
        };

        referralsTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        referralsTable = new JTable(referralsTableModel);
        JScrollPane scrollPane = new JScrollPane(referralsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (refreshReferralsListener != null) {
                    refreshReferralsListener.run();
                }
            }
        });

        JButton createButton = new JButton("Create Referral");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreateReferralDialog();
            }
        });

        JButton updateButton = new JButton("Update Status");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUpdateReferralDialog();
            }
        });

        JButton printButton = new JButton("Print to File");
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPrintReferralDialog();
            }
        });

        // This is role-based button visibility where I have introduced the different users
        if ("PATIENT".equals(currentRole)) {
            createButton.setVisible(false);
            updateButton.setVisible(false);
            printButton.setVisible(false);
        }
        if ("ADMIN".equals(currentRole)) {
            createButton.setVisible(false); // admin creates appointments, consultant creates referrals
            printButton.setVisible(false);
        }

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(createButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(printButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshReferralsTable(ArrayList<Referral> referrals, HmsModel model) {
        if (referralsTableModel == null) return;

        referralsTableModel.setRowCount(0);

        for (int i = 0; i < referrals.size(); i++) {
            Referral referral = referrals.get(i);

            String patient = safeLabelPatient(model, referral.getPatientId());
            String fromClinician = safeLabelClinician(model, referral.getReferringClinicianId());
            String toClinician = safeLabelClinician(model, referral.getReferredToClinicianId());
            String fromFacility = safeLabelFacility(model, referral.getReferringFacilityId());
            String toFacility = safeLabelFacility(model, referral.getReferredToFacilityId());

            Object[] row = {
                    referral.getReferralId(),
                    patient,
                    fromClinician,
                    toClinician,
                    fromFacility,
                    toFacility,
                    referral.getReferralDate(),
                    referral.getUrgencyLevel(),
                    referral.getReferralReason(),
                    referral.getClinicalSummary(),
                    referral.getRequestedInvestigations(),
                    referral.getStatus(),
                    referral.getAppointmentId(),
                    referral.getNotes(),
                    referral.getCreatedDate(),
                    referral.getLastUpdated()
            };

            referralsTableModel.addRow(row);
        }
    }

    private void showCreateReferralDialog() {
        JDialog dialog = new JDialog(this, "Create Referral", true);
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField patientIdField = new JTextField();
        JTextField fromFacilityField = new JTextField();
        JTextField toFacilityField = new JTextField();
        JTextField fromClinicianField = new JTextField();
        JTextField toClinicianField = new JTextField();
        JTextField urgencyField = new JTextField();
        JTextField reasonField = new JTextField();
        JTextField summaryField = new JTextField();

        panel.add(new JLabel("Patient ID (e.g. P001):"));
        panel.add(patientIdField);

        panel.add(new JLabel("Referring Facility ID (e.g. F001):"));
        panel.add(fromFacilityField);

        panel.add(new JLabel("Referred To Facility ID (e.g. F002):"));
        panel.add(toFacilityField);

        panel.add(new JLabel("Referring Clinician ID (e.g. C001):"));
        panel.add(fromClinicianField);

        panel.add(new JLabel("Referred To Clinician ID (e.g. C008):"));
        panel.add(toClinicianField);

        panel.add(new JLabel("Urgency (Routine/Non-urgent/Urgent):"));
        panel.add(urgencyField);

        panel.add(new JLabel("Reason:"));
        panel.add(reasonField);

        panel.add(new JLabel("Clinical Summary:"));
        panel.add(summaryField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientId = patientIdField.getText().trim();
                String fromFacility = fromFacilityField.getText().trim();
                String toFacility = toFacilityField.getText().trim();
                String fromClinician = fromClinicianField.getText().trim();
                String toClinician = toClinicianField.getText().trim();
                String urgency = urgencyField.getText().trim();
                String reason = reasonField.getText().trim();
                String summary = summaryField.getText().trim();

                // I created just a single listener to create a warning to complete all fields.
                if (patientId.isEmpty() || fromFacility.isEmpty() || toFacility.isEmpty()
                        || fromClinician.isEmpty() || toClinician.isEmpty()
                        || urgency.isEmpty() || reason.isEmpty() || summary.isEmpty()) {
                    showErrorMessage("Please complete all fields");
                    return;
                }

                if (createReferralListener != null) {
                    createReferralListener.onCreateReferral(
                            patientId, fromFacility, toFacility,
                            fromClinician, toClinician,
                            urgency, reason, summary
                    );
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showUpdateReferralDialog() {
        int selectedRow = (referralsTable == null) ? -1 : referralsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select a referral to update");
            return;
        }

        String referralId = referralsTable.getValueAt(selectedRow, 0).toString();
        String currentStatus = referralsTable.getValueAt(selectedRow, 11) == null
                ? ""
                : referralsTable.getValueAt(selectedRow, 11).toString();

        String input = JOptionPane.showInputDialog(
                this,
                "Enter new status for Referral " + referralId + ":",
                currentStatus
        );

        if (input != null && !input.trim().isEmpty()) {
            if (updateReferralListener != null) {
                updateReferralListener.onUpdateReferralStatus(referralId, input.trim());
            }
        }
    }

    private void showPrintReferralDialog() {
        int selectedRow = (referralsTable == null) ? -1 : referralsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select a referral to print");
            return;
        }

        String referralId = referralsTable.getValueAt(selectedRow, 0).toString();

        if (printReferralListener != null) {
            printReferralListener.onPrintReferral(referralId);
        }
    }

    // ========== Prescriptions Panel ==========
    private JPanel createPrescriptionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Prescriptions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = { "Prescription ID", "Patient", "Clinician", "Appointment ID", "Date", "Medication",
                "Dosage", "Frequency", "Duration", "Quantity", "Instructions", "Pharmacy", "Status", "Issue Date",
                "Collection Date"
        };

        prescriptionsTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        prescriptionsTable = new JTable(prescriptionsTableModel);
        JScrollPane scrollPane = new JScrollPane(prescriptionsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (refreshPrescriptionsListener != null) {
                    refreshPrescriptionsListener.run();
                }
            }
        });

        JButton createButton = new JButton("Create Prescription");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreatePrescriptionDialog();
            }
        });

        JButton updateButton = new JButton("Update Status");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUpdatePrescriptionDialog();
            }
        });

        JButton printButton = new JButton("Print to File");
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPrintPrescriptionDialog();
            }
        });

        // Role-based button visibility
        if ("PATIENT".equals(currentRole)) {
            createButton.setVisible(false);
            updateButton.setVisible(false);
            printButton.setVisible(false);
        }
        if ("ADMIN".equals(currentRole)) {
            createButton.setVisible(false);
            printButton.setVisible(false);
        }

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(createButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(printButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshPrescriptionsTable(ArrayList<Prescription> prescriptions, HmsModel model) {
        if (prescriptionsTableModel == null) return;

        prescriptionsTableModel.setRowCount(0);

        for (int i = 0; i < prescriptions.size(); i++) {
            Prescription prescription = prescriptions.get(i);

            String patient = safeLabelPatient(model, prescription.getPatientId());
            String clinician = safeLabelClinician(model, prescription.getClinicianId());

            Object[] row = {
                    prescription.getPrescriptionId(),
                    patient,
                    clinician,
                    prescription.getAppointmentId(),
                    prescription.getPrescriptionDate(),
                    prescription.getMedicationName(),
                    prescription.getDosage(),
                    prescription.getFrequency(),
                    prescription.getDurationDays(),
                    prescription.getQuantity(),
                    prescription.getInstructions(),
                    prescription.getPharmacyName(),
                    prescription.getPrescriptionStatus(),
                    prescription.getIssueDate(),
                    prescription.getCollectionDate()
            };

            prescriptionsTableModel.addRow(row);
        }
    }

    private void showCreatePrescriptionDialog() {
        JDialog dialog = new JDialog(this, "Create Prescription", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField patientIdField = new JTextField();
        JTextField clinicianIdField = new JTextField();
        JTextField appointmentIdField = new JTextField();
        JTextField medicationField = new JTextField();
        JTextField pharmacyField = new JTextField();
        JTextField statusField = new JTextField();

        panel.add(new JLabel("Patient ID (e.g. P001):"));
        panel.add(patientIdField);

        panel.add(new JLabel("Clinician ID (e.g. C008):"));
        panel.add(clinicianIdField);

        panel.add(new JLabel("Appointment ID (optional, e.g. AP001):"));
        panel.add(appointmentIdField);

        panel.add(new JLabel("Medication Name:"));
        panel.add(medicationField);

        panel.add(new JLabel("Pharmacy Name:"));
        panel.add(pharmacyField);

        panel.add(new JLabel("Status (Draft/Issued/Dispensed/Collected/Cancelled):"));
        panel.add(statusField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientId = patientIdField.getText().trim();
                String clinicianId = clinicianIdField.getText().trim();
                String appointmentId = appointmentIdField.getText().trim();
                String medication = medicationField.getText().trim();
                String pharmacy = pharmacyField.getText().trim();
                String status = statusField.getText().trim();

                if (patientId.isEmpty() || clinicianId.isEmpty() || medication.isEmpty()
                        || pharmacy.isEmpty() || status.isEmpty()) {
                    showErrorMessage("Please complete all required fields");
                    return;
                }

                if (createPrescriptionListener != null) {
                    createPrescriptionListener.onCreatePrescription(
                            patientId, clinicianId, appointmentId, medication, pharmacy, status
                    );
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showUpdatePrescriptionDialog() {
        int selectedRow = (prescriptionsTable == null) ? -1 : prescriptionsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select a prescription to update");
            return;
        }

        String prescriptionId = prescriptionsTable.getValueAt(selectedRow, 0).toString();
        String currentStatus = prescriptionsTable.getValueAt(selectedRow, 12) == null
                ? ""
                : prescriptionsTable.getValueAt(selectedRow, 12).toString();

        String input = JOptionPane.showInputDialog(
                this,
                "Enter new status for Prescription " + prescriptionId + ":",
                currentStatus
        );

        if (input != null && !input.trim().isEmpty()) {
            if (updatePrescriptionListener != null) {
                updatePrescriptionListener.onUpdatePrescriptionStatus(prescriptionId, input.trim());
            }
        }
    }

    private void showPrintPrescriptionDialog() {
        int selectedRow = (prescriptionsTable == null) ? -1 : prescriptionsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select a prescription to print");
            return;
        }

        String prescriptionId = prescriptionsTable.getValueAt(selectedRow, 0).toString();

        if (printPrescriptionListener != null) {
            printPrescriptionListener.onPrintPrescription(prescriptionId);
        }
    }

    // ========== Appointments Panel ==========
    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Appointments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {
                "Appointment ID", "Patient", "Clinician", "Facility", "Date",
                "Time", "Duration", "Type", "Status", "Reason", "Notes",
                "Created Date", "Last Modified"
        };

        appointmentsTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        appointmentsTable = new JTable(appointmentsTableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (refreshAppointmentsListener != null) {
                    refreshAppointmentsListener.run();
                }
            }
        });

        JButton createButton = new JButton("Create Appointment");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreateAppointmentDialog();
            }
        });

        JButton updateButton = new JButton("Update Status");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUpdateAppointmentDialog();
            }
        });

        JButton cancelButton = new JButton("Cancel Appointment");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCancelAppointmentDialog();
            }
        });

        // Role-based visibility (patient and admin can create/update/cancel)
        if ("CONSULTANT".equals(currentRole)) {
            createButton.setVisible(false);
            updateButton.setVisible(false);
            cancelButton.setVisible(false);
        }

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(createButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(cancelButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshAppointmentsTable(ArrayList<Appointment> appointments, HmsModel model) {
        if (appointmentsTableModel == null) return;

        appointmentsTableModel.setRowCount(0);

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);

            String patient = safeLabelPatient(model, a.getPatientId());
            String clinician = safeLabelClinician(model, a.getClinicianId());
            String facility = safeLabelFacility(model, a.getFacilityId());

            Object[] row = {
                    a.getAppointmentId(),
                    patient,
                    clinician,
                    facility,
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getDurationMinutes(),
                    a.getAppointmentType(),
                    a.getStatus(),
                    a.getReasonForVisit(),
                    a.getNotes(),
                    a.getCreatedDate(),
                    a.getLastModified()
            };

            appointmentsTableModel.addRow(row);
        }
    }

    private void showCreateAppointmentDialog() {
        JDialog dialog = new JDialog(this, "Create Appointment", true);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField patientIdField = new JTextField();
        JTextField clinicianIdField = new JTextField();
        JTextField dateField = new JTextField();

        panel.add(new JLabel("Patient ID (e.g. P001):"));
        panel.add(patientIdField);

        panel.add(new JLabel("Clinician ID (optional, e.g. C001):"));
        panel.add(clinicianIdField);

        panel.add(new JLabel("Appointment Date (e.g. 2025-12-22):"));
        panel.add(dateField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientId = patientIdField.getText().trim();
                String clinicianId = clinicianIdField.getText().trim();
                String date = dateField.getText().trim();

                if (patientId.isEmpty() || date.isEmpty()) {
                    showErrorMessage("Please enter Patient ID and Date");
                    return;
                }

                if (createAppointmentListener != null) {
                    createAppointmentListener.onCreateAppointment(patientId, clinicianId, date);
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showUpdateAppointmentDialog() {
        int selectedRow = (appointmentsTable == null) ? -1 : appointmentsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select an appointment to update");
            return;
        }

        String appointmentId = appointmentsTable.getValueAt(selectedRow, 0).toString();
        String currentStatus = appointmentsTable.getValueAt(selectedRow, 8) == null
                ? ""
                : appointmentsTable.getValueAt(selectedRow, 8).toString();

        String input = JOptionPane.showInputDialog(
                this,
                "Enter new status for Appointment " + appointmentId + " (SCHEDULED, CANCELLED, COMPLETED):",
                currentStatus
        );

        if (input != null && !input.trim().isEmpty()) {
            if (updateAppointmentListener != null) {
                updateAppointmentListener.onUpdateAppointmentStatus(appointmentId, input.trim());
            }
        }
    }

    private void showCancelAppointmentDialog() {
        int selectedRow = (appointmentsTable == null) ? -1 : appointmentsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select an appointment to cancel");
            return;
        }

        String appointmentId = appointmentsTable.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Cancel Appointment " + appointmentId + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (cancelAppointmentListener != null) {
                cancelAppointmentListener.onCancelAppointment(appointmentId);
            }
        }
    }

    // ========== Helper methods ==========

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String safeLabelPatient(HmsModel model, String patientId) {
        if (model == null) return patientId;
        try { return model.formatPatientLabel(patientId); }
        catch (Exception e) { return patientId; }
    }

    private String safeLabelClinician(HmsModel model, String clinicianId) {
        if (model == null) return clinicianId;
        try { return model.formatClinicianLabel(clinicianId); }
        catch (Exception e) { return clinicianId; }
    }

    private String safeLabelFacility(HmsModel model, String facilityId) {
        if (model == null) return facilityId;
        try { return model.formatFacilityLabel(facilityId); }
        catch (Exception e) { return facilityId; }
    }

    // ========== Listener setters ==========

    public void setRefreshPatientsListener(Runnable listener) { this.refreshPatientsListener = listener; }
    public void setRefreshReferralsListener(Runnable listener) { this.refreshReferralsListener = listener; }
    public void setRefreshPrescriptionsListener(Runnable listener) { this.refreshPrescriptionsListener = listener; }
    public void setRefreshAppointmentsListener(Runnable listener) { this.refreshAppointmentsListener = listener; }

    public void setCreateReferralListener(CreateReferralListener listener) { this.createReferralListener = listener; }
    public void setCreatePrescriptionListener(CreatePrescriptionListener listener) { this.createPrescriptionListener = listener; }
    public void setCreateAppointmentListener(CreateAppointmentListener listener) { this.createAppointmentListener = listener; }

    public void setUpdateReferralListener(UpdateReferralListener listener) { this.updateReferralListener = listener; }
    public void setUpdatePrescriptionListener(UpdatePrescriptionListener listener) { this.updatePrescriptionListener = listener; }
    public void setUpdateAppointmentListener(UpdateAppointmentListener listener) { this.updateAppointmentListener = listener; }

    public void setCancelAppointmentListener(CancelAppointmentListener listener) { this.cancelAppointmentListener = listener; }

    public void setPrintReferralListener(PrintReferralListener listener) { this.printReferralListener = listener; }
    public void setPrintPrescriptionListener(PrintPrescriptionListener listener) { this.printPrescriptionListener = listener; }

    public void setOnCloseListener(Runnable listener) { this.onCloseListener = listener; }

    // =====================================================================================
    // Listener interfaces
    // =====================================================================================

    public interface CreateReferralListener {
        void onCreateReferral(String patientId,
                              String referringFacilityId,
                              String referredToFacilityId,
                              String referringClinicianId,
                              String referredToClinicianId,
                              String urgency,
                              String reason,
                              String summary);
    }

    public interface UpdateReferralListener {
        void onUpdateReferralStatus(String referralId, String newStatus);
    }

    public interface PrintReferralListener {
        void onPrintReferral(String referralId);
    }

    public interface CreatePrescriptionListener {
        void onCreatePrescription(String patientId,
                                  String clinicianId,
                                  String appointmentId,
                                  String medicationName,
                                  String pharmacyName,
                                  String status);
    }

    public interface UpdatePrescriptionListener {
        void onUpdatePrescriptionStatus(String prescriptionId, String newStatus);
    }

    public interface PrintPrescriptionListener {
        void onPrintPrescription(String prescriptionId);
    }

    public interface CreateAppointmentListener {
        void onCreateAppointment(String patientId, String clinicianId, String date);
    }

    public interface UpdateAppointmentListener {
        void onUpdateAppointmentStatus(String appointmentId, String newStatus);
    }

    public interface CancelAppointmentListener {
        void onCancelAppointment(String appointmentId);
    }
}
