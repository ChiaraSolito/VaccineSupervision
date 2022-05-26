package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Patient;
import Model.Report;
import Model.RiskFactor;
import Model.Utils.DAO.PatientDAO;
import Model.Vaccination;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    DataBaseConnection pConnection;

    @Override
    public List<Patient> getAllPatients(String username) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Patient> patients = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT P.idpatient, P.birthyear, P.province, P.profession " +
                "FROM Report R JOIN Patient P ON P.idpatient = R.idpatient, " +
                "WHERE R.doctor = '" + username + "'");

        /*
            La lista dei fattori di rischio è inserita vuota, perché saranno inseriti in una query successiva
         */
        while(pConnection.rs.next()){
            List<RiskFactor> listaVuota = new ArrayList<>();
            patients.add(new Patient(
                    new SimpleStringProperty(pConnection.rs.getString("idpatient")),
                    new SimpleStringProperty(pConnection.rs.getString("birthyear")),
                    new SimpleStringProperty(pConnection.rs.getString("province")),
                    new SimpleStringProperty(pConnection.rs.getString("profession")),
                    listaVuota
            ));
        }

        for (Patient p : patients){
            pConnection.rs = pConnection.statement.executeQuery("SELECT RF.name, RF.risklevel, RF.description" +
                    "FROM RiskFactor RF JOIN PatientRisk PR ON PR.risk = RF.name " +
                    "WHERE PR.idpatient = '" + p.getIdPatient() + "'");

            while (pConnection.rs.next()) {
                p.addRiskFactor(new RiskFactor(
                        new SimpleStringProperty(pConnection.rs.getString("RF.name")),
                        new SimpleStringProperty(pConnection.rs.getString("RF.description")),
                        new SimpleStringProperty(pConnection.rs.getString("RF.risklevel"))
                ));
            }
        }

        pConnection.closeConnection();
        return patients;
    }

    @Override
    public List<Report> getPatientReports(String username, String idPatient) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Report> reports = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT R.id, R.reportdate, R.reactiondate, R.reaction, R.idpatient, R.doctor" +
                "FROM Report R WHERE R.idpatient = '" + idPatient +"' AND R.doctor = '" + username + "'");

        while (pConnection.rs.next()) {
            List<Vaccination> listaVuota = new ArrayList<>();
            reports.add(new Report(
                    new SimpleStringProperty(pConnection.rs.getString("R.id")),
                    new SimpleObjectProperty(pConnection.rs.getString("R.idpatient")),
                    new SimpleObjectProperty(pConnection.rs.getString("R.reaction")),
                    new SimpleStringProperty(pConnection.rs.getString("R.reportdate")),
                    new SimpleStringProperty(pConnection.rs.getString("R.reactiondate")),
                    listaVuota,
                    new SimpleStringProperty(pConnection.rs.getString("R.doctor"))
            ));
        }

        for (Report r : reports){
            pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate" +
                    "FROM Vaccination V JOIN Report R ON R.idpatient = V.idpatient " +
                    "WHERE R.idpatient = '" + idPatient + "' AND V.vaccinationdate BETWEEN R.reactiondate - 60 AND R.reactiondate");

            while (pConnection.rs.next()) {
                r.addVaccination(new Vaccination(
                        new SimpleObjectProperty(pConnection.rs.getString("V.idpatient")),
                        new SimpleStringProperty(pConnection.rs.getString("V.vaccine")),
                        new SimpleStringProperty(pConnection.rs.getString("V.typesomministration")),
                        new SimpleStringProperty(pConnection.rs.getString("V.vaccinationsite")),
                        new SimpleStringProperty(pConnection.rs.getString("V.vaccinationdate"))
                ));
            }
        }

        pConnection.closeConnection();
        return reports;
    }

    @Override
    public Patient getPatient(String idPatient) throws SQLException {

        return null;
    }

    @Override
    public void createPatient(String idPatient, String birthYear, String province, String profession, List<RiskFactor> risk_factor) {

    }

    @Override
    public int reactionsNumber(String idPatient) {
        return 0;
    }

    @Override
    public int vaccinationsNumber(String idPatient) {
        return 0;
    }
}
