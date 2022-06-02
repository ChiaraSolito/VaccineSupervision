package View.DoctorView;

import Control.DoctorControl.PatientInfoController;
import Model.Patient;
import Model.Report;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PatientInfo {
    private static User model;

    private final PatientInfoController controller;

    private static Stage patientInfoDocStage;

    private static String id;

    /*
        Costruttore
     */
    public PatientInfo(Stage stage, User model, String id) {
        this.model = model;
        this.patientInfoDocStage = stage;
        controller = new PatientInfoController(model);
        this.id = id;
    }

    public Parent getView() throws NullStringException {
        // Create the GridPane
        List<Report> reports = new ArrayList<>(controller.getPatientReports(id));
        List<Vaccination> vaccinations = new ArrayList<>(controller.getPatientVaccinations(id));

        TabPane tabpane = new TabPane();

        Tab tab1 = new Tab("Informazioni personali", new Label("Show personal informations about patient."));
        Tab tab2 = new Tab("Vaccinazioni"  , new Label("Show all the vaccinations the patient has done."));
        Tab tab3 = new Tab("Segnalazioni" , new Label("Show all the reports about the patient."));

        tabpane.getTabs().add(tab1);
        tabpane.getTabs().add(tab2);
        tabpane.getTabs().add(tab3);

        VBox layout = new VBox(tabpane);

        return layout;
    }

}
