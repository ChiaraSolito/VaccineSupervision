package View.DoctorView;

import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

import static javafx.geometry.Pos.*;

public class MainPage {
    private final User model;
    private static Runnable actionRunnable;

    /*
        Costruttore
     */
    public MainPage(User model, Runnable actionRunnable) {
        this.model = model;
        this.actionRunnable = actionRunnable;
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

        VBox total = new VBox(10, helloText, img, user, actions);
        total.setPrefWidth(300);

        /*
            Buttons creation
         */
        //Lista pazienti
        Button button1 = new Button("Lista Pazienti");
        ImageView icon1 = new ImageView("pic/clipboardIcon.png");
        icon1.setFitWidth(30);
        icon1.setFitHeight(30);
        button1.setGraphic(icon1);
        button1.setOnAction(evt -> actionRunnable.run());

        //
        Button button2 = new Button("Button2");
        Button button3 = new Button("Button3");
        Button button4 = new Button("Button4");
        Button button5 = new Button("Button5");
        Button button6 = new Button("Button6");

        TilePane layout = new TilePane(button1, button2, button3, button4, button5, button6);

        //Layout settings
        layout.setStyle("-fx-padding: 10");
        layout.setPrefColumns(3);

        // Set the alignment
        BorderPane.setAlignment(total, TOP_CENTER);
        BorderPane.setAlignment(layout,CENTER);

        // Create the BorderPane
        BorderPane borderPane = new BorderPane();

        // Set the Location
        borderPane.setTop(total);
        borderPane.setCenter(layout);
        BorderPane.setMargin(total, insets);
        BorderPane.setMargin(layout, insets);

        return borderPane;

    }

}
