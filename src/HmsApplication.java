import javax.swing.SwingUtilities;

/**
 * Entry point for the Healthcare Management System.
 * Mirrors the structure used in the Bookshop example (MVC wiring + console banners).
 */
public class HmsApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializeApplication();
            }
        });
    }

    private static void initializeApplication() {
        System.out.println("Healthcare Management System Starting...");

        // Create the Model
        HmsModel model = new HmsModel();
        System.out.println("Model created and data loaded.");

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
