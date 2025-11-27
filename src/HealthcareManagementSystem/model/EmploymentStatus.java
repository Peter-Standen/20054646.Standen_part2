package HealthcareManagementSystem.model;

public enum EmploymentStatus {

    Full_Time(1),
    Part_Time(2),
    Agency(3);

    private final int value;

    EmploymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
}
