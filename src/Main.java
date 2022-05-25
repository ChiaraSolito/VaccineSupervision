import Control.LoginController;
import Model.User;
import View.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage = new LoginView(primaryStage, new User()).getView();
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }
}
