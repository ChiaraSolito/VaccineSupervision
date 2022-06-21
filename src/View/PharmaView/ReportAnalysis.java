package View.PharmaView;

import Control.FarmacologistControl.ReportAnalysisController;
import Model.User;
import Model.Utils.DAOImpl.VaccinationDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;
import View.Utils.VaccinesList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


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

    Parent getView() throws NullStringException {
        List<String> vaccines = VaccinesList.getCovidVaccinesString();

        BorderPane layout = new BorderPane();

        //Title
        Label title = new Label("Analisi di base dei reports");
        layout.setTop(title);
        layout.setAlignment(title, Pos.TOP_CENTER);
        layout.setMargin(title, new Insets(5));


        //Segnalazioni per vaccino
        CategoryAxis xAxis = new CategoryAxis(); xAxis.setLabel("Vaccino");
        NumberAxis yAxis = new NumberAxis(); yAxis.setLabel("Numero di Reports");

        BarChart<String,Number> barChart1 = new BarChart(xAxis, yAxis);
        barChart1.setTitle("Segnalazioni per vaccino TOTALI");
        barChart1.setCategoryGap(20);

        XYChart.Series dataSeries1 = new XYChart.Series();
        for (String vaccine : vaccines){
            dataSeries1.getData().add(new XYChart.Data(vaccine, 60));
        }
        barChart1.getData().add(dataSeries1);

        BarChart barChart2 = new BarChart(xAxis, yAxis);
        barChart2.setTitle("Segnalazioni per vaccino ULTIMI 6 MESI");
        barChart2.setCategoryGap(20);

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.getData().add(new XYChart.Data("Desktop", 567));
        dataSeries2.getData().add(new XYChart.Data("Phone"  , 65));
        dataSeries2.getData().add(new XYChart.Data("Tablet"  , 23));
        barChart2.getData().add(dataSeries2);

        HBox vaccineReportsCharts = new HBox(barChart1, barChart2); //2 chart


        //Segnalazioni gravi in settimana
        BarChart barChart3 = new BarChart(xAxis, yAxis);
        barChart3.setTitle("Segnalazioni gravi in settimana");
        barChart3.setCategoryGap(20);

        XYChart.Series dataSeries3 = new XYChart.Series();
/*        dataSeries3.getData().add(new XYChart.Data("Desktop", 567));
        dataSeries3.getData().add(new XYChart.Data("Phone"  , 65));
        dataSeries3.getData().add(new XYChart.Data("Tablet"  , 23));*/
        barChart3.getData().add(dataSeries3);

        HBox severeReports = new HBox(); //chart a sx con numero reazioni gravi in settimana, tabella a dx


        //Segnalazioni per provincia (provincia residenza dei pazienti)
        TableView provinceReports = new TableView();


        //Segnalazioni per sede di vaccinazione
        TableView vaccinationSiteReports = new TableView();


        //TabPane
        TabPane views = new TabPane();
        Tab tab1 = new Tab("per vaccino", vaccineReportsCharts);
        Tab tab2 = new Tab("gravi in settimana", severeReports);
        Tab tab3 = new Tab("per provincia", provinceReports);
        Tab tab4 = new Tab("per sede di vaccinazione", vaccinationSiteReports);
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
