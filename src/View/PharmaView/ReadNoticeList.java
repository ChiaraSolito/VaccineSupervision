package View.PharmaView;

import Control.DoctorControl.PatientListController;
import Control.FarmacologistControl.ReadNoticeListController;
import Model.Notice;
import Model.Report;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;
import View.DoctorView.MainPageDoc;
import View.DoctorView.PatientsList;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ReadNoticeList extends Parent {
    private static User model;

    private final ReadNoticeListController controller;

    private static Stage noticeListPharmStage;

    /*
        Costruttore
     */
    public ReadNoticeList(Stage stage, User model) {
        this.model = model;
        this.noticeListPharmStage = stage;
        controller = new ReadNoticeListController(model);
    }


    public Parent getView() throws NullStringException {
        ObservableList<Notice> notices = FXCollections.observableArrayList(controller.getReadNotice());

        // Create the BorderPane
        BorderPane layout = new BorderPane();
        TableView<Notice> noticeList = new TableView<>();
        TableColumn<Notice, String> idColumn = new TableColumn<>("Codice avviso");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(125);
        TableColumn<Notice, String> contentColumn = new TableColumn<>("Content");
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        contentColumn.setPrefWidth(450);
        TableColumn<Notice, String> dateColumn = new TableColumn<>("Data avviso");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("noticeDate"));
        dateColumn.setPrefWidth(125);

        contentColumn.setCellFactory(new Callback<TableColumn<Notice,String>, TableCell<Notice,String>>() {
            @Override
            public TableCell<Notice, String> call( TableColumn<Notice, String> param) {
                final TableCell<Notice, String> cell = new TableCell<Notice, String>() {
                    private Text text;
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item.toString());
                            text.setWrappingWidth(425); // Setting the wrapping width to the Text
                            setGraphic(text);
                        }
                    }
                };
                return cell;
            }
        });

        noticeList.getColumns().addAll(idColumn, dateColumn, contentColumn);
        noticeList.setItems(notices);
        noticeList.setPlaceholder(new Label("No rows to display"));
        layout.setCenter(noticeList);

        Button backButton = new Button();
        backButton.setText("Indietro");
        backButton.setOnAction(e -> {
            try {
                noticeListPharmStage.setScene(new Scene(new MainPagePharm(noticeListPharmStage, model).getView(), 700, 400));
                noticeListPharmStage.setTitle("Menù principale");
                noticeListPharmStage.setResizable(false);
                noticeListPharmStage.show();
            } catch (FileNotFoundException ex){
                throw new RuntimeException(ex);
            }
        });
        layout.setBottom(backButton);
        layout.setAlignment(backButton, Pos.CENTER_LEFT);
        layout.setMargin(backButton, new Insets(5, 5, 5, 5));

        return layout;
    }

}
