import Control.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new LoginController(primaryStage).getView()));
        primaryStage.setTitle("Ciao Page");
        primaryStage.show();
    }
}
