package View.PharmaView;

import Control.FarmacologistControl.MainControllerPharm;
import Model.Notice;
import Model.User;
import View.LoginView;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.List;

import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.geometry.Pos.CENTER_RIGHT;

public class MainPagePharm extends Parent {
    private final User model;
    public final Stage mainPharmStage;

    // Connection with the controller
    private final MainControllerPharm controller;

    /*
        Costruttore
     */
    public MainPagePharm(Stage stage, User model) {
        this.model = model;
        this.mainPharmStage = stage;
        this.controller = new MainControllerPharm(model);

    }

    /*
        Crea la view effettiva
     */
    public Region getView() {

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
        ImageView icon1 = new ImageView("pic/clipboardIcon.png");
        Text text = new Text("Accedi a Segnalazioni");
        Button button1 = createButton(new Button(), icon1, text);
        button1.setPrefWidth(75);
        button1.setOnAction(e -> {
            mainPharmStage.setScene(new Scene(new ReportList(mainPharmStage, model).getView(), 700, 400));
            mainPharmStage.setTitle("Lista segnalazioni");
            mainPharmStage.setResizable(false);
            mainPharmStage.show();
        });

        //
        ImageView icon2 = new ImageView("pic/notice.png");
        Text text2 = new Text("Avvisi già letti");
        Button button2 = createButton(new Button(), icon2, text2);
        button2.setPrefWidth(75);
        button2.setOnAction(e -> {
            mainPharmStage.setScene(new Scene(new ReadNoticeList(mainPharmStage, model).getView(), 700, 400));
            mainPharmStage.setTitle("Lista avvisi");
            mainPharmStage.setResizable(false);
            mainPharmStage.show();
        });

        ImageView icon3 = new ImageView("pic/control.png");
        Text text3 = new Text("Proponi fase controllo");
        Button button3 = createButton(new Button(), icon3, text3);
        button3.setPrefWidth(75);
        //setOnAction per la fase controllo
        button3.setOnAction(e -> {
            mainPharmStage.setScene(new Scene(new ControlPhaseForm(mainPharmStage, model).getView(), 700, 400));
            mainPharmStage.setTitle("Proponi fase controllo");
            mainPharmStage.setResizable(false);
            mainPharmStage.show();
        });

        HBox layout = new HBox(button1, button2, button3);
        VBox menu = new VBox(20, actions, layout);
        layout.setSpacing(10);
        layout.setPrefWidth(300);
        menu.setPrefWidth(300);

        // Set the alignment
        BorderPane.setAlignment(docInfo, CENTER_RIGHT);
        BorderPane.setAlignment(menu, CENTER_LEFT);

        // Create the BorderPane
        BorderPane borderPane = new BorderPane();

        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            mainPharmStage.setTitle("Drug Supervision - Login");
            mainPharmStage.setScene(new Scene(new LoginView(mainPharmStage, new User()).getView(), 700, 400));
            mainPharmStage.setResizable(false);
            mainPharmStage.show();
        });

        // Set the Location
        borderPane.setRight(docInfo);
        borderPane.setLeft(menu);
        BorderPane.setMargin(menu, insets);
        BorderPane.setMargin(docInfo, insets);
        //Down on the right is the button
        BorderPane.setAlignment(logout, Pos.BOTTOM_RIGHT);
        borderPane.setBottom(logout);
        BorderPane.setMargin(logout, insets);
        return borderPane;

    }

    private Button createButton(Button button, ImageView icon, Text text) {
        text.setWrappingWidth(100);
        text.setTextAlignment(TextAlignment.CENTER);
        VBox vBox = new VBox(5, icon, text);
        vBox.setAlignment(Pos.CENTER);
        button.setGraphic(vBox);

        button.setMinHeight(120);
        button.setMinWidth(120);
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
