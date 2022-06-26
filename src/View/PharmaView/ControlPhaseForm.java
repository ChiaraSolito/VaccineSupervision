package View.PharmaView;

import Control.FarmacologistControl.ControlPhaseController;
import Model.ControlPhase;
import Model.User;
import View.Utils.Alerts;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlPhaseForm {
    private final User model;

    private static ControlPhaseController controller;

    private final Stage controlPhaseStage;
    private final ControlPhase controlPhase;

    /*
    Costruttore
     */
    public ControlPhaseForm(Stage stage, User model) {
        this.model = model;
        this.controlPhaseStage = stage;
        controller = new ControlPhaseController();
        this.controlPhase = new ControlPhase();
    }

    Parent getView() {

        BorderPane layout = new BorderPane();

        //set initial variables for control phase
        controlPhase.setPharmacologist(model.getUsername());
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //System.out.println(formatter.format(Calendar.getInstance().getTime()));
        //controlPhase.setReportDate(String.valueOf(Calendar.getInstance().get(Calendar.DATE)));

        //Second option: choose existing patient
        MenuButton chooseVaccine = new MenuButton("Scegli vaccino da porre sotto controllo");

        //Hidden Menu
        ToggleGroup group = new ToggleGroup();

        for (String vaccine : controller.getAllVaccines()) {
            RadioMenuItem sub = new RadioMenuItem("Vaccino: " + vaccine);
            chooseVaccine.getItems().add(sub);
            sub.setToggleGroup(group);
            sub.setOnAction(a -> {
                controlPhase.setVaccine(vaccine);
                chooseVaccine.setText("Vaccino: " + vaccine);
            });
        }

        Text header = new Text("Scegliere il vaccino da proporre per la fase di controllo");
        Button submit = new Button("Conferma");
        submit.setOnAction(e -> {
            if (group.getSelectedToggle() == null) {
                Alerts.displayErrorMessage(model);
            } else {
                if (Alerts.displayConfMessage(model).getResult() == ButtonType.OK) {
                    controller.addControlPhase(controlPhase);
                }
                controlPhaseStage.setScene(new Scene(new MainPagePharm(controlPhaseStage, model).getView(), 700, 400));
                controlPhaseStage.setResizable(false);
                controlPhaseStage.setTitle("Menù Principale");
                controlPhaseStage.show();
            }
        });

        //TABLE VIEW
        TableView<TableObject> vaccineTable = new TableView<>();
        TableColumn<TableObject, String> vaccineColumn = new TableColumn<>("Vaccino");
        vaccineColumn.setCellValueFactory(p -> p.getValue().vaccineProperty());
        vaccineColumn.setPrefWidth(100);
        vaccineColumn.setReorderable(false);
        vaccineTable.getColumns().add(vaccineColumn);
        TableColumn<TableObject, String> controlPhaseColumn = new TableColumn<>("Num. proposte controllo");
        controlPhaseColumn.setPrefWidth(200);
        controlPhaseColumn.setReorderable(false);
        TableColumn<TableObject, Number> generalCPColumn = new TableColumn<>("Totali");
        generalCPColumn.setCellValueFactory(p -> p.getValue().generalCPProperty());
        generalCPColumn.setPrefWidth(100);
        generalCPColumn.setReorderable(false);
        controlPhaseColumn.getColumns().add(generalCPColumn);
        TableColumn<TableObject, Number> sixMonthsCPColumn = new TableColumn<>("Ultimi 6 mesi");
        sixMonthsCPColumn.setCellValueFactory(new PropertyValueFactory<>("sixMonthsCP"));
        sixMonthsCPColumn.setCellValueFactory(p -> p.getValue().sixMonthsCPProperty());
        sixMonthsCPColumn.setPrefWidth(100);
        sixMonthsCPColumn.setReorderable(false);
        controlPhaseColumn.getColumns().add(sixMonthsCPColumn);
        vaccineTable.getColumns().add(controlPhaseColumn);

        for (String vaccine : controller.getAllVaccines()){
            SimpleIntegerProperty totalNumber = new SimpleIntegerProperty(controller.getTotalNumberControlPhase(vaccine));
            SimpleIntegerProperty sixMonthsNumber = new SimpleIntegerProperty(controller.getSixMonthsNumberControlPhase(vaccine));
            SimpleStringProperty vaccineString = new SimpleStringProperty(vaccine);
            vaccineTable.getItems().add(new TableObject(vaccineString, totalNumber, sixMonthsNumber));
        }
        vaccineTable.setPlaceholder(new Label("No rows to display"));

        //BackButton
        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            controlPhaseStage.setScene(new Scene(new MainPagePharm(controlPhaseStage, model).getView(), 700, 400));
            controlPhaseStage.setTitle("Menù Principale");
            controlPhaseStage.setResizable(false);
            controlPhaseStage.show();
        });

        //Layout settings
        Insets insets = new Insets(20);
        //On top are the informations
        BorderPane.setAlignment(header, Pos.TOP_CENTER);
        layout.setTop(header);
        BorderPane.setMargin(header, insets);
        //Center left for the vaccine
        BorderPane.setAlignment(chooseVaccine, Pos.CENTER_LEFT);
        layout.setLeft(chooseVaccine);
        BorderPane.setMargin(chooseVaccine, insets);

        //Center right for the table
        BorderPane.setAlignment(vaccineTable, Pos.CENTER_RIGHT);
        layout.setRight(vaccineTable);
        BorderPane.setMargin(vaccineTable, insets);

        HBox buttons = new HBox(500, backButton, submit);
        BorderPane.setAlignment(buttons, Pos.BOTTOM_CENTER);
        layout.setBottom(buttons);
        BorderPane.setMargin(buttons, insets);
        return layout;
    }

    public static class TableObject {
        private final SimpleStringProperty vaccine;
        private final SimpleIntegerProperty generalCP;
        private final SimpleIntegerProperty sixMonthsCP;

        public TableObject(SimpleStringProperty vaccine, SimpleIntegerProperty generalCP, SimpleIntegerProperty sixMonthsCP) {
            this.vaccine = vaccine;
            this.generalCP = generalCP;
            this.sixMonthsCP = sixMonthsCP;
        }

        public String getVaccine() {
            return vaccine.get();
        }

        public SimpleStringProperty vaccineProperty() {
            return vaccine;
        }

        public void setVaccine(String vaccine) {
            this.vaccine.set(vaccine);
        }

        public SimpleIntegerProperty generalCPProperty() {
            return generalCP;
        }

        public SimpleIntegerProperty sixMonthsCPProperty() {
            return sixMonthsCP;
        }
    }
}
