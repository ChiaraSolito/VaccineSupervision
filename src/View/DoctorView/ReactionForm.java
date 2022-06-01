package View.DoctorView;

import Control.DoctorControl.ReactionFormController;
import Model.Patient;
import Model.RiskFactor;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


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

        BorderPane layout = new BorderPane();

        //Header
        Text infoMenuPatient1 = new Text("Inserimento dei dati del paziente: ");
        Text infoMenuPatient2 = new Text("scegliere la modalità di inserimento");
        VBox infoMenuPatient = new VBox(infoMenuPatient1, infoMenuPatient2);

        //Left menu: patient info
        MenuButton menuPatient = new MenuButton("Dati Paziente");

        //First option: insert new patient
        MenuItem newPatientMenu = new MenuItem("Inserisci nuovo paziente");
        VBox patientVBox = menuNewPatient(); //metodo sotto
        patientVBox.setVisible(false);
        VBox choosePVBox = new VBox(10);


        //Second option: choose existing patient
        MenuItem choosePatient = new MenuItem("Scegli paziente esistente");
        //Hidden Menu and Button
        MenuButton patients = new MenuButton("Seleziona");
        for (String patient : controller.getAllPatients()) {
            patients.getItems().add(new CheckMenuItem("Paziente: " + patient));
        }
        choosePVBox.getChildren().add(patients);


        //show new part and hide the other one first option
        newPatientMenu.setOnAction(e -> {
            choosePVBox.setVisible(false);
            patientVBox.setVisible(true);
        });

        //show new part and hide the other one second option
        choosePVBox.setVisible(false);
        choosePatient.setOnAction(e -> {
            patientVBox.setVisible(false);
            choosePVBox.setVisible(true);
        });

        menuPatient.getItems().addAll(newPatientMenu, choosePatient);
        menuPatient.getOnShowing();
        VBox totalMenuP = new VBox(20, infoMenuPatient, menuPatient, choosePVBox, patientVBox);

        MenuButton menuReaction = new MenuButton("Dati Reazione");
        MenuItem newReactionMenu = new MenuItem("Inserisci nuova reazione");
        newReactionMenu.setOnAction(e -> {
            // Create the BorderPane
            BorderPane newReactionLy = new BorderPane();

            //Login Form - two HBoxes with corrected insets


            //newReactionLy.setRight(patientVBox);
        });

        MenuItem chooseReaction = new MenuItem("Scegli reazione esistente");
        chooseReaction.setOnAction(e -> {
            //reactionDocStage = (Stage) someNode.getScene().getWindow();
        });

        Button submitAll = new Button("Conferma invio");
        submitAll.setOnAction(e -> {
            if (displayConfMessage()) {

            }
        });

        BorderPane.setAlignment(submitAll, Pos.BOTTOM_RIGHT);
        BorderPane.setAlignment(menuReaction, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(totalMenuP, Pos.CENTER_LEFT);

        layout.setRight(menuReaction);
        layout.setLeft(totalMenuP);
        layout.setBottom(submitAll);

        Insets insets = new Insets(10);
        BorderPane.setMargin(menuReaction, insets);
        BorderPane.setMargin(totalMenuP, insets);

        ScrollPane sp = new ScrollPane();
        sp.setContent(layout);
        return sp;
    }


    /*
        Metodo per il binding dei Text Field
     */
    private TextField createBoundTextField(StringProperty boundProperty) {
        TextField results = new TextField("");
        results.textProperty().bindBidirectional(boundProperty);
        return results;
    }

    private TextField createBoundTextFieldInt(IntegerProperty boundProperty) {
        TextField results = new TextField("");
        results.textProperty().bindBidirectional(boundProperty, new NumberStringConverter());
        return results;
    }

    private boolean displayConfMessage() {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Sei sicuro di voler continuare?");
        dialog.setHeaderText("Dottore: " + model.getUsername());
        dialog.setContentText("Stai inserendo un report, questa azione non è reversibile.");
        dialog.showAndWait();
        if (dialog.getResult() == ButtonType.YES) {
            return true;
        } else {
            return false;
        }
    }

    private VBox menuNewPatient() {
        //Hidden VBoxes for new Patient
        VBox birthYear = new VBox(10, new Text("Anno Nascita: "), createBoundTextField(patient.birthYearProperty()));
        VBox province = new VBox(10, new Text("Provincia: "), createBoundTextField(patient.provinceProperty()));
        VBox profession = new VBox(10, new Text("Professione: "), createBoundTextField(patient.professionProperty()));

        //Widths
        birthYear.setPrefWidth(300);
        province.setPrefWidth(300);
        profession.setPrefWidth(300);

        Button addRisk = new Button("Nuovo Fattore di Rischio");

        //Double Hidden VBoxes for new Risk Factor
        RiskFactor newRisk = new RiskFactor();
        TextField nameField = createBoundTextField(newRisk.nameProperty());
        VBox name = new VBox(10, new Text("Nome: "), nameField);

        TextField descriptionField = createBoundTextField(newRisk.descriptionProperty());
        VBox description = new VBox(10, new Text("Descrizione: "), descriptionField);

        TextField levelField = createBoundTextFieldInt(newRisk.riskLevelProperty());
        VBox riskLevel = new VBox(10, new Text("Livello di rischio: "), levelField);
        Button submit = new Button("Inserisci");

        VBox addRiskFactor = new VBox(10, name, description, riskLevel, submit);

        //Hidden Menu and Button
        MenuButton riskFactor = new MenuButton("Fattori di rischio");
        for (String risk : controller.getAllExistingRisks()) {
            riskFactor.getItems().add(new CheckMenuItem(risk));
        }

        addRiskFactor.setVisible(false);
        addRisk.setOnAction(e -> {
            addRiskFactor.setVisible(true);
        });

        submit.setOnAction(e -> {
            controller.addRisk(newRisk.getName(), newRisk.getDescription(), newRisk.getRiskLevel());
            riskFactor.getItems().add(new CheckMenuItem(newRisk.getName()));
            nameField.clear();
            descriptionField.clear();
            levelField.clear();

        });


        HBox buttons = new HBox(10, riskFactor, addRisk);
        VBox patientVBox = new VBox(10, birthYear, province, profession, buttons, addRiskFactor);
        patientVBox.setPrefWidth(300);

        return patientVBox;
    }
}
