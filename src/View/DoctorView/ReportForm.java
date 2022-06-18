package View.DoctorView;

import Control.DoctorControl.ReactionFormController;
import Model.*;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
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
import javafx.util.converter.NumberStringConverter;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportForm {

    private User model;

    private static ReactionFormController controller;

    private Stage reportDocStage;

    private Patient patient;
    private Reaction reaction;
    private Report report;

    /*
    Costruttore
     */
    public ReportForm(Stage stage, User model) {
        this.model = model;
        this.reportDocStage = stage;
        controller = new ReactionFormController(model);
        patient = new Patient();
        reaction = new Reaction();
        report = new Report();
    }

    Parent getView() throws NullStringException {

        BorderPane layout1 = new BorderPane();

        //Header
        Text infoMenuPatient1 = new Text("Inserimento dei dati del paziente: ");
        Text infoMenuPatient2 = new Text("scegliere la modalità di inserimento");
        VBox infoMenuPatient = new VBox(10, infoMenuPatient1, infoMenuPatient2);

        //First option: insert new patient
        Button newPatientMenu = new Button("Inserisci nuovo paziente");

        //Hidden VBoxes to insert Patient informations
        TextField birthYearTextField = createBoundTextField(patient.birthYearProperty());
        VBox birthYear = new VBox(10, new Text("Anno Nascita: "), birthYearTextField);
        TextField provinceTextField = createBoundTextField(patient.provinceProperty());
        VBox province = new VBox(10, new Text("Provincia: "), provinceTextField);
        TextField professionTextField = createBoundTextField(patient.professionProperty());
        VBox profession = new VBox(10, new Text("Professione: "), professionTextField);

        //Widths of VBoxes
        birthYear.setPrefWidth(200);
        province.setPrefWidth(200);
        profession.setPrefWidth(200);

        //Adding new Risk Factors
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

        //MenuButton to choose exising Risk Factors
        //the menu is under the other New Risk because you can insert and then choose - DO NOT CHANGE POSITION
        List<RiskFactor> addedRisks = new ArrayList<>();
        MenuButton riskFactor = new MenuButton("Fattori di rischio");
        for (String risk : controller.getAllExistingRisks()) {
            CheckMenuItem sub = new CheckMenuItem(risk);
            riskFactor.getItems().add(sub);
            sub.setOnAction(a -> {
                addedRisks.add(controller.getRisk(risk));
            });
        }

        //Regulate the visibility of menus
        addRiskFactor.setVisible(false);
        addRisk.setOnAction(e -> {
            addRiskFactor.setVisible(true);
        });

        //insert new risk and clears textfields
        submit.setOnAction(e -> {
            controller.addRisk(newRisk.getName(), newRisk.getDescription(), newRisk.getRiskLevel());
            CheckMenuItem risk = new CheckMenuItem(newRisk.getName());
            riskFactor.getItems().add(risk);
            risk.setOnAction(a -> {
                addedRisks.add(controller.getRisk(newRisk.getName()));
            });
            nameField.clear();
            descriptionField.clear();
            levelField.clear();

        });

        //Get everything in a VBox
        HBox buttons = new HBox(10, riskFactor, addRisk);
        //create a box for all new patient stuff
        VBox newPatientVBOX = new VBox(10, birthYear, province, profession, buttons);
        VBox totalNewLy = new VBox(10, newPatientMenu, newPatientVBOX);

        //Second option: choose existing patient
        MenuButton choosePatient = new MenuButton("Scegli paziente esistente");
        choosePatient.setOnShowing(e -> {
            newPatientVBOX.setVisible(false);
            addRiskFactor.setVisible(false);
            birthYearTextField.clear();
            professionTextField.clear();
            professionTextField.clear();
        });

        //Hidden Menu
        ToggleGroup group = new ToggleGroup();

        for (String getPatient : controller.getAllPatients()) {
            RadioMenuItem sub = new RadioMenuItem("Paziente: " + getPatient);
            choosePatient.getItems().add(sub);
            sub.setToggleGroup(group);
            sub.setOnAction(a -> {
                patient = controller.getPatient(getPatient);
            });
        }

        //Show new Patient menù
        newPatientVBOX.setPrefWidth(200);
        newPatientVBOX.setVisible(false);
        newPatientMenu.setOnAction(e -> {
            newPatientVBOX.setVisible(true);
            group.selectToggle(null);
        });

        VBox rightVBOX = new VBox(20, choosePatient, addRiskFactor);

        Insets insets = new Insets(20);
        //On top are the informations
        layout1.setTop(infoMenuPatient);
        BorderPane.setMargin(infoMenuPatient, insets);
        //Center right and left the two buttons
        BorderPane.setAlignment(totalNewLy, Pos.CENTER_LEFT);
        layout1.setLeft(totalNewLy);
        BorderPane.setMargin(totalNewLy, insets);
        //other button
        BorderPane.setAlignment(rightVBOX, Pos.TOP_RIGHT);
        layout1.setRight(rightVBOX);
        BorderPane.setMargin(rightVBOX, insets);

        //everything goes in a scrollpane
        ScrollPane sp1 = new ScrollPane();
        sp1.setContent(layout1);

        //SECOND TAB
        BorderPane layout2 = new BorderPane();

        //Header
        Text infoMenuReaction1 = new Text("Inserimento dei dati riguardanti la reazione: ");
        Text infoMenuReaction2 = new Text("scegliere la modalità di inserimento");
        VBox infoMenuReaction = new VBox(10, infoMenuReaction1, infoMenuReaction2);

        //Second tab layout
        // First option: insert new reaction
        Button newReactiontMenu = new Button("Inserisci nuova reazione");
        //Hidden VBoxes for new Reaction
        Reaction newReaction = new Reaction();

        //Date is always visible
        DatePicker datePickerR = new DatePicker();
        datePickerR.setOnAction(e -> {
            LocalDate date = datePickerR.getValue();
            report.setReactionDate(date.toString());
        });
        VBox dateReact = new VBox(20, new Text("Data della reazione:"), datePickerR);


        //Other text fields
        TextField nameFieldReact = createBoundTextField(newReaction.nameProperty());
        VBox nameV = new VBox(10, new Text("Nome: "), nameFieldReact);
        TextField descriptionFieldReact = createBoundTextField(newReaction.descriptionProperty());
        VBox descriptionV = new VBox(10, new Text("Descrizione: "), descriptionFieldReact);
        TextField gravityFieldReact = createBoundTextFieldInt(newReaction.gravityProperty());
        VBox gravityV = new VBox(10, new Text("Livello di gravità: "), gravityFieldReact);

        //Get everything in a VBox
        VBox newReactionVBOX = new VBox(10, nameV, descriptionV, gravityV);
        newReactionVBOX.setPrefWidth(300);
        newReactionVBOX.setVisible(false);


        //Second option: choose existing patient
        MenuButton reactions = new MenuButton("Scegli reazione esistente");
        reactions.setOnShowing(e -> {
            newReactionVBOX.setVisible(false);
            nameFieldReact.clear();
            descriptionFieldReact.clear();
            gravityFieldReact.clear();
        });

        ToggleGroup group2 = new ToggleGroup();
        for (String getReaction : controller.getAllExistingReactions()) {
            RadioMenuItem sub = new RadioMenuItem("Reazione: " + getReaction);
            reactions.getItems().add(sub);
            sub.setToggleGroup(group2);
            sub.setOnAction(a -> {
                reaction = controller.getReaction(getReaction);
            });
        }

        VBox totalMenuP = new VBox(20, newReactiontMenu, newReactionVBOX);

        newReactiontMenu.setOnAction(e -> {
            newReactionVBOX.setVisible(true);
            group2.selectToggle(null);
        });

        HBox priorInfo = new HBox(20, dateReact, infoMenuReaction);
        //On top are the informations
        layout2.setTop(priorInfo);
        BorderPane.setMargin(priorInfo, insets);
        //Center right and left the two buttons
        BorderPane.setAlignment(totalMenuP, Pos.CENTER_LEFT);
        layout2.setLeft(totalMenuP);
        BorderPane.setMargin(totalMenuP, insets);
        //other button
        BorderPane.setAlignment(reactions, Pos.TOP_RIGHT);
        layout2.setRight(reactions);
        BorderPane.setMargin(reactions, insets);

        ScrollPane sp2 = new ScrollPane();
        sp2.setContent(layout2);


        //THIRD TAB
        BorderPane layout3 = new BorderPane();

        //Header
        Text infoMenuVaccination = new Text("Inserimento dei dati riguardanti le vaccinazioni: ");

        //Third tab layout
        //Hidden VBoxes for new Vaccination
        // First option: insert new reaction
        Button vaccinationsButton = new Button("Inserisci nuova vaccinazione");
        List<Vaccination> vaccinations = new ArrayList<>();

        Vaccination newVaccination = new Vaccination();
        TextField vaccineField = createBoundTextField(newVaccination.vaccineProperty());
        VBox vaccineV = new VBox(10, new Text("Nome: "), vaccineField);
        TextField typeSomministrationField = createBoundTextField(newVaccination.typeSomministrationProperty());
        VBox typeV = new VBox(10, new Text("Tipo della Somministrazione: "), typeSomministrationField);
        TextField siteField = createBoundTextField(newVaccination.vaccinationSiteProperty());
        VBox siteV = new VBox(10, new Text("Sito della vaccinazione: "), siteField);
        DatePicker datePickerV = new DatePicker();
        datePickerV.setOnAction(e -> {
            LocalDate date = datePickerV.getValue();
            newVaccination.setVaccinationDate(date.toString());
        });
        VBox dateVacc = new VBox(20, new Text("Data della vaccinazione:"), datePickerV);
        Button submitVacc = new Button("Inserisci");

        //insert new risk and clears textfields
        submitVacc.setOnAction(e -> {
            //controller.addRisk(newRisk.getName(), newRisk.getDescription(), newRisk.getRiskLevel());
            Vaccination vaccination = new Vaccination();
            vaccination.setVaccine(newVaccination.getVaccine());
            vaccination.setVaccinationDate(newVaccination.getVaccinationDate());
            vaccination.setVaccinationSite(newVaccination.getVaccinationSite());
            vaccination.setTypeSomministration(newVaccination.getTypeSomministration());
            vaccinations.add(vaccination);
            vaccineField.clear();
            typeSomministrationField.clear();
            siteField.clear();
            datePickerV.cancelEdit();
        });

        //Get everything in a VBox
        VBox vaccinationVBOX = new VBox(10, vaccineV, typeV, siteV, dateVacc, submitVacc);
        vaccinationVBOX.setPrefWidth(300);

        //On top are the informations
        layout3.setTop(infoMenuVaccination);
        BorderPane.setMargin(infoMenuVaccination, insets);
        //Center right and left the two buttons
        BorderPane.setAlignment(vaccinationVBOX, Pos.CENTER);
        layout3.setCenter(vaccinationVBOX);
        BorderPane.setMargin(vaccinationVBOX, insets);
        vaccinationVBOX.setVisible(true);

        ScrollPane sp3 = new ScrollPane();
        sp3.setContent(layout3);

        //Create the TabPane
        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("Dati Paziente", sp1);
        Tab tab2 = new Tab("Dati Reazione", sp2);
        Tab tab3 = new Tab("Dati vaccinazione", sp3);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabpane.getTabs().add(tab1);
        tabpane.getTabs().add(tab2);
        tabpane.getTabs().add(tab3);

        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            try {
                reportDocStage.setScene(new Scene(new MainPageDoc(reportDocStage, model).getView(), 700, 400));
                reportDocStage.setTitle("Menù Principale");
                reportDocStage.setResizable(false);
                reportDocStage.show();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Create button to submit
        Button submitAll = new Button("Conferma invio");


        //Controls to submit everything in the correct way
        submitAll.setOnAction(e -> {
            boolean flagPatient;
            boolean flagReaction;
            if (group.getSelectedToggle() == null) {
                flagPatient = true;
                if (birthYearTextField.getText().isEmpty() || provinceTextField.getText().isEmpty() || professionTextField.getText().isEmpty()) {
                    displayErrorMessage();
                } else {
                    patient.setBirthYear(birthYearTextField.getText());
                    patient.setProvince(provinceTextField.getText());
                    patient.setProfession(professionTextField.getText());
                    for (RiskFactor risk : addedRisks) {
                        patient.addRiskFactor(risk);
                    }
                }
            } else {
                flagPatient = false;
            }
            if (group2.getSelectedToggle() == null) {
                flagReaction = true;
                if (nameFieldReact.getText().isEmpty() || descriptionFieldReact.getText().isEmpty()
                        || gravityFieldReact.getText().isEmpty() || datePickerR.getValue().equals(null)) {
                    displayErrorMessage();
                } else {
                    //displayConfMessage();
                    if (displayConfMessage().getResult() == ButtonType.OK) {
                        reaction.setName(nameFieldReact.getText());
                        reaction.setGravity(Integer.valueOf(gravityFieldReact.getText()));
                        reaction.setDescription((descriptionFieldReact.getText()));
                        controller.createReport(patient, reaction, vaccinations, datePickerR.getValue().toString(), flagPatient, flagReaction);
                        try {
                            reportDocStage.setScene(new Scene(new MainPageDoc(reportDocStage, model).getView(), 700, 400));
                            reportDocStage.setTitle("Menù Principale");
                            reportDocStage.setResizable(false);
                            reportDocStage.show();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            } else {
                flagReaction = false;
                if (displayConfMessage().getResult() == ButtonType.OK) {
                    System.out.println(flagReaction);
                    controller.createReport(patient, reaction, vaccinations, datePickerR.getValue().toString(), flagPatient, flagReaction);
                    try {
                        reportDocStage.setScene(new Scene(new MainPageDoc(reportDocStage, model).getView(), 700, 400));
                        reportDocStage.setTitle("Menù Principale");
                        reportDocStage.setResizable(false);
                        reportDocStage.show();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        HBox buttonsFinal = new HBox(300, backButton, submitAll);
        HBox.setMargin(backButton, insets);
        HBox.setMargin(submitAll, insets);
        VBox layout = new VBox(10, tabpane, buttonsFinal);
        VBox.setMargin(buttonsFinal, insets);
        return layout;
    }

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

    private Dialog displayConfMessage() {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Sei sicuro di voler continuare?");
        dialog.setHeaderText("Dottore: " + model.getUsername());
        dialog.setContentText("Stai inserendo un report, questa azione non è reversibile.");
        dialog.showAndWait();

        return dialog;
    }

    private void displayErrorMessage() {
        Dialog dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Errore!");
        dialog.setHeaderText("Hai dimenticato qualcosa.");
        dialog.setContentText("Inserisci tutti i dati necessari prima di andare avanti");
        dialog.showAndWait();
    }
}
