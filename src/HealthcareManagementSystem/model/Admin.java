package HealthcareManagementSystem.model;

import java.util.UUID;

public class Admin extends Staff {

    public Admin(UUID user_number,
                 String first_name,
                 String last_name,
                 String email,
                 String phone_number,
                 UUID staff_id,
                 Integer payroll_number,
                 String department,
                 String line_manager,
                 String access_level) {

        super(user_number, first_name, last_name, email, phone_number,
                staff_id, payroll_number, department, line_manager, access_level);
    }
}
