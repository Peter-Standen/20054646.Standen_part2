import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReferralManager {

    private static ReferralManager instance;

    private ArrayList<Referral> referrals;
    private Queue<Referral> pending;

    private ReferralManager() {
        referrals = new ArrayList<Referral>();
        pending = new LinkedList<Referral>();
    }

    public static ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    // bookshop equivalent: placeOrder(...)
    public void addReferral(Referral r) {
        if (r == null) return;
        referrals.add(r);
        pending.add(r);
    }

    // bookshop equivalent: getAllOrders()
    public ArrayList<Referral> getAllReferrals() {
        return referrals;
    }

    // bookshop equivalent: completeOrder(orderId)
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
            pending.remove(found); // simplest possible “complete”
        }
    }
}
