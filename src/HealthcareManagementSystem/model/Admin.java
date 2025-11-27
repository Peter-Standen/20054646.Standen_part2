package HealthcareManagementSystem.model;

public class Admin extends Staff{

    private String address;
    private String postcode;

    public Admin(String address, String postcode) {
        this.address = address;
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPostcode() {
        return postcode;
    }
}
