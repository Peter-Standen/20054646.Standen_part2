public class HmsController {

    private final HmsModel model;
    private final HmsView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;

        wireLoadPatients();
    }

    private void wireLoadPatients() {
        view.getLoadPatientsBtn().addActionListener(e -> loadPatients());
    }

    // Common accessors
    public HmsModel getModel() {
        return model;
    }

    public HmsView getView() {
        return view;
    }

    // Lifecycle hook
    public void onClose() {
        // later: model.saveAll();
    }

    // Domain action: Load Patients vertical slice
    public void loadPatients() {
        view.showPatients(model.getAllPatients());
    }

    // Placeholder for next slice
    public void createAppointment() {
        // later: model.createAppointment(...);
    }
}
