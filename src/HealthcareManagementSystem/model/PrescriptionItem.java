package HealthcareManagementSystem.model;

public class PrescriptionItem {

    private String medication_name;
    private String dosage;
    private String frequency;
    private String duration_days;
    private String quantity;
    private String instructions;

    public PrescriptionItem() {
        this.medication_name = "";
        this.dosage = "";
        this.frequency = "";
        this.duration_days = "";
        this.quantity = "";
        this.instructions = "";
    }


    // get methods
    public String getMedication_name() { return medication_name; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public String getDuration_days() { return duration_days; }
    public String getQuantity() { return quantity; }
    public String getInstructions() { return instructions; }

    // set methods
    public void setMedication_name(String medication_name) { this.medication_name = medication_name; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public void setDuration_days(String duration_days) { this.duration_days = duration_days; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

}
