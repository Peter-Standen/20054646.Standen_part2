package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Prescription {

    private final UUID prescription_id;
    private Date prescription_date;
    private String pharmacy_name;
    private PrescriptionStatus prescription_status;
    private Date issue_date;
    private Date collection_date;

    public Prescription() {
        this.prescription_id = UUID.randomUUID();
        this.prescription_date = new Date();
        this.pharmacy_name = null;
        this.prescription_status = null;
        this.issue_date = null;
        this.collection_date = null;
    }

    // get methods
    public UUID getPrescription_id() { return prescription_id; }
    public Date getPrescription_date() { return prescription_date; }
    public String getPharmacy_name() { return pharmacy_name; }
    public PrescriptionStatus getPrescription_status() { return prescription_status; }
    public Date getIssue_date() { return issue_date; }
    public Date getCollection_date() { return collection_date; }

    // set methods
    public void setPrescription_date(Date prescription_date) { this.prescription_date = prescription_date; }
    public void setPharmacy_name(String pharmacy_name) { this.pharmacy_name = pharmacy_name; }
    public void setPrescription_status(PrescriptionStatus prescription_status) { this.prescription_status = prescription_status; }
    public void setIssue_date(Date issue_date) { this.issue_date = issue_date; }
    public void setCollection_date(Date collection_date) { this.collection_date = collection_date; }

}
