package View.PharmaView;

import Control.FarmacologistControl.ReportAnalysisController;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import View.Utils.VaccinesList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;
import java.util.Map;


public class ReportAnalysis {

    private final User model;

    private static ReportAnalysisController controller;

    private final Stage reportAnalysisStage;

    /*
    Costruttore
     */
    public ReportAnalysis(Stage stage, User modelRA) {
        model = modelRA;
        reportAnalysisStage = stage;
        controller = new ReportAnalysisController(model);
    }

    Parent getView() throws NullStringException {
        List<String> vaccines = VaccinesList.getCovidVaccinesString();
        Map<String, Integer> provinceReportsMap = controller.getReactionProvince();
        ObservableList<Map.Entry<String, Integer>> items = FXCollections.observableArrayList(provinceReportsMap.entrySet());
        Map<String, Integer> vaccinationSiteReportsMap = controller.getReactionSite();
        ObservableList<Map.Entry<String, Integer>> items2 = FXCollections.observableArrayList(vaccinationSiteReportsMap.entrySet());
        Map<String, Integer> totalNumberReportsMap = controller.getReactionNumber();
        Map<String, Integer> sixMonthsNumberReportsMap = controller.getReaction6Months();
        Map<String, Integer> severeReportsMap = controller.getReaction6Months();
        ObservableList<Map.Entry<String, Integer>> items5 = FXCollections.observableArrayList(severeReportsMap.entrySet());


        BorderPane layout = new BorderPane();

        //Title
        Label title = new Label("Analisi di base dei reports");
        layout.setTop(title);
        layout.setAlignment(title, Pos.TOP_CENTER);
        layout.setMargin(title, new Insets(5));


        //Segnalazioni per vaccino
        PieChart pieChart1 = new PieChart();
        pieChart1.setTitle("Totali");
        for(Map.Entry<String, Integer> mod: totalNumberReportsMap.entrySet()){
            PieChart.Data above = new PieChart.Data(mod.getKey(), mod.getValue());
            pieChart1.getData().add(above);
        }
        pieChart1.setLabelsVisible(true);

        PieChart pieChart2 = new PieChart();
        pieChart2.setTitle("Ultimi 6 mesi");
        for(Map.Entry<String, Integer> mod: sixMonthsNumberReportsMap.entrySet()){
            PieChart.Data above = new PieChart.Data(mod.getKey(), mod.getValue());
            pieChart2.getData().add(above);
        }
        pieChart2.setLabelsVisible(true);

        HBox vaccineReportsCharts = new HBox(pieChart1, pieChart2);


        //Segnalazioni gravi in settimana
        CategoryAxis xAxis3 = new CategoryAxis(); xAxis3.setLabel("Vaccino");
        NumberAxis yAxis3 = new NumberAxis(); yAxis3.setLabel("Numero di Reports");
        BarChart barChart3 = new BarChart(xAxis3, yAxis3);
        barChart3.setTitle("Segnalazioni gravi in settimana");
        barChart3.setCategoryGap(20);
        barChart3.setLegendVisible(false);

        XYChart.Series<String, Integer> dataSeries = new XYChart.Series();
        for (Map.Entry<String, Integer> entry : severeReportsMap.entrySet()) {
            String tmpString = entry.getKey();
            Integer tmpValue = entry.getValue();
            XYChart.Data<String, Integer> d = new XYChart.Data<>(tmpString, tmpValue);
            dataSeries.getData().add(d);
        }
        barChart3.getData().add(dataSeries);
        barChart3.setMaxWidth(450);


        TableView severeReportsTable = new TableView();
        TableColumn<Map.Entry<String, Integer>, String> vaccineColumn = new TableColumn<>("Vaccino");
        vaccineColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });
        vaccineColumn.setPrefWidth(120);
        TableColumn<Map.Entry<String, Integer>, String> numberSevereForVaccineColumn = new TableColumn<>("#segnalazioni gravi");
        numberSevereForVaccineColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().toString());
            }
        });
        numberSevereForVaccineColumn.setPrefWidth(120);
        severeReportsTable.getColumns().addAll(vaccineColumn, numberSevereForVaccineColumn);
        severeReportsTable.setItems(items5);
        severeReportsTable.setPlaceholder(new Label("No rows to display"));

        HBox severeReports = new HBox(barChart3, severeReportsTable);


        //Segnalazioni per provincia (provincia residenza dei pazienti)
        TableView provinceReports = new TableView();
        TableColumn<Map.Entry<String, Integer>, String> provinceColumn = new TableColumn<>("Provincia");
        provinceColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });
        provinceColumn.setPrefWidth(200);
        TableColumn<Map.Entry<String, Integer>, String> numberForProvinceColumn = new TableColumn<>("Numero segnalazioni");
        numberForProvinceColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().toString());
            }
        });
        numberForProvinceColumn.setPrefWidth(200);
        provinceReports.getColumns().addAll(provinceColumn, numberForProvinceColumn);
        provinceReports.setItems(items);
        provinceReports.setPlaceholder(new Label("No rows to display"));

        HBox provinceReportsTab2 = new HBox(provinceReports);
        provinceReportsTab2.setAlignment(Pos.CENTER);


        //Segnalazioni per sede di vaccinazione
        TableView vaccinationSiteReports = new TableView();
        TableColumn<Map.Entry<String, Integer>, String> vaccinationSiteColumn = new TableColumn<>("Sito di vaccinazione");
        vaccinationSiteColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });
        vaccinationSiteColumn.setPrefWidth(200);
        TableColumn<Map.Entry<String, Integer>, String> numberForVaccinationSiteColumn = new TableColumn<>("Numero segnalazioni");
        numberForVaccinationSiteColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().toString());
            }
        });
        numberForVaccinationSiteColumn.setPrefWidth(200);
        vaccinationSiteReports.setItems(items2);
        vaccinationSiteReports.getColumns().addAll(vaccinationSiteColumn, numberForVaccinationSiteColumn);
        vaccinationSiteReports.setPlaceholder(new Label("No rows to display"));

        HBox vaccinationSiteReportsTab3 = new HBox(500, vaccinationSiteReports);
        vaccinationSiteReportsTab3.setAlignment(Pos.CENTER);


        //TabPane
        TabPane views = new TabPane();
        Tab tab1 = new Tab("per vaccino", vaccineReportsCharts);
        Tab tab2 = new Tab("gravi in settimana", severeReports);
        Tab tab3 = new Tab("per provincia", provinceReportsTab2);
        Tab tab4 = new Tab("per sede di vaccinazione", vaccinationSiteReportsTab3);
        views.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        views.getTabs().addAll(tab1, tab2, tab3, tab4);
        layout.setCenter(views);

        //BackButton
        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            try {
                reportAnalysisStage.setScene(new Scene(new ReportList(reportAnalysisStage, model).getView(), 700, 400));
                reportAnalysisStage.setTitle("Menù principale");
                reportAnalysisStage.setResizable(false);
                reportAnalysisStage.show();
            } catch (NullStringException ex){
                throw new RuntimeException(ex);
            }
        });

        layout.setBottom(backButton);
        layout.setMargin(backButton, new Insets(5));

        return layout;
    }
}
