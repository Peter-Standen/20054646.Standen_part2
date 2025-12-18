package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Referral {

    private UUID referralId;
    private Date referralDate;
    private Integer urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private String requestedInvestigations;
    private String status;
    private String notes;
    private Date createdDate;
    private Date lastUpdated;
    private CommunicationMethod communicationMethod;

    public Referral() {
        this.referralId = UUID.randomUUID();
        this.referralDate = new Date();
        this.createdDate = new Date();
        this.lastUpdated = new Date();
    }

    // getters
    public UUID getReferralId() { return referralId; }
    public Date getReferralDate() { return referralDate; }
    public Integer getUrgencyLevel() { return urgencyLevel; }
    public String getReferralReason() { return referralReason; }
    public String getClinicalSummary() { return clinicalSummary; }
    public String getRequestedInvestigations() { return requestedInvestigations; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public Date getCreatedDate() { return createdDate; }
    public Date getLastUpdated() { return lastUpdated; }
    public CommunicationMethod getCommunicationMethod() { return communicationMethod; }

    // setters
    public void setReferralId(UUID referralId) { this.referralId = referralId; }
    public void setReferralDate(Date referralDate) { this.referralDate = referralDate; }
    public void setUrgencyLevel(Integer urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public void setReferralReason(String referralReason) { this.referralReason = referralReason; }
    public void setClinicalSummary(String clinicalSummary) { this.clinicalSummary = clinicalSummary; }
    public void setRequestedInvestigations(String requestedInvestigations) { this.requestedInvestigations = requestedInvestigations; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
    public void setCommunicationMethod(CommunicationMethod communicationMethod) { this.communicationMethod = communicationMethod; }
}
