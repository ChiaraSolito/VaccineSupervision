package View.DoctorView;

import Control.DoctorControl.PatientInfoController;
import Model.*;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
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

    public static class HBoxCell extends HBox {
        Label label = new Label();

        HBoxCell(String labelText, String name, String labelText2, int gravity, String labelText3, String description) {
            super();

            label.setText(labelText + name + labelText2 + gravity + labelText3 + description);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

        }
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
        TableView reportsInfo = new TableView<>();
        TableColumn<Report, String> idColumn = new TableColumn<>("Codice report");
        //idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Report, String> reactionColumn = new TableColumn<>("Reazione");
        //reactionColumn.setCellValueFactory(new PropertyValueFactory<>("reaction"));
        TableColumn<Report, String> reactionDateColumn = new TableColumn<>("Data reazione");
        //reactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("reactionDate"));
        TableColumn<Report, String> reportDateColumn = new TableColumn<>("Data report");
        //reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        TableColumn<Vaccination, String> vaccinationColumn = new TableColumn<>("Vaccinazioni ultimi due mesi");

/*
        TableColumn<Vaccination, String> vaccineNameColumn = new TableColumn<>("Vaccino");
        vaccineNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccine"));
        TableColumn<Vaccination, String> typeNameColumn = new TableColumn<>("Tipo somm.");
        typeNameColumn.setCellValueFactory(new PropertyValueFactory<>("typeSomministration"));
        TableColumn<Vaccination, String> siteNameColumn = new TableColumn<>("Sede");
        siteNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationSite"));
        TableColumn<Vaccination, Date> dateNameColumn = new TableColumn<>("Data");
        dateNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationDate"));
*/

//        vaccinationColumn.getColumns().addAll(vaccineNameColumn, typeNameColumn, siteNameColumn, dateNameColumn);
        reportsInfo.getColumns().addAll(idColumn, reactionColumn, reactionDateColumn, reportDateColumn, vaccinationColumn);

/*
        for (Report report : reports){
            List<Vaccination> twoMonthsVaccinations = new ArrayList<>(controller.getPatientTwoMonthsVaccination(id));
            String vaccinationString = twoMonthsVaccinations.stream().map(Vaccination::toString).collect(Collectors.joining("\n "));

            reportsInfo.getItems().add(report.getId(), report.getReaction().getName(), report.getReactionDate(), report.getReportDate(), vaccinationString);
        }
        reportsInfo.setPlaceholder(new Label("No rows to display"));
*/


        //Create the VBox
        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("Informazioni personali", personalInfo);
        Tab tab2 = new Tab("Vaccinazioni", vaccinationsInfo);
        Tab tab3 = new Tab("Segnalazioni", reportsInfo);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabpane.getTabs().add(tab1);
        tabpane.getTabs().add(tab2);
        tabpane.getTabs().add(tab3);

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
