package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Referral {

    private UUID referral_id;
    private Date referral_date;
    private Integer urgency_level;
    private String referral_reason;
    private String clinical_summary;
    private String requested_investigations;
    private String status;
    private String notes;
    private Date created_date;
    private Date last_updated;
    private CommunicationMethod communication_method;

    public Referral() {
        this.referral_id = UUID.randomUUID();
        this.referral_date = new Date();
        this.urgency_level = null;
        this.referral_reason = null;
        this.clinical_summary = null;
        this.requested_investigations = null;
        this.status = null;
        this.notes = null;
        this.created_date = new Date();
        this.last_updated = new Date();
        this.communication_method = null;
    }

    // get methods
    public UUID getReferral_id() {
        return referral_id;
    }
    public Date getReferral_date() {
        return referral_date;
    }
    public Integer getUrgency_level() {
        return urgency_level;
    }
    public String getReferral_reason() {
        return referral_reason;
    }
    public String getClinical_summary() {
        return clinical_summary;
    }
    public String getRequested_investigations() {
        return requested_investigations;
    }
    public String getStatus() {
        return status;
    }
    public String getNotes() {
        return notes;
    }
    public Date getCreated_date() {
        return created_date;
    }
    public Date getLast_updated() {
        return last_updated;
    }
    public CommunicationMethod getCommunication_method() {
        return communication_method;
    }

    // set methods
    public void setReferral_id_id(UUID referral_id) {
        this.referral_id = referral_id;
    }
    public void setReferral_date(Date referral_date) { this.referral_date = referral_date; }
    public void setUrgency_level(Integer urgency_level) {
        this.urgency_level = urgency_level;
    }
    public void setReferral_reason(String referral_reason) {
        this.referral_reason = referral_reason;
    }
    public void setClinical_summary(String clinical_summary) {
        this.clinical_summary = clinical_summary;
    }
    public void setRequested_investigations(String requested_investigations) { this.requested_investigations = requested_investigations; }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }
    public void setCommunication_method(CommunicationMethod communication_method) { this.communication_method = communication_method; }

}
