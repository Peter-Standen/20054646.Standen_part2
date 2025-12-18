package HealthcareManagementSystem.model;

import java.sql.Time;
import java.util.UUID;

public class Facility {

    private final UUID facilityId;
    private String facilityName;
    private FacilityType facilityType;
    private String address;
    private String postcode;
    private Time openingHours;
    private Integer capacity;
    private String specialitiesOffered;
    private String managerName;

    public Facility() {
        this.facilityId = UUID.randomUUID();
        this.facilityName = null;
        this.facilityType = null;
        this.address = null;
        this.postcode = null;
        this.openingHours = null;
        this.capacity = null;
        this.specialitiesOffered = null;
        this.managerName = null;
    }

    // Getters
    public UUID getFacilityId() { return facilityId; }
    public String getFacilityName() { return facilityName; }
    public FacilityType getFacilityType() { return facilityType; }
    public String getAddress() { return address; }
    public String getPostcode() { return postcode; }
    public Time getOpeningHours() { return openingHours; }
    public Integer getCapacity() { return capacity; }
    public String getSpecialitiesOffered() { return specialitiesOffered; }
    public String getManagerName() { return managerName; }

    // set methods
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    public void setFacilityType(FacilityType facilityType) { this.facilityType = facilityType; }
    public void setAddress(String address) { this.address = address; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public void setOpeningHours(Time openingHours) { this.openingHours = openingHours; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setSpecialitiesOffered(String specialitiesOffered) { this.specialitiesOffered = specialitiesOffered; }
    public void setManagerName(String managerName) { this.managerName = managerName; }

}
