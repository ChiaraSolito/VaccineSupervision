package View.PharmaView;

import Control.DoctorControl.MainControllerDoc;
import Control.FarmacologistControl.MainControllerPharm;
import Model.Notice;
import Model.User;
import View.DoctorView.MainPageDoc;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static javafx.geometry.Pos.*;

public class MainPagePharm extends Parent {
    private final User model;
    public final Stage mainPharmStage;

    // Connection with the controller
    private final MainControllerPharm controller;

    /*
        Costruttore
     */
    public MainPagePharm(Stage stage, User model) throws FileNotFoundException {
        this.model = model;
        this.mainPharmStage = stage;
        this.controller = new MainControllerPharm(model);

    }

    /*
        Crea la view effettiva
     */
    public Region getView() throws FileNotFoundException {

        //insets
        Insets insets = new Insets(30);

        //Login Information
        ImageView img = new ImageView("pic/userIcon.png");
        img.setFitWidth(20);
        img.setFitHeight(20);
        Label actions = new Label("Menù:");
        Text helloText = new Text("Ciao Farmacologo!");

        HBox user = new HBox(10, new Text("User ID: "), new Text(model.getUsername()));
        user.setPrefWidth(300);

        VBox docInfo = new VBox(10, helloText, img, user);
        Color set = Color.web("0x236D5E",1.0);
        docInfo.setBorder(new Border(new BorderStroke( set, set, set, set,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));
        docInfo.setPadding(insets);
        docInfo.setSpacing(40);
        docInfo.setPrefWidth(200);



        /*
            Buttons creation
         */
        //Lista pazienti
        Button button1 = new Button();
        ImageView icon1 = new ImageView("pic/clipboardIcon.png");
        Text text=new Text("Accedi a Segnalazioni");
        button1 = createButton(button1, icon1, text);

        //
        Button button2 = new Button();
        ImageView icon2 = new ImageView("pic/notice.png");
        Text text2=new Text("Avvisi già letti");
        button2 = createButton(button2, icon2, text2);

        Button button3 = new Button();
        ImageView icon3 = new ImageView("pic/control.png");
        Text text3=new Text("Attiva fase controllo");
        button3 = createButton(button3, icon3, text3);
        //inserire il setOnAction per la fase controllo

        //ObservableList<Button> menu = FXCollections.observableArrayList(button1);
        //ListView<String> listView = new ListView<String>(menu);

        HBox layout = new HBox(button1, button2, button3);
        VBox menu = new VBox(20, actions, layout);
        layout.setSpacing(20);
        layout.setPrefWidth(300);
        menu.setPrefWidth(300);

        // Set the alignment
        BorderPane.setAlignment(docInfo, CENTER_RIGHT);
        BorderPane.setAlignment(menu, CENTER_LEFT);

        // Create the BorderPane
        BorderPane borderPane = new BorderPane();

        // Set the Location
        borderPane.setRight(docInfo);
        borderPane.setLeft(menu);
        BorderPane.setMargin(menu, insets);
        BorderPane.setMargin(docInfo, insets);
        return borderPane;

    }

    private Button createButton(Button button, ImageView icon, Text text) {
        text.setWrappingWidth(100);
        VBox vBox = new VBox(5, icon,text);
        vBox.setAlignment(Pos.CENTER);
        button.setGraphic(vBox);

        button.setMinHeight(100);
        button.setMinWidth(100);
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        return button;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }


    public Alert readNotice() {
        List<Notice> notices = controller.getUnreadNotices();

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);

        if (!notices.isEmpty()) {
            for (Notice notice : notices) {

                dialog.setTitle("Avviso!");
                dialog.setHeaderText("Nuovo Avviso!");
                dialog.setContentText(notice.getContent());
                dialog.getDialogPane();

                controller.setNoticeRead(notice);
            }
        } else {
            dialog = null;
        }
        return dialog;
    }
}
