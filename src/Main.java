import Model.User;
import View.LoginView;
import View.Utils.VaccinesList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        VaccinesList.populate();
        primaryStage.setTitle("Drug Supervision - Login");
        primaryStage.setScene(new Scene(new LoginView(primaryStage, new User()).getView(), 700, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
