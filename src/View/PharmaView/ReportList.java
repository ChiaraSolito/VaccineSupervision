package View.PharmaView;

import Control.FarmacologistControl.ReportListController;
import Model.Report;
import Model.User;
import Model.Vaccination;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportList {

    private static User model;

    private final ReportListController controller;

    private static Stage reportListStage;

    public ReportList(Stage stage, User modelG) {
        model = modelG;
        reportListStage = stage;
        controller = new ReportListController();
    }

    Parent getView() {
        List<Report> reports = new ArrayList<>(controller.getReportList());

        //Create the TableView
        TableView<TableObject> reportList = new TableView<>();
        TableColumn<TableObject, String> idColumn = new TableColumn<>("Report");
        idColumn.setCellValueFactory(p -> p.getValue().idProperty());
        idColumn.setPrefWidth(60);
        idColumn.setReorderable(false);
        reportList.getColumns().add(idColumn);
        TableColumn<TableObject, String> doctorColumn = new TableColumn<>("Medico");
        doctorColumn.setCellValueFactory(p -> p.getValue().doctorProperty());
        doctorColumn.setPrefWidth(60);
        doctorColumn.setReorderable(false);
        reportList.getColumns().add(doctorColumn);
        TableColumn<TableObject, String> reactionColumn = new TableColumn<>("Reazione");
        reactionColumn.setCellValueFactory(p -> p.getValue().reactionProperty());
        reactionColumn.setPrefWidth(100);
        reactionColumn.setReorderable(false);
        reportList.getColumns().add(reactionColumn);
        TableColumn<TableObject, String> reportDateColumn = new TableColumn<>("Data report");
        reportDateColumn.setCellValueFactory(p -> p.getValue().reportDateProperty());
        reportDateColumn.setPrefWidth(100);
        reportDateColumn.setReorderable(false);
        reportList.getColumns().add(reportDateColumn);
        TableColumn<TableObject, String> reactionDateColumn = new TableColumn<>("Data reazione");
        reactionDateColumn.setCellValueFactory(p -> p.getValue().reactionDateProperty());
        reactionDateColumn.setPrefWidth(100);
        reactionDateColumn.setReorderable(false);
        reportList.getColumns().add(reactionDateColumn);
        TableColumn<TableObject, String> vaccinationColumn = new TableColumn<>("Vaccinazioni due mesi precedenti alla reazione");
        vaccinationColumn.setCellValueFactory(p -> p.getValue().twoMonthsVaccinationsProperty());
        vaccinationColumn.setPrefWidth(260);
        vaccinationColumn.setReorderable(false);
        reportList.getColumns().add(vaccinationColumn);

        for (Report report : reports){
            List<Vaccination> twoMonthsVaccinations = controller.getPatientTwoMonthsVaccination(report.getPatient(), report.getReactionDate());
            String vaccinationString = twoMonthsVaccinations.stream().map(Vaccination::toString).collect(Collectors.joining("\n"));
            SimpleStringProperty vaccinationSimpleString = new SimpleStringProperty(vaccinationString);
            TableObject obj = new TableObject(report.idProperty(), report.doctorProperty(), report.reactionProperty(), report.reportDateProperty(), report.reactionDateProperty(), vaccinationSimpleString);
            reportList.getItems().add(obj);
        }
        reportList.setPlaceholder(new Label("No rows to display"));


        // Create the BorderPane
        BorderPane layout = new BorderPane();
        layout.setCenter(reportList);

        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            reportListStage.setScene(new Scene(new MainPagePharm(reportListStage, model).getView(), 700, 400));
            reportListStage.setTitle("Menù principale");
            reportListStage.setResizable(false);
            reportListStage.show();
        });

        Button analysisButton = new Button();
        analysisButton.setText("Analisi di base");
        analysisButton.setOnAction(e -> {
            reportListStage.setScene(new Scene(new ReportAnalysis(reportListStage, model).getView(), 700, 400));
            reportListStage.setTitle("Analisi di base");
            reportListStage.setResizable(false);
            reportListStage.show();
        });

        HBox buttons = new HBox(520, backButton, analysisButton);
        BorderPane.setAlignment(buttons, Pos.BOTTOM_CENTER);
        layout.setBottom(buttons);
        BorderPane.setMargin(buttons, new Insets(5));

        return layout;
    }

    public static class TableObject{
        private final SimpleStringProperty id;
        private final SimpleStringProperty reaction;
        private final SimpleStringProperty reportDate;
        private final SimpleStringProperty reactionDate;
        private final SimpleStringProperty doctor;
        private final SimpleStringProperty twoMonthsVaccinations;

        public TableObject(SimpleStringProperty id, SimpleStringProperty doctor, SimpleStringProperty reaction, SimpleStringProperty reportDate,
                           SimpleStringProperty reactionDate, SimpleStringProperty twoMonthsVaccinations){
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
        public SimpleStringProperty reactionProperty() { return reaction; }
        public SimpleStringProperty reportDateProperty() { return reportDate; }
        public SimpleStringProperty reactionDateProperty() { return reactionDate; }
        public SimpleStringProperty doctorProperty() { return doctor; }
        public SimpleStringProperty twoMonthsVaccinationsProperty() { return twoMonthsVaccinations; }
    }

}
