package View.DoctorView;

import Model.User;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

import java.io.FileNotFoundException;

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

        //Button creation
        Button button1 = new Button("Lista Pazienti");
        button1.setOnAction(evt -> actionRunnable.run());
        Button button2 = new Button("Button2");
        Button button3 = new Button("Button3");
        Button button4 = new Button("Button4");
        Button button5 = new Button("Button5");
        Button button6 = new Button("Button6");

        TilePane layout = new TilePane(button1, button2, button3, button4, button5, button6);

        //Layout settings
        layout.setHgap(20);
        layout.setVgap(20);
        layout.setStyle("-fx-padding: 10");
        layout.setPrefColumns(3);


        return layout;
    }

}
