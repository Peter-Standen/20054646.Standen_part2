package HealthcareManagementSystem.model;

import java.util.UUID;

public class Staff extends User {

    private UUID staff_id;
    private Integer payrollNumber;
    private String department;
    private String line_manager;
    private String access_level;

    // Correct constructor
    public Staff(UUID user_number,
                 String first_name,
                 String last_name,
                 String email,
                 String phone_number,
                 UUID staff_id,
                 Integer payroll_number,
                 String department,
                 String line_manager,
                 String access_level) {

        super(user_number, first_name, last_name, email, phone_number);

        this.staff_id = staff_id;
        this.payrollNumber = payroll_number;
        this.department = department;
        this.line_manager = line_manager;
        this.access_level = access_level;
    }
    public UUID getStaff_id() {
        return staff_id;
    }
    public void setStaff_id(UUID staff_id) {
        this.staff_id = staff_id;
    }
    public Integer getPayrollNumber() {
        return payrollNumber;
    }
    public void setPayrollNumber(Integer payroll_number) {
        this.payrollNumber = payroll_number;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getLine_manager() {
        return line_manager;
    }
    public void setLine_manager(String line_manager) {
        this.line_manager = line_manager;
    }
    public String getAccess_level() {
        return access_level;
    }
    public void setAccess_level(String access_level) {
        this.access_level = access_level;
    }
}
