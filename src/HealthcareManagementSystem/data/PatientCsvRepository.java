package HealthcareManagementSystem.data;

import HealthcareManagementSystem.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatientCsvRepository {

    private final String path;

    public PatientCsvRepository(String path) {
        this.path = path;
    }

    public List<Patient> loadAll() {
        List<String[]> rows = CsvReader.read(path);
        List<Patient> out = new ArrayList<>();

        for (String[] r : rows) {
            Patient p = new Patient(
                    UUID.fromString(r[0]),
                    r[1],
                    r[2],
                    r[3],
                    r[4]
            );

            p.setPatient_id(UUID.fromString(r[5]));
            p.setNhs_number(Integer.parseInt(r[6]));

            out.add(p);
        }
        return out;
    }

    public void append(Patient p) {
        CsvWriter.appendLine(path, toRow(p));
    }

    private String[] toRow(Patient p) {
        return new String[] {
                p.getId().toString(),
                p.getFirstName(),
                p.getLastName(),
                p.getDob(),
                p.getAddress(),
                p.getPatient_id().toString(),
                String.valueOf(p.getNhs_number())
        };
    }
}
