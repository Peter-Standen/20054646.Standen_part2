public class ReferralsManager {
    private static ReferralsManager instance;

    private final ReferralCsvRepository referralRepo;
    private final Queue<Referral> queue = new LinkedList<>();

    private ReferralsManager(ReferralCsvRepository referralRepo) {
        this.referralRepo = referralRepo;
    }

    public static ReferralsManager getInstance(ReferralCsvRepository referralRepo) {
        if (instance == null) instance = new ReferralsManager(referralRepo);
        return instance;
    }

    public Referral createReferral(Referral referral) {
        queue.add(referral);
        referralRepo.append(referral);

        writeEmailIfNotExists(referral);
        writeEhrUpdateIfNotExists(referral);
        appendAudit(referral, "CREATED");

        return referral;
    }
}
