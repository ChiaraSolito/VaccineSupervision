package Control.FarmacologistControl;

import Model.Notice;
import Model.User;
import Model.Utils.DAOImpl.NoticeDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import View.PharmaView.MainPagePharm;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainControllerPharm {
    private User model;
    private MainPagePharm mainPagePharm;

    private NoticeDAOImpl noticesDAO;

    public MainControllerPharm(User model) {
        this.model = model;
    }

    public List<Notice> getUnreadNotices(){
        noticesDAO = new NoticeDAOImpl();
        List<Notice> notices = new ArrayList<>();

        try{
            notices = noticesDAO.getNotReadNotices(model.getUsername());
        } catch (NullStringException nse){
            System.err.println("String Error: " + nse.getMessage());
        }
        return notices;
    }
    public List<Notice> getNoticesList(){

        noticesDAO = new NoticeDAOImpl();
        List<Notice> notices = new ArrayList<>();

        try{
            notices = noticesDAO.getAllNotices(model.getUsername());
        } catch (NullStringException nse){
            System.err.println("String Error: ");
            nse.printStackTrace();
        }
        return notices;

    }

    public void setNoticeRead(Notice notice){
        noticesDAO = new NoticeDAOImpl();
        try{
            noticesDAO.setReadNotice(model.getUsername(), notice.getId());
        } catch (NullStringException nse){
            System.err.println("String Error: ");
            nse.printStackTrace();
        }
    }

    private void selectTask() {

    }

    public Parent getView() throws FileNotFoundException {
        return mainPagePharm.getView();
    }

    private void displayErrorMessage() {
    }

    private void loadPatientList(Stage stage) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda pazienti");
        dialog.showAndWait();
    }

    private void loadInsertView() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda cose");
        dialog.showAndWait();
    }
}
