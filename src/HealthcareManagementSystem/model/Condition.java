package HealthcareManagementSystem.model;

import java.util.UUID;

public class Condition {

    private UUID condition_reference_number;
    private String condition_name;
    private String symptom_1;
    private String symptom_2;
    private String symptom_3;
    private String symptom_4;
    private String symptom_5;
    private String additional_symptoms;

    public Condition() {
        this.condition_reference_number = UUID.randomUUID();
        this.condition_name = "";
        this.symptom_1 = "";
        this.symptom_2 = "";
        this.symptom_3 = "";
        this.symptom_4 = "";
        this.symptom_5 = "";
        this.additional_symptoms = "";
    }

    // get methods
    public UUID getCondition_reference_number() { return condition_reference_number; }
    public String getCondition_name() { return condition_name; }
    public String getSymptom_1() { return symptom_1; }
    public String getSymptom_2() { return symptom_2; }
    public String getSymptom_3() { return symptom_3; }
    public String getSymptom_4() { return symptom_4; }
    public String getSymptom_5() { return symptom_5; }
    public String getAdditional_symptoms() { return additional_symptoms; }

    // set methods
    public void setCondition_name(String condition_name) { this.condition_name = condition_name; }
    public void setSymptom_1(String symptom_1) { this.symptom_1 = symptom_1; }
    public void setSymptom_2(String symptom_2) { this.symptom_2 = symptom_2; }
    public void setSymptom_3(String symptom_3) { this.symptom_3 = symptom_3; }
    public void setSymptom_4(String symptom_4) { this.symptom_4 = symptom_4; }
    public void setSymptom_5(String symptom_5) { this.symptom_5 = symptom_5; }
    public void setAdditional_symptoms(String additional_symptoms) { this.additional_symptoms = additional_symptoms; }

}
