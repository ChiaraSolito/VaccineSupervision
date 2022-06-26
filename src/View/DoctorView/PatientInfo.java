package View.DoctorView;

import Control.DoctorControl.PatientInfoController;
import Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PatientInfo {
    private static User model;

    private final PatientInfoController controller;

    private static Stage patientInfoDocStage;

    private static String id;

    /*
        Costruttore
     */
    public PatientInfo(Stage stage, User modelPI, String idPI) {
        model = modelPI;
        patientInfoDocStage = stage;
        controller = new PatientInfoController(modelPI);
        id = idPI;
    }

    public Parent getView() {
        Patient patient = controller.getPatient(id);
        List<Report> reports = new ArrayList<>(controller.getPatientReports(id));
        List<Vaccination> vaccinations = new ArrayList<>(controller.getPatientVaccinations(id));
        List<RiskFactor> riskFactorList = new ArrayList<>(patient.getAllRiskFactor());

        // Create the GridPane for personalInfo
        int numberVaccinations = controller.getNumberVaccination(patient.getIdPatient());
        int numberReports = controller.getNumberReport(patient.getIdPatient());
        GridPane personalInfo = new GridPane();
        personalInfo.add(new Text("Codice paziente "), 0, 0, 1, 1);
        personalInfo.add(new Text(patient.getIdPatient()), 1, 0, 1, 1);
        personalInfo.add(new Text("Anno di nascita "), 0, 1, 1, 1);
        personalInfo.add(new Text(patient.getBirthYear()), 1, 1, 1, 1);
        personalInfo.add(new Text("Provincia "), 2, 0, 1, 1);
        personalInfo.add(new Text(patient.getProvince()), 3, 0, 1, 1);
        personalInfo.add(new Text("Professione "), 2, 1, 1, 1);
        personalInfo.add(new Text(patient.getProfession()), 3, 1, 1, 1);
        personalInfo.add(new Text("Numero vaccinazioni "), 0, 2, 1, 1);
        personalInfo.add(new Text(Integer.toString(numberVaccinations)), 1, 2, 1, 1);
        personalInfo.add(new Text("Numero segnalazioni "), 2, 2, 1, 1);
        personalInfo.add(new Text(Integer.toString(numberReports)), 3, 2, 1, 1);
        personalInfo.add(new Text("Fattori di rischio "), 0, 3, 1, 1);
        personalInfo.setPadding(new Insets(10, 10, 10, 30));

        ListView<String> riskFactorsListView = new ListView<>();
        for (RiskFactor riskfactor : riskFactorList) {
            riskFactorsListView.getItems().add(riskfactor.getName());
        }
        personalInfo.add(riskFactorsListView, 0, 4, 2, 1);
        personalInfo.setHgap(10);
        personalInfo.setVgap(10);


        //Create the TableView for vaccinations
        TableView<Vaccination> vaccinationsInfo = new TableView<>();
        TableColumn<Vaccination, String> vaccineColumn = new TableColumn<>("Vaccino");
        vaccineColumn.setCellValueFactory(p -> p.getValue().vaccineProperty());
        vaccineColumn.setMaxWidth(175);
        vaccineColumn.setMinWidth(175);
        vaccineColumn.setReorderable(false);
        vaccinationsInfo.getColumns().add(vaccineColumn);
        TableColumn<Vaccination, String> typeSomministrationColumn = new TableColumn<>("Tipo somministrazione");
        typeSomministrationColumn.setCellValueFactory(p -> p.getValue().typeSomministrationProperty());
        typeSomministrationColumn.setPrefWidth(175);
        typeSomministrationColumn.setReorderable(false);
        vaccinationsInfo.getColumns().add(typeSomministrationColumn);
        TableColumn<Vaccination, String> siteColumn = new TableColumn<>("Sede vaccinazione");
        siteColumn.setCellValueFactory(p -> p.getValue().vaccinationSiteProperty());
        siteColumn.setPrefWidth(175);
        siteColumn.setReorderable(false);
        vaccinationsInfo.getColumns().add(siteColumn);
        TableColumn<Vaccination, String> dateColumn = new TableColumn<>("Data vaccinazione");
        dateColumn.setCellValueFactory(p -> p.getValue().vaccinationDateProperty());
        dateColumn.setPrefWidth(174);
        dateColumn.setReorderable(false);
        vaccinationsInfo.getColumns().add(dateColumn);

        for (Vaccination vaccination : vaccinations) {
            vaccinationsInfo.getItems().add(vaccination);
        }
        vaccinationsInfo.setPlaceholder(new Label("No rows to display"));


        //Create the TableView for reports
        TableView<TableObject> reportsInfo = new TableView<>();
        TableColumn<TableObject, String> idColumn = new TableColumn<>("Codice report");
        idColumn.setCellValueFactory(p -> p.getValue().idProperty());
        idColumn.setPrefWidth(100);
        idColumn.setReorderable(false);
        reportsInfo.getColumns().add(idColumn);
        TableColumn<TableObject, String> reactionColumn = new TableColumn<>("Reazione");
        reactionColumn.setCellValueFactory(p -> p.getValue().reactionProperty());
        reactionColumn.setPrefWidth(100);
        reactionColumn.setReorderable(false);
        reportsInfo.getColumns().add(reactionColumn);
        TableColumn<TableObject, String> reportDateColumn = new TableColumn<>("Data report");
        reportDateColumn.setCellValueFactory(p -> p.getValue().reportDateProperty());
        reportDateColumn.setPrefWidth(100);
        reportDateColumn.setReorderable(false);
        reportsInfo.getColumns().add(reportDateColumn);
        TableColumn<TableObject, String> reactionDateColumn = new TableColumn<>("Data reazione");
        reactionDateColumn.setCellValueFactory(p -> p.getValue().reactionDateProperty());
        reactionDateColumn.setPrefWidth(100);
        reactionDateColumn.setReorderable(false);
        reportsInfo.getColumns().add(reactionDateColumn);
        TableColumn<TableObject, String> vaccinationColumn = new TableColumn<>("Vaccinazioni due mesi precedenti alla reazione");
        vaccinationColumn.setCellValueFactory(p -> p.getValue().twoMonthsVaccinationsProperty());
        vaccinationColumn.setPrefWidth(300);
        vaccinationColumn.setReorderable(false);
        reportsInfo.getColumns().add(vaccinationColumn);

        for (Report report : reports){
            List<Vaccination> twoMonthsVaccinations = controller.getPatientTwoMonthsVaccination(id, report.getReactionDate());
            String vaccinationString = twoMonthsVaccinations.stream().map(Vaccination::toString).collect(Collectors.joining("\n"));
            SimpleStringProperty vaccinationSimpleString = new SimpleStringProperty(vaccinationString);
            TableObject obj = new TableObject(report.idProperty(), report.reactionProperty(), report.reportDateProperty(), report.reactionDateProperty(), vaccinationSimpleString);
            reportsInfo.getItems().add(obj);
        }
        reportsInfo.setPlaceholder(new Label("No rows to display"));


        //Create the TabPane
        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("Informazioni personali", personalInfo);
        Tab tab2 = new Tab("Vaccinazioni", vaccinationsInfo);
        Tab tab3 = new Tab("Segnalazioni", reportsInfo);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabpane.getTabs().addAll(tab1, tab2, tab3);

        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            patientInfoDocStage.setScene(new Scene(new PatientsList(patientInfoDocStage, model).getView(), 700, 400));
            patientInfoDocStage.setTitle("Lista dei pazienti");
            patientInfoDocStage.setResizable(false);
            patientInfoDocStage.show();
        });

        BorderPane layout = new BorderPane();
        layout.setCenter(tabpane);
        layout.setBottom(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
        BorderPane.setMargin(backButton, new Insets(5, 5, 5, 5));

        return layout;
    }

    public static class TableObject{
        private final SimpleStringProperty id;
        private final SimpleStringProperty reaction;
        private final SimpleStringProperty reportDate;
        private final SimpleStringProperty reactionDate;
        private final SimpleStringProperty twoMonthsVaccinations;

        public TableObject(SimpleStringProperty id, SimpleStringProperty reaction, SimpleStringProperty reportDate,
                           SimpleStringProperty reactionDate, SimpleStringProperty twoMonthsVaccinations){
            this.id = id;
            this.reaction = reaction;
            this.reportDate = reportDate;
            this.reactionDate = reactionDate;
            this.twoMonthsVaccinations = twoMonthsVaccinations;
        }

        public String getId() { return id.get(); }
        public SimpleStringProperty idProperty() { return id; }
        public void setId(String id) { this.id.set(id); }
        public SimpleStringProperty reactionProperty() { return reaction; }
        public SimpleStringProperty reportDateProperty() { return reportDate; }
        public SimpleStringProperty reactionDateProperty() { return reactionDate; }
        public SimpleStringProperty twoMonthsVaccinationsProperty() { return twoMonthsVaccinations; }
    }
}
