package View.DoctorView;

import Control.DoctorControl.MainControllerDoc;
import Model.Patient;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import static javafx.geometry.Pos.*;

public class PatientsList extends Parent {
    private final User model;

    private final MainControllerDoc controller;

    private final Stage listDocStage;

    /*
        Costruttore
     */
    public PatientsList(Stage stage, User model) {
        this.model = model;
        this.listDocStage = stage;
        controller = new MainControllerDoc();
    }

    public Parent getView() {
        // Create the BorderPane
        BorderPane borderPane = new BorderPane();


        listDocStage.setTitle("Drug Supervision - Login");
        listDocStage.setScene(new Scene(borderPane, 700,400));
        listDocStage.setResizable(false);

        return borderPane;
    }
}
