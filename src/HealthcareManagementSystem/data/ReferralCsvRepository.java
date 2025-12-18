package HealthcareManagementSystem.data;

import HealthcareManagementSystem.model.Referral;

public class ReferralCsvRepository {

    private final String path;

    public ReferralCsvRepository(String path) {
        this.path = path;
    }

    public void append(Referral referral) {
        CsvWriter.appendLine(path, toRow(referral));
    }

    private String[] toRow(Referral r) {
        return new String[] {
                r.getId().toString(),
                r.getPatientId().toString(),
                r.getClinicianId().toString(),
                r.getFacilityId().toString(),
                r.getUrgency().toString(),
                r.getClinicalSummary()
        };
    }
}
