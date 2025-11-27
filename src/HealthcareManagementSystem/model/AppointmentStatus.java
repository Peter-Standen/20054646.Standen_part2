package HealthcareManagementSystem.model;

public enum AppointmentStatus {
    SCHEDULED(1),
    CANCELLED(2),
    COMPLETED(3);

    private final int value;

    AppointmentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
