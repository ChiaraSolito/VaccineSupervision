package Control.DoctorControl;

import Model.DataBase.DataBaseConnection;
import Model.User;
import Model.Utils.DAOImpl.PatientDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import View.DoctorView.MainPageDoc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientListController {
    private User model;

    private MainPageDoc mainPageDoc;

    DataBaseConnection userConnection;

    //Costruttore
    public PatientListController(User model) {
        this.model = model;
    }

    public List<String> getAllPatients(){
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        ObservableList<String> patients;

        try{
            patients = FXCollections.observableArrayList(patientDAO.getAllPatients(model.getUsername()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patients;
    }
}
