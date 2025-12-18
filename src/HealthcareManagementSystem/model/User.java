package HealthcareManagementSystem.model;

import java.util.UUID;

public class User {

    private UUID userNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public User(UUID userNumber, String firstName, String lastName, String email, String phoneNumber) {
        this.userNumber = userNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // get methods
    public UUID getUserNumber() { return userNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    // set methods
    public void setUserNumber(UUID userNumber) { this.userNumber = userNumber; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}
