package Control.DoctorControl;

import Model.User;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PatientInfo {
    
    private static User model;

    //private final PatientListController controller;

    private static Stage listDocStage;

    /*
        Costruttore
     */
    public PatientInfo(Stage listDocStage, User model) {
        this.model = model;
        this.listDocStage = listDocStage;
        //controller = new PatientListController(model);
    }

    public Parent getView() {
        // Create the BorderPane
        BorderPane layout = new BorderPane();

        Label label = new Label("lol");

        layout.setCenter(label);

        return layout;
    }
}
