package HealthcareManagementSystem.data;

import HealthcareManagementSystem.model.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class PatientCsvRepository {

    private final String path;

    public PatientCsvRepository(String path) {
        this.path = path;
    }

    public List<Patient> loadAll() {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                String[] r = line.split(",");

                Patient p = new Patient(
                        UUID.fromString(r[0]),
                        r[1],   // firstName
                        r[2],   // lastName
                        r[3],   // email
                        r[4]    // phoneNumber
                );

                p.setPatientId(UUID.fromString(r[5]));
                p.setNhsNumber(Integer.parseInt(r[6]));
                p.setDateOfBirth(new Date()); // or parse if required

                patients.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }
}
