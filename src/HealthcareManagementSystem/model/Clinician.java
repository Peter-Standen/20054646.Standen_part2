package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Clinician extends Staff {

    private UUID clinicianId;
    private String gmcNumber;
    private ClinicianType title;
    private String speciality;
    private String workplaceType;
    private EmploymentStatus employmentStatus;
    private Date startDate;

    public Clinician(UUID userNumber,
                     String firstName,
                     String lastName,
                     String email,
                     String phoneNumber,
                     UUID staffId,
                     Integer payrollNumber,
                     String department,
                     String lineManager,
                     String accessLevel,
                     UUID clinicianId,
                     String gmcNumber,
                     ClinicianType title,
                     String speciality,
                     String workplaceType,
                     EmploymentStatus employmentStatus,
                     Date startDate) {

        super(userNumber, firstName, lastName, email, phoneNumber, staffId, payrollNumber, department, lineManager, accessLevel);

        this.clinicianId = clinicianId;
        this.gmcNumber = gmcNumber;
        this.title = title;
        this.speciality = speciality;
        this.workplaceType = workplaceType;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    // get methods
    public UUID getClinicianId() {
        return clinicianId;
    }
    public String getGmcNumber() { return gmcNumber; }
    public ClinicianType getTitle() { return title; }
    public String getSpeciality() { return speciality; }
    public String getWorkplaceType() { return workplaceType; }
    public EmploymentStatus getEmploymentStatus() { return employmentStatus; }
    public Date getStart_date() { return startDate; }

    // set methods
    public void setClinicianId(UUID clinicianId) { this.clinicianId = clinicianId; }
    public void setGmcNumber(String gmcNumber) { this.gmcNumber = gmcNumber; }
    public void setTitle(ClinicianType title) { this.title = title; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
    public void setWorkplaceType(String workplaceType) { this.workplaceType = workplaceType; }
    public void setEmploymentStatus(EmploymentStatus employmentStatus) { this.employmentStatus = employmentStatus; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
}
