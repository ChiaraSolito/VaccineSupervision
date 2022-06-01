package View.DoctorView;

import Control.DoctorControl.ReactionFormController;
import Model.Patient;
import Model.RiskFactor;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ReactionForm {
    private static User model;

    private final ReactionFormController controller;

    private static Stage reactionDocStage;

    private static Patient patient;

    /*
        Costruttore
     */
    public ReactionForm(Stage stage, User model) {
        this.model = model;
        this.reactionDocStage = stage;
        controller = new ReactionFormController(model);
        patient = new Patient();
    }

    public Parent getView() throws NullStringException {
        // Create the BorderPane
        BorderPane layout = new BorderPane();

        //Login Form - two HBoxes with corrected insets
        HBox birthYear = new HBox(10, new Text("Anno Nascita: "), createBoundTextField(patient.birthYearProperty()));
        HBox province = new HBox(10, new Text("Provincia: "), createBoundTextField(patient.provinceProperty()));
        HBox profession = new HBox(10, new Text("Professione: "), createBoundTextField(patient.professionProperty()));
        //HBox riskFactor = new HBox(10, new Text("Fattore di Rischio: "), createBoundTextField());
        VBox patientVBox = new VBox(birthYear, province, profession);
        patientVBox.setPrefWidth(300);

        layout.setCenter(patientVBox);
        return layout;
    }


    /*
        Metodo per il binding dei Text Field
     */
    private Node createBoundTextField(StringProperty boundProperty) {
        TextField results = new TextField("");
        results.textProperty().bindBidirectional(boundProperty);
        return results;
    }

    private void displayErrorMessage() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("Incorrect Username or Password");
        dialog.setContentText("You have entered incorrect username or Password.\nPlease try Again and check if you chose the right user type.");
        dialog.showAndWait();
    }
}
