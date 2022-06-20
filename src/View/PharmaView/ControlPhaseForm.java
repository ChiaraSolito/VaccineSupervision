package View.PharmaView;

import Control.FarmacologistControl.ControlPhaseController;
import Model.ControlPhase;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import View.Utils.Alerts;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class ControlPhaseForm {
    private final User model;

    private static ControlPhaseController controller;

    private final Stage controlPhaseStage;
    private final ControlPhase controlPhase;

    /*
    Costruttore
     */
    public ControlPhaseForm(Stage stage, User model) {
        this.model = model;
        this.controlPhaseStage = stage;
        controller = new ControlPhaseController();
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
            sub.setOnAction(a -> controlPhase.setVaccine(vaccine));
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
            if (group.getSelectedToggle() == null || datePickerR.getValue() == null) {
                Alerts.displayErrorMessage(model);
            } else {
                if (Alerts.displayConfMessage(model).getResult() == ButtonType.OK) {
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
            }
        });

        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            try {
                controlPhaseStage.setScene(new Scene(new MainPagePharm(controlPhaseStage, model).getView(), 700, 400));
                controlPhaseStage.setTitle("Menù Principale");
                controlPhaseStage.setResizable(false);
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

        HBox buttons = new HBox(500, backButton, submit);
        BorderPane.setAlignment(buttons, Pos.BOTTOM_CENTER);
        layout.setBottom(buttons);
        BorderPane.setMargin(buttons, insets);
        return layout;
    }
}
