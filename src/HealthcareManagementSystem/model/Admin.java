package HealthcareManagementSystem.model;

import java.util.Date;

public class Admin extends Staff {

    private String staff_id;
    private String payrollNumber;
    private String department;
    private String line_manager;
    private String access_level;

    public

}


    public Clinician(String user_number,
                     String first_name,
                     String last_name,
                     String email,
                     String phone_number,
                     String clinician_id,
                     String gmc_number,
                     ClinicianType title,
                     Speciality speciality,
                     String workplace_type,
                     EmploymentStatus employment_status,
                     Date start_date) {

        super(user_number, first_name, last_name, email, phone_number);

        this.clinician_id = clinician_id;
        this.gmc_number = gmc_number;
        this.title = title;
        this.speciality = speciality;
        this.workplace_type = workplace_type;
        this.employment_status = employment_status;
        this.start_date = start_date;
    }

    public String getClinician_id() {
        return clinician_id;
    }

    public void setClinician_id(String clinician_id) {
        this.clinician_id = clinician_id;
    }

    public String getGmc_number() {
        return gmc_number;
    }

    public void setGmc_number(String gmc_number) {
        this.gmc_number = gmc_number;
    }

    public ClinicianType getTitle() {
        return title;
    }

    public void setTitle(ClinicianType title) {
        this.title = title;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public String getWorkplace_type() {
        return workplace_type;
    }

    public void setWorkplace_type(String workplace_type) {
        this.workplace_type = workplace_type;
    }

    public EmploymentStatus getEmployment_status() {
        return employment_status;
    }

    public void setEmployment_status(EmploymentStatus employment_status) {
        this.employment_status = employment_status;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
}

