package View.DoctorView;

import Control.DoctorControl.PatientInfoController;
import Model.*;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public PatientInfo(Stage stage, User model, String id) {
        this.model = model;
        this.patientInfoDocStage = stage;
        controller = new PatientInfoController(model);
        this.id = id;
    }

    public class TableObject{
        private SimpleStringProperty id;
        private SimpleStringProperty reaction;
        private SimpleStringProperty reportDate;
        private SimpleStringProperty reactionDate;
        private SimpleStringProperty twoMonthsVaccinations;

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
        public String getReaction() { return reaction.get(); }
        public SimpleStringProperty reactionProperty() { return reaction; }
        public void setReaction(String reaction) { this.reaction.set(reaction); }
        public String getReportDate() { return reportDate.get(); }
        public SimpleStringProperty reportDateProperty() { return reportDate; }
        public void setReportDate(String reportDate) { this.reportDate.set(reportDate); }
        public String getReactionDate() { return reactionDate.get(); }
        public SimpleStringProperty reactionDateProperty() { return reactionDate; }
        public void setReactionDate(String reactionDate) { this.reactionDate.set(reactionDate); }
        public String getTwoMonthsVaccinations() { return twoMonthsVaccinations.get(); }
        public SimpleStringProperty twoMonthsVaccinationsProperty() { return twoMonthsVaccinations; }
        public void setTwoMonthsVaccinations(String twoMonthsVaccinations) { this.twoMonthsVaccinations.set(twoMonthsVaccinations); }
    }

    public Parent getView() throws NullStringException {
        Patient patient = controller.getPatient(id);
        List<Report> reports = new ArrayList<>(controller.getPatientReports(id));
        List<Vaccination> vaccinations = new ArrayList<>(controller.getPatientVaccinations(id));
        List<RiskFactor> riskFactorList = new ArrayList<>((controller.getPatient(id)).getAllRiskFactor());

        // Create the GridPane for personalInfo
        GridPane personalInfo = new GridPane();
        personalInfo.add(new Text("Codice paziente "), 0, 0, 1, 1);
        personalInfo.add(new Text(patient.getIdPatient()), 1, 0, 1, 1);
        personalInfo.add(new Text("Anno di nascita "), 0, 1, 1, 1);
        personalInfo.add(new Text(patient.getBirthYear()), 1, 1, 1, 1);
        personalInfo.add(new Text("Provincia "), 2, 0, 1, 1);
        personalInfo.add(new Text(patient.getProvince()), 3, 0, 1, 1);
        personalInfo.add(new Text("Professione "), 2, 1, 1, 1);
        personalInfo.add(new Text(patient.getProfession()), 3, 1, 1, 1);
        personalInfo.add(new Text("Fattori di rischio "), 0, 3, 1, 1);
        personalInfo.setPadding(new Insets(10, 10, 10, 30));

        ListView riskFactorsListView = new ListView();
       for (RiskFactor riskfactor : riskFactorList) {
            riskFactorsListView.getItems().add(riskfactor.getName());
        }
        personalInfo.add(riskFactorsListView, 0, 4, 2, 1);
        personalInfo.setHgap(10);
        personalInfo.setVgap(10);


        //Create the TableView for vaccinations
        TableView vaccinationsInfo = new TableView<>();
        TableColumn<Vaccination, String> vaccineColumn = new TableColumn<>("Vaccino");
        vaccineColumn.setCellValueFactory(new PropertyValueFactory<>("vaccine"));
        vaccineColumn.setPrefWidth(175);
        TableColumn<Vaccination, String> typeSomministrationColumn = new TableColumn<>("Tipo somministrazione");
        typeSomministrationColumn.setCellValueFactory(new PropertyValueFactory<>("typeSomministration"));
        typeSomministrationColumn.setPrefWidth(175);
        TableColumn<Vaccination, String> siteColumn = new TableColumn<>("Sede vaccinazione");
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationSite"));
        siteColumn.setPrefWidth(175);
        TableColumn<Vaccination, String> dateColumn = new TableColumn<>("Data vaccinazione");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationDate"));
        dateColumn.setPrefWidth(175);

        vaccinationsInfo.getColumns().addAll(vaccineColumn, typeSomministrationColumn, siteColumn, dateColumn);
        for (Vaccination vaccination : vaccinations) {
            vaccinationsInfo.getItems().add(vaccination);
        }
        vaccinationsInfo.setPlaceholder(new Label("No rows to display"));


        //Create the TableView for reports
        GridPane reportsInfo2 = new GridPane();
        TableView reportsInfo = new TableView();
        TableColumn<TableObject, String> idColumn = new TableColumn<>("Codice report");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(100);
        TableColumn<TableObject, String> reactionColumn = new TableColumn<>("Reazione");
        reactionColumn.setCellValueFactory(new PropertyValueFactory<>("reaction"));
        reactionColumn.setPrefWidth(100);
        TableColumn<TableObject, String> reportDateColumn = new TableColumn<>("Data report");
        reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        reportDateColumn.setPrefWidth(100);
        TableColumn<TableObject, String> reactionDateColumn = new TableColumn<>("Data reazione");
        reactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("reactionDate"));
        reactionDateColumn.setPrefWidth(100);
        TableColumn<TableObject, String> vaccinationColumn = new TableColumn<>("Vaccinazioni due mesi precedenti alla reazione");
        vaccinationColumn.setCellValueFactory(new PropertyValueFactory<>("twoMonthsVaccinations"));
        vaccinationColumn.setPrefWidth(300);

        reportsInfo.getColumns().addAll(idColumn, reactionColumn, reportDateColumn, reactionDateColumn, vaccinationColumn);
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
            try {
                patientInfoDocStage.setScene(new Scene(new PatientsList(patientInfoDocStage, model).getView(), 700, 400));
                patientInfoDocStage.setTitle("Lista dei pazienti");
                patientInfoDocStage.setResizable(false);
                patientInfoDocStage.show();
            } catch (NullStringException ex) {
                throw new RuntimeException(ex);
            }
        });

        BorderPane layout = new BorderPane();
        layout.setCenter(tabpane);
        layout.setBottom(backButton);
        layout.setAlignment(backButton, Pos.CENTER_LEFT);
        layout.setMargin(backButton, new Insets(5, 5, 5, 5));

        return layout;
    }

}
