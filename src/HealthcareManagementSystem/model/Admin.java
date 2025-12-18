package HealthcareManagementSystem.model;

import java.util.UUID;

public class Admin extends Staff {

    public Admin(UUID userNumber,
                 String firstName,
                 String lastName,
                 String email,
                 String phoneNumber,
                 UUID staffId,
                 Integer payrollNumber,
                 String department,
                 String lineManager,
                 String accessLevel) {

        super(userNumber, firstName, lastName, email, phoneNumber, staffId,
                payrollNumber, department, lineManager, accessLevel);
    }
}
