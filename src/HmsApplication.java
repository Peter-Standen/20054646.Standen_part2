import HealthcareManagementSystem.controller.AdminController;
import HealthcareManagementSystem.data.AppointmentCsvRepository;
import HealthcareManagementSystem.data.ClinicianCsvRepository;
import HealthcareManagementSystem.data.PatientCsvRepository;
import HealthcareManagementSystem.data.PrescriptionCsvRepository;
import HealthcareManagementSystem.data.ReferralCsvRepository;
import HealthcareManagementSystem.service.PatientRecordService;
import HealthcareManagementSystem.service.PrescriptionService;
import HealthcareManagementSystem.service.ReferralsManager;
import HealthcareManagementSystem.view.AdminUI;
import javax.swing.SwingUtilities;

/**
 * Main Application class for the Healthcare Management System
 * Initializes and wires together the Model, View, and Controller components
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
        System.out.println("Bookshop Management System Starting...");

        // Create the Model
        HMSModel model = new HmsModel();
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
        System.out.println("Bookshop Management System started!");
        System.out.println("Architecture: MVC Pattern");
        System.out.println("Order Management: Singleton Pattern");
        System.out.println("=====================================");

        });
    }
}
