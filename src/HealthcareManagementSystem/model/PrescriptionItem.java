package HealthcareManagementSystem.model;

public class PrescriptionItem {

    private String medicationName;
    private String dosage;
    private String frequency;
    private String durationDays;
    private String quantity;
    private String instructions;

    public PrescriptionItem() {
        this.medicationName = "";
        this.dosage = "";
        this.frequency = "";
        this.durationDays = "";
        this.quantity = "";
        this.instructions = "";
    }


    // get methods
    public String getMedicationName() { return medicationName; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public String getDurationDays() { return durationDays; }
    public String getQuantity() { return quantity; }
    public String getInstructions() { return instructions; }

    // set methods
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public void setDurationDays(String durationDays) { this.durationDays = durationDays; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

}
