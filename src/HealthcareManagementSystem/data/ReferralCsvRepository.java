package HealthcareManagementSystem.data;

import HealthcareManagementSystem.model.Referral;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ReferralCsvRepository {

    private final String path;

    public ReferralCsvRepository(String path) {
        this.path = path;
    }

    public void append(Referral r) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {

            // If your CSV needs specific column order, match your referrals.csv header order here.
            String line =
                    r.getReferralId() + "," +
                            r.getUrgencyLevel() + "," +
                            safe(r.getReferralReason()) + "," +
                            safe(r.getClinicalSummary()) + "," +
                            safe(r.getRequestedInvestigations()) + "," +
                            safe(r.getStatus()) + "," +
                            safe(r.getNotes());

            bw.newLine();
            bw.write(line);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String safe(String s) {
        return s == null ? "" : s.replace(",", " "); // keep it simple for coursework
    }
}
