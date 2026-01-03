import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Singleton class to manage referrals (similar to OrderManager in the Bookshop example)
 */
public class ReferralManager {

    private static ReferralManager instance;

    private ArrayList<Referral> referrals;
    private Queue<Referral> pendingReferrals;

    private ReferralManager() {
        referrals = new ArrayList<Referral>();
        pendingReferrals = new LinkedList<Referral>();
    }

    public static ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    // Referral Methods

    public void addReferral(Referral referral) {
        if (referral == null) return;
        referrals.add(referral);
        pendingReferrals.add(referral);
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<Referral>(referrals);
    }

    public ArrayList<Referral> getPendingReferrals() {
        return new ArrayList<Referral>(pendingReferrals);
    }

    public void completeReferral(String referralId) {
        if (referralId == null) return;

        Referral found = null;
        for (int i = 0; i < referrals.size(); i++) {
            if (referrals.get(i).getReferralId().equals(referralId)) {
                found = referrals.get(i);
                break;
            }
        }

        if (found != null) {
            pendingReferrals.remove(found);
        }
    }

    public void clearAllReferrals() {
        referrals.clear();
        pendingReferrals.clear();
    }
}
