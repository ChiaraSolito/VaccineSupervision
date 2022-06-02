package Control.DoctorControl;

import Model.Patient;
import Model.Report;
import Model.User;
import Model.Utils.DAOImpl.PatientDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;
import View.DoctorView.PatientInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class PatientInfoController {

    private static User model;

    /*
        Costruttore
     */
    public PatientInfoController(User model) {
        this.model = model;
    }

    public Patient getPatient(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        Patient p = new Patient();

        try {
            //patient = FXCollections.observableArrayList(patientDAO.getPatient(model.getUsername()));
            p = patientDAO.getPatient(patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return p;
    }

    public List<Report> getPatientReports(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        List<Report> reports;

        try {
            reports = patientDAO.getPatientReports(model.getUsername(), patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return reports;
    }

    public List<Vaccination> getPatientVaccinations(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        List<Vaccination> vaccinations;

        try {
            //patient = FXCollections.observableArrayList(patientDAO.getPatient(model.getUsername()));
            vaccinations = patientDAO.getPatientVaccinations(patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return vaccinations;
    }

}
