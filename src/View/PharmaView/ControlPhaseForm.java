package View.PharmaView;

import Control.FarmacologistControl.ControlPhaseController;
import Model.ControlPhase;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class ControlPhaseForm {
    private User model;

    private static ControlPhaseController controller;

    private Stage controlPhaseStage;
    private ControlPhase controlPhase;

    /*
    Costruttore
     */
    public ControlPhaseForm(Stage stage, User model) {
        this.model = model;
        this.controlPhaseStage = stage;
        this.controller = new ControlPhaseController(model);
        this.controlPhase = new ControlPhase();
    }

    Parent getView() throws NullStringException {

        BorderPane layout = new BorderPane();

        //set initial variables for control phase
        controlPhase.setPharmacologist(model.getUsername());

        //Second option: choose existing patient
        MenuButton chooseVaccine = new MenuButton("Scegli vaccino da porre sotto controllo");

        //Hidden Menu
        ToggleGroup group = new ToggleGroup();

        for (String vaccine : controller.getAllVaccines()) {
            RadioMenuItem sub = new RadioMenuItem("Vaccino: " + vaccine);
            chooseVaccine.getItems().add(sub);
            sub.setToggleGroup(group);
            sub.setOnAction(a -> {
                controlPhase.setVaccine(vaccine);
            });
        }


        //Date is always visible
        DatePicker datePickerR = new DatePicker();
        datePickerR.setOnAction(e -> {
            LocalDate date = datePickerR.getValue();
            controlPhase.setReportDate(date.toString());
        });
        VBox dateReact = new VBox(20, new Text("Data della reazione:"), datePickerR);

        VBox content = new VBox(10, chooseVaccine, dateReact);
        Text header = new Text("Scegliere il vaccino da proporre per la fase di controllo");
        Button submit = new Button("Conferma");
        submit.setOnAction(e -> {
            if (displayConfMessage().getResult() == ButtonType.OK) {
                controller.addControlPhase(controlPhase);
            }
            try {
                controlPhaseStage.setScene(new Scene(new MainPagePharm(controlPhaseStage, model).getView(), 700, 400));
                controlPhaseStage.setResizable(false);
                controlPhaseStage.setTitle("Menù Principale");
                controlPhaseStage.show();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Layout settings
        Insets insets = new Insets(20);
        //On top are the informations
        BorderPane.setAlignment(header, Pos.TOP_CENTER);
        layout.setTop(header);
        BorderPane.setMargin(header, insets);
        //Center for the vaccine
        BorderPane.setAlignment(content, Pos.CENTER);
        layout.setCenter(content);
        BorderPane.setMargin(content, insets);
        //Down on the right is the button
        BorderPane.setAlignment(submit, Pos.BOTTOM_RIGHT);
        layout.setBottom(submit);
        BorderPane.setMargin(submit, insets);

        return layout;
    }

    private Dialog displayConfMessage() {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Sei sicuro di voler continuare?");
        dialog.setHeaderText("Farmacologo: " + model.getUsername());
        dialog.setContentText("Stai proponendo una fase di controllo, questa azione non è reversibile.");
        dialog.showAndWait();

        return dialog;
    }
}
