package View.PharmaView;

import Control.FarmacologistControl.ReportAnalysisController;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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
        controller = new ReportAnalysisController();
    }

    Parent getView() {
        Map<String, Integer> provinceReportsMap = controller.getReactionProvince();
        ObservableList<Map.Entry<String, Integer>> items = FXCollections.observableArrayList(provinceReportsMap.entrySet());
        Map<String, Integer> vaccinationSiteReportsMap = controller.getReactionSite();
        ObservableList<Map.Entry<String, Integer>> items2 = FXCollections.observableArrayList(vaccinationSiteReportsMap.entrySet());
        Map<String, Integer> totalNumberReportsMap = controller.getReactionNumber();
        Map<String, Integer> sixMonthsNumberReportsMap = controller.getReaction6Months();
        Map<String, Integer> severeReportsMap = controller.countVaccineSevereReaction();
        ObservableList<Map.Entry<String, Integer>> items5 = FXCollections.observableArrayList(severeReportsMap.entrySet());


        BorderPane layout = new BorderPane();

        //Title
        Label title = new Label("Analisi di base dei reports");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        layout.setTop(title);
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setMargin(title, new Insets(5));


        //Segnalazioni per vaccino
        PieChart pieChart1 = new PieChart();
        pieChart1.setTitle("Totali");
        int sum = 0;
        for(Map.Entry<String, Integer> mod: totalNumberReportsMap.entrySet()){
            PieChart.Data above = new PieChart.Data(mod.getKey(), mod.getValue());
            pieChart1.getData().add(above);
            sum += mod.getValue();
        }
        pieChart1.setLabelsVisible(true);

        int finalSum = sum;
        pieChart1.getData().forEach(data -> {
            String percentage = String.format("%.0f / %d", data.getPieValue(), finalSum);
            Tooltip tooltip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), tooltip);
        });

        PieChart pieChart2 = new PieChart();
        int sum2 = 0;
        for(Map.Entry<String, Integer> mod: sixMonthsNumberReportsMap.entrySet()){
            PieChart.Data above = new PieChart.Data(mod.getKey(), mod.getValue());
            pieChart2.getData().add(above);
            sum2 += mod.getValue();
        }
        pieChart2.setLabelsVisible(true);

        int finalSum2 = sum2;
        pieChart2.setTitle("Ultimi 6 mesi");
        pieChart2.getData().forEach(data -> {
            String percentage = String.format("%.0f / %d", data.getPieValue(), finalSum2);
            Tooltip tooltip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), tooltip);
        });

        HBox vaccineReportsCharts = new HBox(pieChart1, pieChart2);


        //Segnalazioni gravi in settimana
        CategoryAxis xAxis3 = new CategoryAxis(); xAxis3.setLabel("Vaccino");
        NumberAxis yAxis3 = new NumberAxis(); yAxis3.setLabel("Numero di Reports");
        BarChart<String, Number> barChart3 = new BarChart<>(xAxis3, yAxis3);
        barChart3.setTitle("Segnalazioni gravi in settimana");
        barChart3.setCategoryGap(20);
        barChart3.setLegendVisible(false);

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : severeReportsMap.entrySet()) {
            String tmpString = entry.getKey();
            Number tmpValue = entry.getValue();
            XYChart.Data<String, Number> d = new XYChart.Data<>(tmpString, tmpValue);
            dataSeries.getData().add(d);
        }
        barChart3.getData().add(dataSeries);
        barChart3.setMaxWidth(450);


        TableView<Map.Entry<String, Integer>> severeReportsTable = new TableView<>();
        TableColumn<Map.Entry<String, Integer>, String> vaccineColumn = new TableColumn<>("Vaccino");
        vaccineColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        vaccineColumn.setPrefWidth(120);
        vaccineColumn.setReorderable(false);
        severeReportsTable.getColumns().add(vaccineColumn);
        TableColumn<Map.Entry<String, Integer>, String> numberSevereForVaccineColumn = new TableColumn<>("#segnalazioni gravi");
        numberSevereForVaccineColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        numberSevereForVaccineColumn.setPrefWidth(120);
        numberSevereForVaccineColumn.setReorderable(false);
        severeReportsTable.getColumns().add(numberSevereForVaccineColumn);
        severeReportsTable.setItems(items5);
        severeReportsTable.setPlaceholder(new Label("No rows to display"));

        HBox severeReports = new HBox(barChart3, severeReportsTable);


        //Segnalazioni per provincia (provincia residenza dei pazienti)
        TableView<Map.Entry<String, Integer>> provinceReports = new TableView<>();
        TableColumn<Map.Entry<String, Integer>, String> provinceColumn = new TableColumn<>("Provincia");
        provinceColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        provinceColumn.setPrefWidth(200);
        provinceColumn.setReorderable(false);
        provinceReports.getColumns().add(provinceColumn);
        TableColumn<Map.Entry<String, Integer>, String> numberForProvinceColumn = new TableColumn<>("Numero segnalazioni");
        numberForProvinceColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        numberForProvinceColumn.setPrefWidth(200);
        numberForProvinceColumn.setReorderable(false);
        provinceReports.getColumns().add(numberForProvinceColumn);
        provinceReports.setItems(items);
        provinceReports.setPlaceholder(new Label("No rows to display"));

        HBox provinceReportsTab2 = new HBox(provinceReports);
        provinceReportsTab2.setAlignment(Pos.CENTER);


        //Segnalazioni per sede di vaccinazione
        TableView<Map.Entry<String, Integer>> vaccinationSiteReports = new TableView<>();
        TableColumn<Map.Entry<String, Integer>, String> vaccinationSiteColumn = new TableColumn<>("Sito di vaccinazione");
        vaccinationSiteColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        vaccinationSiteColumn.setPrefWidth(200);
        vaccinationSiteColumn.setReorderable(false);
        vaccinationSiteReports.getColumns().add(vaccinationSiteColumn);
        TableColumn<Map.Entry<String, Integer>, String> numberForVaccinationSiteColumn = new TableColumn<>("Numero segnalazioni");
        numberForVaccinationSiteColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        numberForVaccinationSiteColumn.setPrefWidth(200);
        numberForVaccinationSiteColumn.setReorderable(false);
        vaccinationSiteReports.getColumns().add(numberForVaccinationSiteColumn);
        vaccinationSiteReports.setItems(items2);
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
        BorderPane.setMargin(views, new Insets(5));

        //BackButton
        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            reportAnalysisStage.setScene(new Scene(new ReportList(reportAnalysisStage, model).getView(), 700, 400));
            reportAnalysisStage.setTitle("Lista segnalazioni");
            reportAnalysisStage.setResizable(false);
            reportAnalysisStage.show();
        });

        layout.setBottom(backButton);
        BorderPane.setMargin(backButton, new Insets(5));

        return layout;
    }
}
