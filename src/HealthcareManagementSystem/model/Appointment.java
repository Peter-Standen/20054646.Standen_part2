package HealthcareManagementSystem.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Appointment {

    private UUID appointment_id;
    private Date appointment_date;
    private Time appointment_time;
    private Integer duration;
    private AppointmentType appointment_type;
    private AppointmentStatus status;
    private String reason_for_visit;
    private String notes;
    private Date created_date;
    private Date last_modified;
    private String location;

    public Appointment() {
        this.appointment_id = UUID.randomUUID();
        this.appointment_date = null;
        this.appointment_time = null;
        this.duration = null;
        this.appointment_type = null;
        this.status = null;
        this.reason_for_visit = null;
        this.notes = null;
        this.created_date = null;
        this.last_modified = null;
        this.location = null;
    }

    public UUID getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(UUID appointment_id) {
        this.appointment_id = appointment_id;
    }

    public Date getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(Date appointment_date) {
        this.appointment_date = appointment_date;
    }

    public Time getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(Time appointment_time) {
        this.appointment_time = appointment_time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public AppointmentType getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(AppointmentType appointment_type) {
        this.appointment_type = appointment_type;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getReason_for_visit() {
        return reason_for_visit;
    }

    public void setReason_for_visit(String reason_for_visit) {
        this.reason_for_visit = reason_for_visit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
