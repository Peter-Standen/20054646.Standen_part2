package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class PatientRecord {

    private final UUID record_id;
    private UUID patient_id;
    private UUID appointment_id;
    private UUID clinician_id;
    private Condition condition;
    private String notes;
    private Date created_date;
    private Date last_modified;

    public PatientRecord() {
        this.record_id = UUID.randomUUID();
        this.patient_id = null;
        this.appointment_id = null;
        this.clinician_id = null;
        this.condition = null;
        this.notes = null;
        this.created_date = new Date();
        this.last_modified = new Date();
    }

    // get methods
    public UUID getRecord_id() { return record_id; }
    public UUID getPatient_id() { return patient_id; }
    public UUID getAppointment_id() { return appointment_id; }
    public UUID getClinician_id() { return clinician_id; }
    public Condition getCondition() { return condition; }
    public String getNotes() { return notes; }
    public Date getCreated_date() { return created_date; }
    public Date getLast_modified() { return last_modified; }

    // set methods
    public void setPatient_id(UUID patient_id) { this.patient_id = patient_id; }
    public void setAppointment_id(UUID appointment_id) { this.appointment_id = appointment_id; }
    public void setClinician_id(UUID clinician_id) { this.clinician_id = clinician_id; }
    public void setCondition(Condition condition) { this.condition = condition; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setLast_modified(Date last_modified) { this.last_modified = last_modified; }
}
