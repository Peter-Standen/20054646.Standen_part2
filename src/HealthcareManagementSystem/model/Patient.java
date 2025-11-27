package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Patient extends User {

    private UUID patient_id;
    private Integer nhs_number;
    private Date date_of_birth;
    private String gender;
    private String emergency_contact_name;
    private String emergency_contact_phone;
    private Date registration_date;
    private UUID gp_surgery_id;

    public Patient(UUID user_number,
                   String first_name,
                   String last_name,
                   String email,
                   String phone_number) {

        super(user_number, first_name, last_name, email, phone_number);

        this.patient_id = UUID.randomUUID();
        this.nhs_number = 0;
        this.date_of_birth = new Date();
        this.gender = "";
        this.emergency_contact_name = "";
        this.emergency_contact_phone = "";
        this.registration_date = new Date();
        this.gp_surgery_id = UUID.randomUUID();
    }

    // get methods
    public UUID getPatient_id() { return patient_id; }
    public Integer getNhs_number() { return nhs_number; }
    public Date getDate_of_birth() { return date_of_birth; }
    public String getGender() { return gender; }
    public String getEmergency_contact_name() { return emergency_contact_name; }
    public String getEmergency_contact_phone() { return emergency_contact_phone; }
    public Date getRegistration_date() { return registration_date; }
    public UUID getGp_surgery_id() { return gp_surgery_id; }

    // set methods
    public void setPatient_id(UUID patient_id) { this.patient_id = patient_id; }
    public void setNhs_number(Integer nhs_number) { this.nhs_number = nhs_number; }
    public void setDate_of_birth(Date date_of_birth) { this.date_of_birth = date_of_birth; }
    public void setGender(String gender) { this.gender = gender; }
    public void setEmergency_contact_name(String emergency_contact_name) { this.emergency_contact_name = emergency_contact_name; }
    public void setEmergency_contact_phone(String emergency_contact_phone) { this.emergency_contact_phone = emergency_contact_phone; }
    public void setRegistration_date(Date registration_date) { this.registration_date = registration_date; }
    public void setGp_surgery_id(UUID gp_surgery_id) { this.gp_surgery_id = gp_surgery_id; }

}
