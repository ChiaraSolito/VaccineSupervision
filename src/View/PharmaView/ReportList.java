package View.PharmaView;

import Control.FarmacologistControl.ControlPhaseController;
import Control.FarmacologistControl.ReportListController;
import Model.*;
import Model.Utils.Exceptions.NullStringException;
import View.DoctorView.PatientInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportList {

    private static User model;

    private ReportListController controller;

    private static Stage reportListStage;

    public ReportList(Stage stage, User model) {
        this.model = model;
        this.reportListStage = stage;
        this.controller = new ReportListController(model);
    }

    Parent getView() throws NullStringException {
        //ObservableList<Report> reports = FXCollections.observableArrayList(controller.getReportList());

        //Create the TableView
        TableView reportList = new TableView();
        TableColumn<TableObject, String> idColumn = new TableColumn<>("Codice report");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(100);
        TableColumn<TableObject, String> doctorColumn = new TableColumn<>("Codice dottore");
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        doctorColumn.setPrefWidth(100);
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
        vaccinationColumn.setPrefWidth(200);

        reportList.getColumns().addAll(idColumn, doctorColumn, reactionColumn, reportDateColumn, reactionDateColumn, vaccinationColumn);
/*        for (Report report : reports){
            String p = report.getPatient().getIdPatient();
            List<Vaccination> twoMonthsVaccinations = controller.getPatientTwoMonthsVaccination(p, report.getReactionDate());
            String vaccinationString = twoMonthsVaccinations.stream().map(Vaccination::toString).collect(Collectors.joining("\n"));
            SimpleStringProperty vaccinationSimpleString = new SimpleStringProperty(vaccinationString);
            TableObject obj = new TableObject(report.idProperty(), report.doctorProperty(), report.reactionProperty(), report.reportDateProperty(), report.reactionDateProperty(), vaccinationSimpleString);
            reportList.getItems().add(obj);
        }*/
        reportList.setPlaceholder(new Label("No rows to display"));


        // Create the BorderPane
        BorderPane layout = new BorderPane();
        layout.setCenter(reportList);

        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            try {
                reportListStage.setScene(new Scene(new MainPagePharm(reportListStage, model).getView(), 700, 400));
                reportListStage.setTitle("Menù principale");
                reportListStage.setResizable(false);
                reportListStage.show();
            } catch (FileNotFoundException ex){
                throw new RuntimeException(ex);
            }
        });
        layout.setBottom(backButton);
        layout.setAlignment(backButton, Pos.CENTER_LEFT);
        layout.setMargin(backButton, new Insets(5, 5, 5, 5));

        return layout;
    }

    public class TableObject{
        private SimpleStringProperty id;
        private SimpleStringProperty reaction;
        private SimpleStringProperty reportDate;
        private SimpleStringProperty reactionDate;
        private SimpleStringProperty doctor;
        private SimpleStringProperty twoMonthsVaccinations;

        public TableObject(SimpleStringProperty id, SimpleStringProperty reaction, SimpleStringProperty reportDate,
                           SimpleStringProperty reactionDate, SimpleStringProperty doctor, SimpleStringProperty twoMonthsVaccinations){
            this.id = id;
            this.reaction = reaction;
            this.reportDate = reportDate;
            this.reactionDate = reactionDate;
            this.doctor = doctor;
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
        public String getDoctor() { return doctor.get(); }
        public SimpleStringProperty doctorProperty() { return doctor; }
        public void setDoctor(String doctor) { this.doctor.set(doctor); }
        public String getTwoMonthsVaccinations() { return twoMonthsVaccinations.get(); }
        public SimpleStringProperty twoMonthsVaccinationsProperty() { return twoMonthsVaccinations; }
        public void setTwoMonthsVaccinations(String twoMonthsVaccinations) { this.twoMonthsVaccinations.set(twoMonthsVaccinations); }
    }

}
