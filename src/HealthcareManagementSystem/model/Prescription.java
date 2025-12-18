package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Prescription {

    private final UUID prescriptionId;
    private Date prescriptionDate;
    private String pharmacyName;
    private PrescriptionStatus prescriptionStatus;
    private Date issueDate;
    private Date collectionDate;

    public Prescription() {
        this.prescriptionId = UUID.randomUUID();
        this.prescriptionDate = new Date();
        this.pharmacyName = null;
        this.prescriptionStatus = null;
        this.issueDate = null;
        this.collectionDate = null;
    }

    // get methods
    public UUID getPrescriptionId() { return prescriptionId; }
    public Date getPrescriptionDate() { return prescriptionDate; }
    public String getPharmacyName() { return pharmacyName; }
    public PrescriptionStatus getPrescriptionStatus() { return prescriptionStatus; }
    public Date getIssueDate() { return issueDate; }
    public Date getCollectionDate() { return collectionDate; }

    // set methods
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public void setPharmacyName(String pharmacyName) { this.pharmacyName = pharmacyName; }
    public void setPrescriptionStatus(PrescriptionStatus prescriptionStatus) { this.prescriptionStatus = prescriptionStatus; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }
    public void setCollectionDate(Date collectionDate) { this.collectionDate = collectionDate; }

}
