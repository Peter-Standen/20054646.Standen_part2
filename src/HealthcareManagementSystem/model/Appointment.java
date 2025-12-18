package HealthcareManagementSystem.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Appointment {

    private UUID appointmentId;
    private Date appointmentDate;
    private Time appointmentTime;
    private Integer duration;
    private AppointmentType appointmentType;
    private AppointmentStatus status;
    private String reasonForVisit;
    private String notes;
    private Date createdDate;
    private Date lastModified;
    private String location;

    public Appointment() {
        this.appointmentId = UUID.randomUUID();
        this.createdDate = new Date();
        this.lastModified = new Date();
    }

    // getters
    public UUID getAppointmentId() { return appointmentId; }
    public Date getAppointmentDate() { return appointmentDate; }
    public Time getAppointmentTime() { return appointmentTime; }
    public Integer getDuration() { return duration; }
    public AppointmentType getAppointmentType() { return appointmentType; }
    public AppointmentStatus getStatus() { return status; }
    public String getReasonForVisit() { return reasonForVisit; }
    public String getNotes() { return notes; }
    public Date getCreatedDate() { return createdDate; }
    public Date getLastModified() { return lastModified; }
    public String getLocation() { return location; }

    // setters
    public void setAppointmentId(UUID appointmentId) { this.appointmentId = appointmentId; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(Time appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public void setAppointmentType(AppointmentType appointmentType) { this.appointmentType = appointmentType; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public void setReasonForVisit(String reasonForVisit) { this.reasonForVisit = reasonForVisit; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public void setLastModified(Date lastModified) { this.lastModified = lastModified; }
    public void setLocation(String location) { this.location = location; }
}
