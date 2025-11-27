package HealthcareManagementSystem.model;

import java.sql.Time;
import java.util.UUID;

public class Facility {

    private final UUID facility_id;
    private String facility_name;
    private FacilityType facility_type;
    private String address;
    private String postcode;
    private Time opening_hours;
    private Integer capacity;
    private String specialities_offered;
    private String manager_name;

    public Facility() {
        this.facility_id = UUID.randomUUID();
        this.facility_name = null;
        this.facility_type = null;
        this.address = null;
        this.postcode = null;
        this.opening_hours = null;
        this.capacity = null;
        this.specialities_offered = null;
        this.manager_name = null;
    }

    // Getters
    public UUID getFacility_id() { return facility_id; }
    public String getFacility_name() { return facility_name; }
    public FacilityType getFacility_type() { return facility_type; }
    public String getAddress() { return address; }
    public String getPostcode() { return postcode; }
    public Time getOpening_hours() { return opening_hours; }
    public Integer getCapacity() { return capacity; }
    public String getSpecialities_offered() { return specialities_offered; }
    public String getManager_name() { return manager_name; }

    // set methods
    public void setFacility_name(String facility_name) { this.facility_name = facility_name; }
    public void setFacility_type(FacilityType facility_type) { this.facility_type = facility_type; }
    public void setAddress(String address) { this.address = address; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public void setOpening_hours(Time opening_hours) { this.opening_hours = opening_hours; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setSpecialities_offered(String specialities_offered) { this.specialities_offered = specialities_offered; }
    public void setManager_name(String manager_name) { this.manager_name = manager_name; }

}
