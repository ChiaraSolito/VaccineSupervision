package View.PharmaView;

import Control.FarmacologistControl.ReadNoticeListController;
import Model.Notice;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileNotFoundException;

public class ReadNoticeList extends Parent {
    private static User model;

    private final ReadNoticeListController controller;

    private static Stage noticeListPharmStage;

    /*
        Costruttore
     */
    public ReadNoticeList(Stage stage, User modelRD) {
        model = modelRD;
        noticeListPharmStage = stage;
        controller = new ReadNoticeListController(model);
    }


    public Parent getView() throws NullStringException {
        ObservableList<Notice> notices = FXCollections.observableArrayList(controller.getReadNotice());

        // Create the BorderPane
        BorderPane layout = new BorderPane();
        TableView<Notice> noticeList = new TableView<>();
        TableColumn<Notice, String> idColumn = new TableColumn<>("Codice avviso");
        idColumn.setCellValueFactory(p -> p.getValue().idProperty());
        idColumn.setPrefWidth(125);
        noticeList.getColumns().add(idColumn);
        TableColumn<Notice, String> contentColumn = new TableColumn<>("Content");
        contentColumn.setCellValueFactory(p -> p.getValue().contentProperty());
        contentColumn.setPrefWidth(450);
        noticeList.getColumns().add(contentColumn);
        TableColumn<Notice, String> dateColumn = new TableColumn<>("Data avviso");
        dateColumn.setCellValueFactory(p -> p.getValue().noticeDateProperty());
        dateColumn.setPrefWidth(125);
        noticeList.getColumns().add(dateColumn);

        contentColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Notice, String> call( TableColumn<Notice, String> param) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            Text text = new Text(item);
                            text.setWrappingWidth(425); // Setting the wrapping width to the Text
                            setGraphic(text);
                        }
                    }
                };
            }
        });

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
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
        BorderPane.setMargin(backButton, new Insets(5, 5, 5, 5));

        return layout;
    }

}
