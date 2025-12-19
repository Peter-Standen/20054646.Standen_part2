import javax.swing.SwingUtilities;

public class HmsApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HmsApplication::initializeApplication);
    }

    private static void initializeApplication() {
        System.out.println("Healthcare Management System Starting...");

        // Create the Model
        HmsModel model = new HmsModel();
        System.out.println("Model created and data loaded");

        // Create the View
        HmsView view = new HmsView();
        System.out.println("View created.");

        // Create the Controller and wire Model and View together
        HmsController controller = new HmsController(model, view);
        System.out.println("Controller created.");

        // Set controller in view
        view.setController(controller);
        System.out.println("MVC components wired together.");

        // Display the application
        view.setVisible(true);

        System.out.println("=====================================");
        System.out.println("Healthcare Management System started!");
        System.out.println("Architecture: MVC Pattern");
        System.out.println("=====================================");
    }
}
