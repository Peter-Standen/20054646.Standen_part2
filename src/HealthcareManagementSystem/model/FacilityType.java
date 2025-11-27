package HealthcareManagementSystem.model;

public enum FacilityType {
    GP_SURGERY(1),
    HOSPITAL(2),
    CLINIC(3);

    private final int value;

    FacilityType(int value) { this.value = value; }

    // get methods
    public int getValue() { return value; }
}
