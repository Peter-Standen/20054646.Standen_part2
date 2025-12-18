package HealthcareManagementSystem.model;

import java.util.Date;
import java.util.UUID;

public class Patient extends User {

    private UUID patientId;
    private Integer nhsNumber;
    private Date dateOfBirth;
    private String gender;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private Date registrationDate;
    private UUID gpSurgeryId;

    public Patient(UUID userNumber,
                   String firstName,
                   String lastName,
                   String email,
                   String phoneNumber) {

        super(userNumber, firstName, lastName, email, phoneNumber);

        this.patientId = UUID.randomUUID();
        this.nhsNumber = null;
        this.dateOfBirth = null;
        this.gender = "";
        this.emergencyContactName = "";
        this.emergencyContactPhone = "";
        this.registrationDate = new Date();
        this.gpSurgeryId = null;
    }

    // get methods
    public UUID getPatientId() { return patientId; }
    public Integer getNhsNumber() { return nhsNumber; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public Date getRegistrationDate() { return registrationDate; }
    public UUID getGpSurgeryId() { return gpSurgeryId; }

    // set methods
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public void setNhsNumber(Integer nhsNumber) { this.nhsNumber = nhsNumber; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setGender(String gender) { this.gender = gender; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    public void setGpSurgeryId(UUID gpSurgeryId) { this.gpSurgeryId = gpSurgeryId; }

}
