package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Clinician extends Staff {

    private UUID clinician_id;
    private String gmc_number;
    private ClinicianType title;
    private String speciality;
    private String workplace_type;
    private EmploymentStatus employment_status;
    private Date start_date;

    public Clinician(UUID user_number,
                     String first_name,
                     String last_name,
                     String email,
                     String phone_number,
                     UUID staff_id,
                     Integer payroll_number,
                     String department,
                     String line_manager,
                     String access_level,
                     UUID clinician_id,
                     String gmc_number,
                     ClinicianType title,
                     String speciality,
                     String workplace_type,
                     EmploymentStatus employment_status,
                     Date start_date) {

        super(user_number, first_name, last_name, email, phone_number, staff_id, payroll_number, department, line_manager, access_level);

        this.clinician_id = clinician_id;
        this.gmc_number = gmc_number;
        this.title = title;
        this.speciality = speciality;
        this.workplace_type = workplace_type;
        this.employment_status = employment_status;
        this.start_date = start_date;
    }

    // get methods
    public UUID getClinician_id() {
        return clinician_id;
    }
    public String getGmc_number() { return gmc_number; }
    public ClinicianType getTitle() { return title; }
    public String getSpeciality() { return speciality; }
    public String getWorkplace_type() { return workplace_type; }
    public EmploymentStatus getEmployment_status() { return employment_status; }
    public Date getStart_date() { return start_date; }

    // set methods
    public void setClinician_id(UUID clinician_id) { this.clinician_id = clinician_id; }
    public void setGmc_number(String gmc_number) { this.gmc_number = gmc_number; }
    public void setTitle(ClinicianType title) { this.title = title; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
    public void setWorkplace_type(String workplace_type) { this.workplace_type = workplace_type; }
    public void setEmployment_status(EmploymentStatus employment_status) { this.employment_status = employment_status; }
    public void setStart_date(Date start_date) { this.start_date = start_date; }
}
