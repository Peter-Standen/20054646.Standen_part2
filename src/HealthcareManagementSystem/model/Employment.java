package HealthcareManagementSystem.model;

import java.util.UUID;
import java.util.Date;

public class Employment {
    private UUID employmentId;
    private String employmentStatus;
    private String role;
    private Date startDate;
    private Date endDate;
    private Boolean isPrimarySite;

    public Employment() {
        this.employmentId = UUID.randomUUID();
        this.employmentStatus = "";
        this.role = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.isPrimarySite = false;
    }

    // get methods
    public UUID getEmploymentId() {
        return employmentId;
    }
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    public String getRole() {
        return role;
    }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() {
        return endDate;
    }
    public Boolean getIsPrimarySite() {
        return isPrimarySite;
    }

    // set methods
    public void setEmploymentId(UUID employmentId) {
        this.employmentId = employmentId;
    }
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setIsPrimarySite(Boolean isPrimarySite) {
        this.isPrimarySite = isPrimarySite;
    }
}
