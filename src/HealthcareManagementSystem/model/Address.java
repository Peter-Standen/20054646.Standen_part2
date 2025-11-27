package HealthcareManagementSystem.model;

public class Address {

    private String address;
    private String postcode;

    public Address(String address, String postcode) {
        this.address = address;
        this.postcode = postcode;
    }

    // get methods
    public String getAddress(){ return address; }
    public String getPostcode() { return postcode; }

    // set methods
    public void setAddress(String address) { this.address = address; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
}
