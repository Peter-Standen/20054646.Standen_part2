public class HmsController {

    private final HmsModel model;
    private final HmsView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;
    }

    // =========================
    // Common accessors
    // =========================

    public HmsModel getModel() {
        return model;
    }

    public HmsView getView() {
        return view;
    }

    // =========================
    // Common lifecycle hooks
    // =========================

    public void onClose() {
        // model.saveAll();
    }

    // =========================
    // Example domain actions
    // =========================

    public void loadPatients() {
        // model.loadPatients();
    }

    public void createAppointment() {
        // model.createAppointment(...);
    }
}
