import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReferralManager {

    private static ReferralManager instance;

    private final ArrayList<Referral> referrals;
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

    // I have aligned this with the placeOrder from the bookshop
    public void addReferral(Referral r) {
        if (r == null) return;
        referrals.add(r);
        pending.add(r);
    }

    // I have aligned this with the getAllOrders() method from the bookshop
    public ArrayList<Referral> getAllReferrals() {
        return referrals;
    }

    // I have aligned this with the completeOrder(orderId) method from the bookshop
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
