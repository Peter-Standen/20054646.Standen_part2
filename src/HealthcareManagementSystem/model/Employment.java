package HealthcareManagementSystem.model;

import java.util.UUID;
import java.util.Date;

public class Employment {
    private UUID employment_id;
    private String employment_status;
    private String role;
    private Date start_date;
    private Date end_date;
    private Boolean is_primary_site;

    public Employment() {
        this.employment_id = UUID.randomUUID();
        this.employment_status = "";
        this.role = "";
        this.start_date = new Date();
        this.end_date = new Date();
        this.is_primary_site = false;
    }

    // get methods
    public UUID getEmployment_id() {
        return employment_id;
    }
    public String getEmployment_status() {
        return employment_status;
    }
    public String getRole() {
        return role;
    }
    public Date getStart_date() {
        return start_date;
    }
    public Date getEnd_date() {
        return end_date;
    }
    public Boolean getIs_primary_site() {
        return is_primary_site;
    }

    // set methods
    public void setEmployment_id(UUID employment_id) {
        this.employment_id = employment_id;
    }
    public void setEmployment_status(String employment_status) {
        this.employment_status = employment_status;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    public void setIs_primary_site(Boolean is_primary_site) {
        this.is_primary_site = is_primary_site;
    }
}
