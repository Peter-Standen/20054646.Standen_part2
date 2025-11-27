package HealthcareManagementSystem.model;

public enum ClinicianType {

    GP(1),
    Consultant(2),
    Senior_Nurse(3),
    Practice_Nurse(4),
    Staff_Nurse(5);

    private final int value;

    ClinicianType(int value) { this.value = value; }

    // get methods
    public int getValue() { return value; }
}
