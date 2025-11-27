package HealthcareManagementSystem.model;

public enum AppointmentType {

    Routine_Consultation(1),
    Vaccination(2),
    Follow_Up(3),
    Urgent_Consultation(4),
    Specialist_Consultation(5),
    Emergency(6),
    Health_Check(7);

    private final int value;

    AppointmentType(int value) { this.value = value; }

    // get methods
    public int getValue() { return value; }
}
