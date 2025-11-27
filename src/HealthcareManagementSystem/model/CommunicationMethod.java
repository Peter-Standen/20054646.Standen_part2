package HealthcareManagementSystem.model;

public enum CommunicationMethod {

    Email(1),
    Telephone(2),
    Electronic_Health_Record(3);

    private final int value;

    CommunicationMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
}
