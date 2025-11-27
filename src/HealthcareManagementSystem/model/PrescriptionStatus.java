package HealthcareManagementSystem.model;

public enum PrescriptionStatus {

    Draft(1),
    Issued(2),
    Dispensed(3),
    Collected(4),
    Cancelled(5);

    private final int value;

    PrescriptionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
}
