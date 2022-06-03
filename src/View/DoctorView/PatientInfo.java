package View.DoctorView;

import Control.DoctorControl.PatientInfoController;
import Model.*;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
        Button button = new Button();

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
        List<Vaccination> twoMonthsVaccinations = new ArrayList<>(controller.getPatientTwoMonthsVaccination(id));
        List<RiskFactor> riskFactorList = new ArrayList<>((controller.getPatient(id)).getAllRiskFactor());

        // Create the GridPane for personalInfo
        GridPane personalInfo = new GridPane();
        personalInfo.add(new Text("Codice paziente: "), 0, 0, 1, 1);
        personalInfo.add(new Text(patient.getIdPatient()), 1, 0, 1, 1);
        personalInfo.add(new Text("Anno di nascita: "), 0, 1, 1, 1);
        personalInfo.add(new Text(patient.getBirthYear()), 1, 1, 1, 1);
        personalInfo.add(new Text("Provincia: "), 2, 0, 1, 1);
        personalInfo.add(new Text(patient.getProvince()), 3, 0, 1, 1);
        personalInfo.add(new Text("Professione: "), 2, 1, 1, 1);
        personalInfo.add(new Text(patient.getProfession()), 3, 1, 1, 1);
        personalInfo.add(new Text("Fattori di rischio: "), 0, 2, 1, 1);
        personalInfo.setPadding(new Insets(10, 10, 10, 30));

        List<HBoxCell> listHBoxRF = new ArrayList<>();
        for (RiskFactor riskfactor : riskFactorList) {
            listHBoxRF.add(new HBoxCell("Nome: ", riskfactor.getName(), "Gravità: ", riskfactor.getRiskLevel(), "Descrizione: ", riskfactor.getDescription()));
        }
        IntegerProperty intProperty = new SimpleIntegerProperty();
        SimpleListProperty<HBoxCell> myObservableList = new SimpleListProperty<>(FXCollections.observableArrayList(listHBoxRF));

        intProperty.bind(myObservableList.sizeProperty());
        ListView<HBoxCell> riskFactorListView = new ListView<HBoxCell>(myObservableList);
        if(myObservableList.size() == 0){
            Text text = new Text("Nessun fattore di rischio ancora inserito");
        }
        personalInfo.add(riskFactorListView, 1, 4, 1, 1);
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

        vaccinationsInfo.getColumns().add(vaccineColumn);
        vaccinationsInfo.getColumns().add(typeSomministrationColumn);
        vaccinationsInfo.getColumns().add(siteColumn);
        vaccinationsInfo.getColumns().add(dateColumn);

        for (Vaccination vaccination : vaccinations){
            vaccinationsInfo.getItems().add(vaccination);
        }
        vaccinationsInfo.setPlaceholder(new Label("No rows to display"));


        //Create the TableView for reports
        TableView reportsInfo = new TableView<>();
        TableColumn<Report, String> idColumn = new TableColumn<>("Codice report");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Report, String> reactionColumn = new TableColumn<>("Reazione");
        reactionColumn.setCellValueFactory(new PropertyValueFactory<>("reaction"));
        TableColumn<Report, String> reactionDateColumn = new TableColumn<>("Data reazione");
        reactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("reactionDate"));
        TableColumn<Report, String> reportDateColumn = new TableColumn<>("Data report");
        reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        //TableColumn<Report, String> vaccinationColumn = new TableColumn<>("Vaccinazione");
        //vaccinationColumn.setCellValueFactory(new PropertyValueFactory<>("vaccination"));

        reportsInfo.getColumns().add(idColumn);
        reportsInfo.getColumns().add(reactionColumn);
        reportsInfo.getColumns().add(reactionDateColumn);
        reportsInfo.getColumns().add(reportDateColumn);
        //reportsInfo.getColumns().add(vaccinationColumn);

        for (Report report : reports){
            reportsInfo.getItems().add(report);
        }
        reportsInfo.setPlaceholder(new Label("No rows to display"));


        //Create the TabPane
        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("Informazioni personali", personalInfo);
        Tab tab2 = new Tab("Vaccinazioni"  , vaccinationsInfo);
        Tab tab3 = new Tab("Segnalazioni" , reportsInfo);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabpane.getTabs().add(tab1);
        tabpane.getTabs().add(tab2);
        tabpane.getTabs().add(tab3);

        VBox layout = new VBox(tabpane);

        return layout;
    }

}
