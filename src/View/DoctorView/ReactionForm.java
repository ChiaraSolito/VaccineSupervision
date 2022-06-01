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
        MenuItem newPatientMenu = new MenuItem("Inserisci nuovo paziente");
        VBox patientVBox = menuNewPatient();
        patientVBox.setVisible(false);
        newPatientMenu.setOnAction(e -> {
            patientVBox.setVisible(true);
        });

        MenuItem choosePatient = new MenuItem("Scegli paziente esistente");
        choosePatient.setOnAction(e -> {
            //reactionDocStage = (Stage) someNode.getScene().getWindow();
        });

        menuPatient.getItems().addAll(newPatientMenu, choosePatient);
        menuPatient.getOnShowing();
        VBox totalMenuP = new VBox(infoMenuPatient, menuPatient, patientVBox);

        MenuButton menuReaction = new MenuButton("Dati Reazione");
        MenuItem newReactionMenu = new MenuItem("Inserisci nuova reazione");
        newReactionMenu.setOnAction(e -> {
            // Create the BorderPane
            BorderPane newReactionLy = new BorderPane();

            //Login Form - two HBoxes with corrected insets


            //newReactionLy.setRight(patientVBox);
            reactionDocStage = (Stage) newReactionLy.getScene().getWindow();
        });

        MenuItem chooseReaction = new MenuItem("Scegli reazione esistente");
        chooseReaction.setOnAction(e -> {
            //reactionDocStage = (Stage) someNode.getScene().getWindow();
        });


        BorderPane.setAlignment(menuReaction, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(totalMenuP, Pos.CENTER_LEFT);

        layout.setRight(menuReaction);
        layout.setLeft(totalMenuP);

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

    private void displayErrorMessage() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("Incorrect Username or Password");
        dialog.setContentText("You have entered incorrect username or Password.\nPlease try Again and check if you chose the right user type.");
        dialog.showAndWait();
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

        //Hidden Menu and Button
        MenuButton riskFactor = new MenuButton("Fattori di rischio");
        for (String risk : controller.getAllExistingRisks()) {
            riskFactor.getItems().add(new CheckMenuItem(risk));
        }
        Button addRisk = new Button("Nuovo Fattore di Rischio");
        HBox buttons = new HBox(10, riskFactor, addRisk);

        //Double Hidden VBoxes for new Risk Factor
        RiskFactor newRisk = new RiskFactor();
        TextField nameField = createBoundTextField(newRisk.nameProperty());
        VBox name = new VBox(10, new Text("Nome: "), nameField);

        TextField descriptionField = createBoundTextField(newRisk.descriptionProperty());
        VBox description = new VBox(10, new Text("Descrizione: "), descriptionField);

        TextField levelField = createBoundTextFieldInt(newRisk.riskLevelProperty());
        VBox riskLevel = new VBox(10, new Text("Livello di rischio: "), levelField);
        Button submit = new Button("Inserisci");
        submit.setOnAction(e -> {
            controller.addRisk(newRisk.getName(), newRisk.getDescription(), newRisk.getRiskLevel());
            nameField.clear();
            descriptionField.clear();
            levelField.clear();
        });

        VBox addRiskFactor = new VBox(10, name, description, riskLevel, submit);

        addRiskFactor.setVisible(false);
        addRisk.setOnAction(e -> {
            addRiskFactor.setVisible(true);
        });


        VBox patientVBox = new VBox(10, birthYear, province, profession, buttons, addRiskFactor);
        patientVBox.setPrefWidth(300);

        return patientVBox;
    }
}
