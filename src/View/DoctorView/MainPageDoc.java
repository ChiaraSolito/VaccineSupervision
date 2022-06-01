package View.DoctorView;

import Control.DoctorControl.MainControllerDoc;
import Model.User;
import Model.Utils.Exceptions.NullStringException;
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
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import static javafx.geometry.Pos.*;

public class MainPageDoc extends Parent {
    private final User model;

    private final MainControllerDoc controller;

    private final Stage mainDocStage;

    /*
        Costruttore
     */
    public MainPageDoc(Stage stage, User model) {
        this.model = model;
        this.mainDocStage = stage;
        controller = new MainControllerDoc();
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
        Text helloText = new Text("Ciao Doc!");

        HBox user = new HBox(10, new Text("User ID: "), new Text(model.getUsername()));
        user.setPrefWidth(300);

        VBox docInfo = new VBox(10, helloText, img, user);
        Color set = Color.web("0x236D5E",1.0);
        docInfo.setBorder(new Border(new BorderStroke( set, set, set, set,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));
        docInfo.setSpacing(20);
        docInfo.setPrefWidth(300);

        /*
            Buttons creation
         */
        //Lista pazienti
        Button button1 = new Button();
        ImageView icon1 = new ImageView("pic/clipboardIcon.png");
        Text text=new Text("Lista pazienti");
        button1 = createButton(button1, icon1, text);
        //inserisci una chiamata alla vista della lista pazienti
        button1.setOnAction(e -> {
            try {
                mainDocStage.setScene(new Scene(new PatientsList(mainDocStage, model).getView(),700,400));
                mainDocStage.setTitle("Lista dei pazienti");
                mainDocStage.setResizable(false);
                mainDocStage.show();
            } catch (NullStringException ex) {
                throw new RuntimeException(ex);
            }
        });

        //
        Button button2 = new Button();
        ImageView icon2 = new ImageView("pic/reazione.png");
        Text text2=new Text("Inserisci reazione");
        button2 = createButton(button2, icon2, text2);
        //inserire una chiamata al form inserisci reazione
        button2.setOnAction(e -> {
            try {
                mainDocStage.setScene(new Scene(new ReactionForm(mainDocStage, model).getView(),700,400));
                mainDocStage.setTitle("Inserimento reazione");
                mainDocStage.setResizable(false);
                mainDocStage.show();
            } catch (NullStringException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox layout = new HBox(button1, button2);
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
        button.setMinWidth(50);
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        return button;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
