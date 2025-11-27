package HealthcareManagementSystem.model;

public class PhoneNumber {
    private String value;

    public PhoneNumber(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        this.value = value;
    }
}
