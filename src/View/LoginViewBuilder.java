package View;

import Model.User;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.FileNotFoundException;
import static javafx.geometry.Pos.CENTER_RIGHT;

public class LoginViewBuilder {
    private final User model;
    private Runnable actionRunnable;

    /*
        Costruttore
     */
    public LoginViewBuilder(User model, Runnable actionRunnable) {
        this.model = model;
        this.actionRunnable = actionRunnable;
    }

    /*
        Crea la view effettiva
     */
    public Region getView() throws FileNotFoundException {

        //Text title
        Text title = new Text("LOGIN FORM");

        //Login Form
        HBox userHBox = new HBox(10, new Text("User ID: "), createBoundTextField(model.userProperty()));
        HBox passwordHBox = new HBox(10, new Text("Password: "), createBoundPasswordField(model.passwordProperty()));
        Button loginButton = new Button("Login");
        loginButton.setOnAction(evt -> actionRunnable.run());
        VBox results = new VBox(10, userHBox, passwordHBox, loginButton);

        //Using an ImageView to set an image in the login form
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("pic/VaccineSupervision.png"));

        // Set the alignment
        BorderPane.setAlignment(title,Pos.TOP_CENTER);
        BorderPane.setAlignment(imageView,Pos.CENTER_LEFT);
        BorderPane.setAlignment(results,CENTER_RIGHT);

        // Create the upper BorderPane
        BorderPane borderPane = new BorderPane();

        // Set the Location
        borderPane.setTop(title);
        borderPane.setLeft(imageView);
        borderPane.setRight(results);

        return borderPane;
    }

    /*
        Metodo per il binding del Password Field
     */
    private Node createBoundPasswordField(StringProperty boundProperty) {
        PasswordField results = new PasswordField();
        results.textProperty().bindBidirectional(boundProperty);
        return results;
    }

    /*
        Metodo per il binding del Text Field
     */
    private Node createBoundTextField(StringProperty boundProperty) {
        TextField results = new TextField("");
        results.textProperty().bindBidirectional(boundProperty);
        return results;
    }

}
