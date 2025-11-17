package HealthcareManagementSystem.model;

import HealthcareManagementSystem.data.StaffCsvRepository;

import java.util.Date;
import java.util.UUID;

public class Clinician extends Staff {

    private UUID clinician_id;
    private String gmc_number;
    private ClinicianType title;
    private Speciality speciality;
    private String workplace_type;
    private EmploymentStatus employment_status;
    private Date start_date;

    public Clinician(String user_number,
                     String first_name,
                     String last_name,
                     String email,
                     String phone_number,
                     UUID staff_id,
                     Integer payroll_number,
                     String department,
                     String line_manager,
                     String access_level,
                     String clinician_id,
                     String gmc_number,
                     ClinicianType title,
                     Speciality speciality,
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
