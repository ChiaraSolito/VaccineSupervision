package View.DoctorView;

import Control.DoctorControl.MainControllerDoc;
import Control.DoctorControl.PatientInfo;
import Model.Patient;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Control.DoctorControl.PatientListController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.*;

public class PatientsList extends Parent {
    private static User model;

    private final PatientListController controller;

    private static Stage listDocStage;

    /*
        Costruttore
     */
    public PatientsList(Stage stage, User model) {
        this.model = model;
        this.listDocStage = stage;
        controller = new PatientListController(model);
    }

    public static class HBoxCell extends HBox {
        Label label = new Label();
        Button button = new Button();

        HBoxCell(String labelText, String id, String buttonText) {
            super();

            label.setText(labelText + id);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

            button.setText(buttonText);
            button.setOnAction(e -> {
                listDocStage.setScene(new Scene(new PatientInfo(listDocStage, model).getView(),700,400));
                listDocStage.setTitle("Informazioni paziente");
                listDocStage.setResizable(false);
                listDocStage.show();
            });

            this.getChildren().addAll(label, button);
        }
    }

    public Parent getView() throws NullStringException {
        // Create the BorderPane
        List<String> patients = new ArrayList<>(controller.getAllPatients());

        BorderPane layout = new BorderPane();

        List<HBoxCell> listHBox = new ArrayList<>();
        for (String patient : patients) {
            listHBox.add(new HBoxCell("ID Paziente: ", patient, "Info"));
        }

        ListView<HBoxCell> listView = new ListView<HBoxCell>();
        ObservableList<HBoxCell> myObservableList = FXCollections.observableList(listHBox);
        listView.setItems(myObservableList);

        layout.setCenter(listView);

        return layout;
    }
}
