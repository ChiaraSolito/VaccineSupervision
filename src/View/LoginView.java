package View;

import Control.LoginController;
import Model.User;
import View.DoctorView.MainPageDoc;
import View.PharmaView.MainPagePharm;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class LoginView {

    private final Stage primaryStage;

    private final User model;

    private LoginController controller;

    /*
        Costruttore
    */
    public LoginView(Stage stage, User model) {
        this.primaryStage = stage;
        this.model = model;
        controller = new LoginController(model);
    }

    /*
        Crea la view effettiva
     */
    public Stage getView() throws FileNotFoundException {

        Insets insets = new Insets(30);

        //Login Form - two HBoxes with corrected insets
        HBox userHBox = new HBox(10, new Text("User ID: "), createBoundTextField(model.userProperty()));
        HBox passwordHBox = new HBox(10, new Text("Password: "), createBoundPasswordField(model.passwordProperty()));
        userHBox.setPrefWidth(300);
        passwordHBox.setPrefWidth(300);

        //login button and VBox
        Button loginButton = new Button("Login");
        loginButton.setOnAction(evt -> {
            try {
                int flag = controller.checkAccess();
                if (flag == 0) {
                    primaryStage.setScene(new Scene(new MainPageDoc(primaryStage, model).getView(), 700, 400));
                    primaryStage.setResizable(false);
                    primaryStage.setTitle("Menù Principale");
                    primaryStage.show();
                } else if (flag == 1) {
                    MainPagePharm main = new MainPagePharm(primaryStage, model);
                    primaryStage.setScene(new Scene(main.getView(), 700, 400));
                    primaryStage.setResizable(false);
                    primaryStage.setTitle("Menù Principale");
                    primaryStage.show();

                    Alert dialog = main.readNotice();
                    if (dialog != null) {
                        dialog.showAndWait();
                    }

                } else {
                    displayErrorMessage();
                }
            } catch (FileNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });

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
        BorderPane.setAlignment(imageView, Pos.CENTER_LEFT);
        BorderPane.setAlignment(results, Pos.CENTER_RIGHT);

        // Create the BorderPane
        BorderPane borderPane = new BorderPane();

        // Set the Location
        borderPane.setLeft(imageView);
        borderPane.setRight(results);
        BorderPane.setMargin(imageView, insets);
        BorderPane.setMargin(results, insets);

        primaryStage.setTitle("Drug Supervision - Login");
        primaryStage.setScene(new Scene(borderPane, 700, 400));
        primaryStage.setResizable(false);

        return primaryStage;
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

    private void displayErrorMessage() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("Incorrect Username or Password");
        dialog.setContentText("You have entered incorrect username or Password.\nPlease try Again and check if you chose the right user type.");
        dialog.showAndWait();
    }
}
