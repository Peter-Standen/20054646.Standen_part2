package HealthcareManagementSystem.model;

import java.util.UUID;

public class Staff extends User {

    private UUID staffId;
    private Integer payrollNumber;
    private String department;
    private String lineManager;
    private String accessLevel;

    public Staff(UUID userNumber,
                 String firstName,
                 String lastName,
                 String email,
                 String phoneNumber,
                 UUID staffId,
                 Integer payrollNumber,
                 String department,
                 String lineManager,
                 String accessLevel) {

        super(userNumber, firstName, lastName, email, phoneNumber);

        this.staffId = staffId;
        this.payrollNumber = payrollNumber;
        this.department = department;
        this.lineManager = lineManager;
        this.accessLevel = accessLevel;
    }

    // get methods
    public UUID getStaffId() { return staffId; }
    public Integer getPayrollNumber() { return payrollNumber; }
    public String getDepartment() { return department; }
    public String getLineManager() { return lineManager; }
    public String getAccessLevel() { return accessLevel; }

    // set methods
    public void setStaffId(UUID staffId) { this.staffId = staffId; }
    public void setPayrollNumber(Integer payroll_number) { this.payrollNumber = payroll_number; }
    public void setDepartment(String department) { this.department = department; }
    public void setLineManager(String lineManager) { this.lineManager = lineManager; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

}
