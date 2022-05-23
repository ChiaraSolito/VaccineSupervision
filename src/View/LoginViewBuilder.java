package View;

import Model.User;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
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

        Insets insets = new Insets(30);

        //Login Form - two HBoxes with corrected insets
        HBox userHBox = new HBox(10, new Text("User ID: "), createBoundTextField(model.userProperty()));
        HBox passwordHBox = new HBox(10, new Text("Password: "), createBoundPasswordField(model.passwordProperty()));
        userHBox.setPrefWidth(300);
        passwordHBox.setPrefWidth(300);

        //login button and VBox
        Button loginButton = new Button("Login");
        loginButton.setOnAction(evt -> actionRunnable.run());
        ImageView icon = new ImageView("pic/userIcon.png");
        icon.setFitWidth(50);
        icon.setFitHeight(50);
        VBox results = new VBox(10, icon, userHBox, passwordHBox, loginButton);
        results.setPrefWidth(300);
        results.setPadding(insets);

        //Using an ImageView to set an image in the login form
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("pic/VaccineSupervision.png"));
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);

        // Set the alignment
        BorderPane.setAlignment(imageView,Pos.CENTER_LEFT);
        BorderPane.setAlignment(results,CENTER_RIGHT);

        // Create the BorderPane
        BorderPane borderPane = new BorderPane();

        // Set the Location
        borderPane.setLeft(imageView);
        borderPane.setRight(results);
        BorderPane.setMargin(imageView, insets);
        BorderPane.setMargin(results, insets);

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
