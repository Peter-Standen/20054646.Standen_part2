package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class PatientRecord {

    private final UUID recordId;
    private UUID patientId;
    private UUID appointmentId;
    private UUID clinicianId;
    private Condition condition;
    private String notes;
    private Date createdDate;
    private Date lastModified;

    public PatientRecord() {
        this.recordId = UUID.randomUUID();
        this.patientId = null;
        this.appointmentId = null;
        this.clinicianId = null;
        this.condition = null;
        this.notes = null;
        this.createdDate = new Date();
        this.lastModified = new Date();
    }

    // get methods
    public UUID getRecordId() { return recordId; }
    public UUID getPatientId() { return patientId; }
    public UUID getAppointmentId() { return appointmentId; }
    public UUID getClinicianId() { return clinicianId; }
    public Condition getCondition() { return condition; }
    public String getNotes() { return notes; }
    public Date getCreatedDate() { return createdDate; }
    public Date getLastModified() { return lastModified; }

    // set methods
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public void setAppointmentId(UUID appointmentId) { this.appointmentId = appointmentId; }
    public void setClinicianId(UUID clinicianId) { this.clinicianId = clinicianId; }
    public void setCondition(Condition condition) { this.condition = condition; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setLastModified(Date lastModified) { this.lastModified = lastModified; }
}
