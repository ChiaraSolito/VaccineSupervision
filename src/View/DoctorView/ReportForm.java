package View.DoctorView;

import Control.DoctorControl.ReactionFormController;
import Model.*;
import View.Utils.Alerts;
import View.Utils.BoundField;
import View.Utils.VaccinesList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ReportForm {
    private final User model;

    private static ReactionFormController controller;

    private final Stage reportDocStage;

    private Patient patient;
    private Reaction reaction;
    private final Report report;

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

    Parent getView() {

        BorderPane layout1 = new BorderPane();

        //Header
        Text infoMenuPatient1 = new Text("1° passo \nInserimento dei dati del paziente: \nè possibile inserire un NUOVO PAZIENTE o scegliere un PAZIENTE GIA' ESISTENTE.");
        Text infoMenuPatient2 = new Text("Scegliere la modalità di inserimento");
        VBox infoMenuPatient = new VBox(10, infoMenuPatient1, infoMenuPatient2);

        //First option: insert new patient
        Button newPatientMenu = new Button("Inserisci nuovo paziente");

        //Hidden VBoxes to insert Patient informations
        TextField birthYearTextField = BoundField.createBoundTextField(patient.birthYearProperty());
        VBox birthYear = new VBox(10, new Text("Anno Nascita: "), birthYearTextField);
        TextField provinceTextField = BoundField.createBoundTextField(patient.provinceProperty());
        VBox province = new VBox(10, new Text("Provincia: "), provinceTextField);
        TextField professionTextField = BoundField.createBoundTextField(patient.professionProperty());
        VBox profession = new VBox(10, new Text("Professione: "), professionTextField);

        //Widths of VBoxes
        birthYear.setPrefWidth(200);
        province.setPrefWidth(200);
        profession.setPrefWidth(200);

        //Adding new Risk Factors
        Button addRisk = new Button("Nuovo Fattore");

        //Double Hidden VBoxes for new Risk Factor
        RiskFactor newRisk = new RiskFactor();
        TextField nameField = BoundField.createBoundTextField(newRisk.nameProperty());
        VBox name = new VBox(10, new Text("Nome: "), nameField);

        TextField descriptionField = BoundField.createBoundTextField(newRisk.descriptionProperty());
        VBox description = new VBox(10, new Text("Descrizione: "), descriptionField);

        TextField levelField = BoundField.createBoundTextFieldInt(newRisk.riskLevelProperty());
        VBox riskLevel = new VBox(10, new Text("Livello di rischio (da 1 a 5): "), levelField);
        Button submit = new Button("Inserisci");

        VBox addRiskFactor = new VBox(10, name, description, riskLevel, submit);

        //MenuButton to choose exising Risk Factors
        //the menu is under the other New Risk because you can insert and then choose - DO NOT CHANGE POSITION
        List<RiskFactor> addedRisks = new ArrayList<>();
        MenuButton riskFactor = new MenuButton("Fattori rischio");
        for (String risk : controller.getAllExistingRisks()) {
            CheckMenuItem sub = new CheckMenuItem(risk);
            riskFactor.getItems().add(sub);
            sub.setOnAction(a -> addedRisks.add(controller.getRisk(risk)));
        }

        //Regulate the visibility of menus
        addRiskFactor.setVisible(false);
        addRisk.setOnAction(e -> addRiskFactor.setVisible(true));

        //insert new risk and clears text fields
        submit.setOnAction(e -> {
            if (newRisk.getName().isEmpty() || newRisk.getDescription().isEmpty() || newRisk.getRiskLevel() < 1
                    || newRisk.getRiskLevel() > 5 || newRisk.getName().contains("'") || newRisk.getDescription().contains("'")) {
                Alerts.displayRiskError(model);
            } else {
                controller.addRisk(newRisk.getName(), newRisk.getDescription(), newRisk.getRiskLevel());
                CheckMenuItem risk = new CheckMenuItem(newRisk.getName());
                riskFactor.getItems().add(risk);
                risk.setOnAction(a -> {
                    System.out.println(newRisk.getName());
                    addedRisks.add(controller.getRisk(risk.getText()));
                });
            }
            nameField.clear();
            descriptionField.clear();
            levelField.clear();
        });

        //Get everything in a VBox
        HBox buttons = new HBox(15, riskFactor, addRisk);
        //create a box for all new patient stuff
        VBox newPatientVBOX = new VBox(10, birthYear, province, profession, buttons);
        VBox totalNewLy = new VBox(10, newPatientMenu, newPatientVBOX);
        totalNewLy.setPrefWidth(225);

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
                choosePatient.setText("Paziente: " + getPatient);
            });
        }

        //Show new Patient menù
        newPatientVBOX.setPrefWidth(200);
        newPatientVBOX.setVisible(false);
        newPatientMenu.setOnAction(e -> {
            newPatientVBOX.setVisible(true);
            group.selectToggle(null);
            choosePatient.setText("Scegli paziente esistente");
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

        //everything goes in a scroll pane
        ScrollPane sp1 = new ScrollPane();
        sp1.setContent(layout1);
        sp1.setStyle("-fx-font-size: 20px;");
        layout1.setStyle("-fx-font-size: 12px;");


        //SECOND TAB
        BorderPane layout2 = new BorderPane();

        //Header
        Text infoMenuReaction1 = new Text("2° passo \nInserimento dei dati riguardanti la reazione: \nè possibile inserire una NUOVA REAZIONE oppure scegliere una REAZIONE GIA' ESISTENTE. ");
        Text infoMenuReaction2 = new Text("Scegliere la modalità di inserimento");

        //Second tab layout
        // First option: insert new reaction
        Button newReactionMenu = new Button("Inserisci nuova reazione");
        //Hidden VBoxes for new Reaction
        Reaction newReaction = new Reaction();

        //Date is always visible
        DatePicker datePickerR = new DatePicker();
        datePickerR.setOnAction(e -> {
            LocalDate date = datePickerR.getValue();
            report.setReactionDate(date.toString());
        });
        HBox dateReact = new HBox(20, new Text("Data della reazione:"), datePickerR);


        //Other text fields
        TextField nameFieldReact = BoundField.createBoundTextField(newReaction.nameProperty());
        VBox nameV = new VBox(10, new Text("Nome: "), nameFieldReact);
        TextField descriptionFieldReact = BoundField.createBoundTextField(newReaction.descriptionProperty());
        VBox descriptionV = new VBox(10, new Text("Descrizione: "), descriptionFieldReact);
        TextField gravityFieldReact = BoundField.createBoundTextFieldInt(newReaction.gravityProperty());
        VBox gravityV = new VBox(10, new Text("Livello di gravità (da 1 a 5): "), gravityFieldReact);

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
                reactions.setText("Reazione: " + getReaction);
            });
        }

        VBox totalMenuP = new VBox(20, newReactionMenu, newReactionVBOX);

        newReactionMenu.setOnAction(e -> {
            newReactionVBOX.setVisible(true);
            group2.selectToggle(null);
            reactions.setText("Scegli reazione esistente: ");
        });

        VBox priorInfo = new VBox(20, infoMenuReaction1, dateReact, infoMenuReaction2);
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
        sp2.setStyle("-fx-font-size: 20px;");
        layout2.setStyle("-fx-font-size: 12px;");


        //THIRD TAB
        BorderPane layout3 = new BorderPane();

        //Header
        Text infoMenuVaccination = new Text("""
                3° passo\s
                Inserimento dei dati riguardanti le vaccinazioni, inserire in ORDINE CRONOLOGICO:\s
                è importante cliccare su 'Inserisci' quando si vuole inserire una nuova vaccinazione.
                Se le vaccinazioni non sono state inserite in ORDINE CRONOLOGICO, è necessario rifare tutta la procedura\s
                di inserimento della segnalazione.""");

        //Third tab layout
        //Hidden VBoxes for new Vaccination
        List<Vaccination> vaccinations = new ArrayList<>();
        Vaccination newVaccination = new Vaccination();

        //Implementation of somministration type -> not visible
        MenuButton doseList = new MenuButton("Scegli il tipo di somministrazione");
        ToggleGroup doseGroup = new ToggleGroup();
        doseList.setVisible(false);

        //Choose the vaccine
        MenuButton vaccineList = new MenuButton("Scegli il vaccino effettuato");
        ToggleGroup vaccineGroup = new ToggleGroup();
        //populates the toggle with covid vaccines
        for (String vaccine : VaccinesList.getCovidVaccinesString()) {
            RadioMenuItem sub = new RadioMenuItem(vaccine);
            vaccineList.getItems().add(sub);
            sub.setToggleGroup(vaccineGroup);
            sub.setOnAction(a -> {
                vaccineList.setText(vaccine);
                newVaccination.setVaccine(vaccine);
                doseList.setVisible(true);
            });
        }
        //populates the toggle with anti-influential vaccine
        for (String vaccine : VaccinesList.getInfluenceVaccines()) {
            RadioMenuItem sub = new RadioMenuItem(vaccine);
            vaccineList.getItems().add(sub);
            sub.setToggleGroup(vaccineGroup);
            sub.setOnAction(a -> {
                vaccineList.setText(vaccine);
                newVaccination.setVaccine(vaccine);
                newVaccination.setTypeSomministration("Standard");
                doseGroup.selectToggle(null);
                doseList.setVisible(false);
                doseList.setText("Scegli il tipo di somministrazione");
            });
        }
        //populate somministration type button
        for (String dose : new ArrayList<>(Arrays.asList("Prima dose", "Seconda dose", "Dose booster", "Unica"))) {
            RadioMenuItem sub = new RadioMenuItem(dose);
            doseList.getItems().add(sub);
            sub.setToggleGroup(doseGroup);
            sub.setOnAction(a -> {
                doseList.setText(dose);
                newVaccination.setTypeSomministration(dose);
            });
        }

        //Other informations text fields
        TextField siteField = BoundField.createBoundTextField(newVaccination.vaccinationSiteProperty());
        VBox siteV = new VBox(10, new Text("Sito della vaccinazione: "), siteField);
        DatePicker datePickerV = new DatePicker();
        datePickerV.setOnAction(e -> {
            LocalDate date = datePickerV.getValue();
            newVaccination.setVaccinationDate(date.toString());
        });
        VBox dateVacc = new VBox(20, new Text("Data della vaccinazione:"), datePickerV);

        //Buttons to insert the vaccine in the list of vaccinations
        Button submitVacc = new Button("Inserisci");
        List<String> doses = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();

        //insert new risk and clears text fields
        submitVacc.setOnAction(e -> {
            //Controls on vaccine doses
            Vaccination vaccination = new Vaccination();
            boolean flag = true;
            //Vaccino anti influenzale non soggetto a controlli
            if (newVaccination.getTypeSomministration() == "Standard") {
                flag = false;
                vaccination.setTypeSomministration(newVaccination.getTypeSomministration());
                doses.add(newVaccination.getTypeSomministration());
                dates.add(datePickerV.getValue());
                vaccination.setVaccine(newVaccination.getVaccine());
                vaccination.setVaccinationDate(newVaccination.getVaccinationDate());
                vaccination.setVaccinationSite(newVaccination.getVaccinationSite());
                vaccinations.add(vaccination);
            } else {
                //Vaccini covid soggetti a controlli
                if (datePickerR.getValue() == null) {
                    Alerts.displayDateError(model);
                } else if (flag && VaccinesList.covidVaccines.get(newVaccination.getVaccine()).contains(newVaccination.getTypeSomministration()
                ) && !doses.contains(newVaccination.getTypeSomministration()) && (dates.isEmpty() || datePickerV.getValue().isAfter(dates.get(dates.size() - 1)))
                        && datePickerV.getValue().isBefore(datePickerR.getValue())) {
                    vaccination.setTypeSomministration(newVaccination.getTypeSomministration());
                    doses.add(newVaccination.getTypeSomministration());
                    dates.add(datePickerV.getValue());
                    vaccination.setVaccine(newVaccination.getVaccine());
                    vaccination.setVaccinationDate(newVaccination.getVaccinationDate());
                    vaccination.setVaccinationSite(newVaccination.getVaccinationSite());
                    vaccinations.add(vaccination);
                } else {
                    Alerts.displayNotAcceptedVacc(model);
                }
            }
            vaccineGroup.selectToggle(null);
            doseGroup.selectToggle(null);
            vaccineList.setText("Scegli il vaccino effettuato");
            doseList.setText("Scegli il tipo di somministrazione");
            siteField.clear();
            datePickerV.cancelEdit();
        });

        Label warning = new Label("** Questo campo è OBBLIGATORIO per \nNUOVO PAZIENTE, altrimenti opzionale");
        warning.setTextFill(Color.color(1, 0, 0));
        HBox hbox = new HBox(10, submitVacc, warning);
        //Get everything in a VBox
        VBox vaccinationVBOX = new VBox(30, vaccineList, doseList, siteV, dateVacc, hbox);
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
        sp3.setStyle("-fx-font-size: 20px;");
        layout3.setStyle("-fx-font-size: 12px;");


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
            reportDocStage.setScene(new Scene(new MainPageDoc(reportDocStage, model).getView(), 700, 400));
            reportDocStage.setTitle("Menù Principale");
            reportDocStage.setResizable(false);
            reportDocStage.show();
        });

        //Create button to submit
        Button submitAll = new Button("Conferma invio");


        //====== CONTROLS =======
        //Controls to submit everything in the correct way
        submitAll.setOnAction(e -> {
            int counter = 0;
            boolean patientFlag = false;
            //Controls on the patient side
            if (group.getSelectedToggle() == null) { //if toggle is empty insert a new patient
                if ((birthYearTextField.getText().isEmpty() || provinceTextField.getText().isEmpty()
                        || professionTextField.getText().isEmpty() || vaccinations.isEmpty()) && counter == 0) {
                    Alerts.displayErrorMessage(model);
                    counter = 1;
                } else {
                    //controls on birth year
                    if ((parseInt(birthYearTextField.getText()) < 1900 || parseInt(birthYearTextField.getText()) > Calendar.getInstance().get(Calendar.YEAR)) && counter == 0) {
                        Alerts.displayNotAcceptedPatient(model);
                        counter = 1;
                    } else {
                        patient.setBirthYear(birthYearTextField.getText());
                        patient.setProvince(provinceTextField.getText());
                        patient.setProfession(professionTextField.getText());
                        for (RiskFactor risk : addedRisks) {
                            patient.addRiskFactor(risk);
                        }
                        patientFlag = true;
                    }
                }
            }

            //Controls on vaccine history
            if (!patientFlag) {
                for (Vaccination vaccination : vaccinations) {
                    if (controller.getVaccinationsDoses(patient.getIdPatient()).contains(vaccination.getTypeSomministration())
                            && !vaccination.getTypeSomministration().equals("Standard")) {
                        Alerts.displayErrorVaccineHistory(model);
                        counter = 15;
                    }
                }

                if (counter == 15) {
                    vaccinations.clear();
                }
            }

            //Controls on reaction side
            if (group2.getSelectedToggle() == null) { //if toggle is empty we have to create a new reaction
                if (nameFieldReact.getText().isEmpty() || descriptionFieldReact.getText().isEmpty()
                        || gravityFieldReact.getText().isEmpty() || Integer.parseInt(gravityFieldReact.getText()) < 1
                        || Integer.parseInt(gravityFieldReact.getText()) > 5 || datePickerR.getValue() == null) {
                    if (counter == 0) {
                        Alerts.displayErrorMessage(model);
                        counter = 1;
                    }
                } else {
                    reaction.setName(nameFieldReact.getText());
                    reaction.setGravity(parseInt(gravityFieldReact.getText()));
                    reaction.setDescription((descriptionFieldReact.getText()));
                    controller.createReaction(reaction);
                }
            } else {
                //controls on date coherence
                if (datePickerR.getValue() == null && counter == 0) {
                    Alerts.displayErrorMessage(model);
                    counter = 1;
                } else {
                    if (datePickerR.getValue().isAfter(LocalDate.now()) && counter == 0) {
                        Alerts.displayNotAcceptedPatient(model);
                        counter = 1;
                    }
                }
            }

            //Final message of confirmation
            if (counter == 0 && Alerts.displayConfMessage(model).getResult() == ButtonType.OK) {
                if (patientFlag) {
                    patient.setIdPatient(controller.createPatient(patient));
                }
                if (patient.getIdPatient().isEmpty()) {
                    Alerts.patientError(model);
                } else {
                    controller.addVaccination(patient, vaccinations);
                    controller.createReport(patient, reaction, datePickerR.getValue().toString());
                }
                reportDocStage.setScene(new Scene(new MainPageDoc(reportDocStage, model).getView(), 700, 400));
                reportDocStage.setTitle("Menù Principale");
                reportDocStage.setResizable(false);
                reportDocStage.show();
            }
        });


        HBox buttonsFinal = new HBox(300, backButton, submitAll);
        HBox.setMargin(backButton, insets);
        HBox.setMargin(submitAll, insets);

        Label title = new Label("Inserimento dati del report");
        title.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
        Button help = new Button("Help");
        help.setAlignment(Pos.CENTER_RIGHT);
        help.setOnAction(e -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("Come inserire un report");
            dialog.setHeaderText("Come inserire un report");
            dialog.setContentText("""
                    Guida per inserire un report:
                     - inserire un paziente: si può scegliere un paziente già esistente oppure inserire un nuovo paziente
                     - inserire la data della reazione
                     - inserire una reazione: si può scegliere reazione già esistente oppure inserirne una nuova
                     - inserire le vaccinazioni: si possono inserire vaccinazioni anti-Covid e antinfluenzali

                    Prima di confermare la segnalazione, e' necessario compilare OBBLIGATORIAMENTE i dati del paziente e i dati della reazione, mentre è FACOLTATIVA la compilazione delle vaccinazioni nel caso si sia scelto un paziente già esistente (altrimenti anche questo campo è obbligatorio).
                    I tab vanno compilati SEQUENZIALMENTE.
                    
                    Nell'inserimento di un nuovo paziente, se si vuole un nuovo fattore di rischio si deve prima inserire negli appositi campi e, dopo averlo inserito, selezionarlo nel menù a tendina a fianco.

                    Nell'inserimento delle vaccinazioni, per ogni vaccinazione che si inserisce è necessario premere sul pulsante 'Inserisci', poi si può procedere con la conferma di inserimento dell'intera segnalazione.""");
            dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            dialog.showAndWait();
        });
        HBox top = new HBox(title, help);
        HBox.setMargin(help, new Insets(5, 5, 5, 20));
        HBox.setMargin(title, new Insets(5, 300, 5, 20));

        BorderPane layout = new BorderPane();
        layout.setTop(top);
        layout.setCenter(tabpane);
        layout.setBottom(buttonsFinal);

        return layout;
    }
}
