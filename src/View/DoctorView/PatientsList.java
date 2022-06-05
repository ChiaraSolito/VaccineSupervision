package View.DoctorView;

import Control.DoctorControl.PatientInfoController;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Control.DoctorControl.PatientListController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
                try {
                    listDocStage.setScene(new Scene(new PatientInfo(listDocStage, model, id).getView(),700,400));
                    listDocStage.setTitle("Informazioni paziente");
                    listDocStage.setResizable(false);
                    listDocStage.show();
                } catch (NullStringException ex) {
                    throw new RuntimeException(ex);
                }
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

        IntegerProperty intProperty = new SimpleIntegerProperty();
        SimpleListProperty<HBoxCell> myObservableList = new SimpleListProperty<>(FXCollections.observableArrayList(listHBox));

        intProperty.bind(myObservableList.sizeProperty());
        ListView<HBoxCell> listView = new ListView<HBoxCell>(myObservableList);

        if(myObservableList.size() == 0){
            Text text = new Text("Nessun paziente ancora inserito");
            layout.setTop(text);
        } else {
            layout.setCenter(listView);
        }

        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            try {
                listDocStage.setScene(new Scene(new MainPageDoc(listDocStage, model).getView(), 700, 400));
                listDocStage.setTitle("Menù principale");
                listDocStage.setResizable(false);
                listDocStage.show();
            } catch (FileNotFoundException ex){
                throw new RuntimeException(ex);
            }
        });
        layout.setBottom(backButton);
        layout.setAlignment(backButton, Pos.CENTER_LEFT);
        layout.setMargin(backButton, new Insets(5, 5, 5, 5));


        return layout;
    }
}
