package View.DoctorView;

import Model.User;
import View.LoginView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.geometry.Pos.CENTER_RIGHT;

public class MainPageDoc extends Parent {
    private final User model;

    private final Stage mainDocStage;

    /*
        Costruttore
     */
    public MainPageDoc(Stage stage, User model) {
        this.model = model;
        this.mainDocStage = stage;
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
        Label actions = new Label("Menù");
        Text helloText = new Text("Accesso eseguito come\nDOTTORE");

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
        Text text = new Text("Lista pazienti");
        Button button1 = createButton(new Button(), icon1, text);
        //inserisci una chiamata alla vista della lista pazienti
        button1.setOnAction(e -> {
            mainDocStage.setScene(new Scene(new PatientsList(mainDocStage, model).getView(), 700, 400));
            mainDocStage.setTitle("Lista dei pazienti");
            mainDocStage.setResizable(false);
            mainDocStage.show();
        });

        //
        ImageView icon2 = new ImageView("pic/reazione.png");
        Text text2 = new Text("Inserisci segnalazione");
        Button button2 = createButton(new Button(), icon2, text2);
        //inserire una chiamata al form inserisci segnalazione
        button2.setOnAction(e -> {
            mainDocStage.setScene(new Scene(new ReportForm(mainDocStage, model).getView(), 700, 400));
            mainDocStage.setTitle("Inserimento report");
            mainDocStage.setResizable(false);
            mainDocStage.show();
        });

        HBox layout = new HBox(button1, button2);
        VBox menu = new VBox(20, actions, layout);
        layout.setSpacing(20);
        layout.setPrefWidth(300);
        menu.setPrefWidth(300);

        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            mainDocStage.setTitle("Vaccine Supervision - Login");
            mainDocStage.setScene(new Scene(new LoginView(mainDocStage, new User()).getView(), 700, 400));
            mainDocStage.setResizable(false);
            mainDocStage.show();
        });

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
}
