package Control.DoctorControl;

import Model.User;
import Model.Utils.DAOImpl.PatientDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class PatientListController {
    private final User model;

    //Costruttore
    public PatientListController(User model) {
        this.model = model;
    }

    public List<String> getAllPatients() throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        ObservableList<String> patients;

        try {
            patients = FXCollections.observableArrayList(patientDAO.getAllPatients(model.getUsername()));
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return patients;
    }
}
