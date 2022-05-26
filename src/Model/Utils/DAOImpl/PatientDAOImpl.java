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
    public List<String> getAllPatients(String username) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<String> patients = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT R.idpatient" +
                "FROM Report R WHERE R.doctor = '" + username + "'");

       while(pConnection.rs.next()){
            patients.add(pConnection.rs.getString("R.idpatient"));
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
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT P.idpatient, P.birthyear, P.province, P.profession" +
                "FROM Patient P WHERE P.idpatient = '" + idPatient + "'");

        Patient p = new Patient();
        while(pConnection.rs.next()){
            List<RiskFactor> listaVuota = new ArrayList<>();
            p.setIdPatient(pConnection.rs.getString("P.idpatient"));
            p.setBirthYear(pConnection.rs.getString("P.birthyear"));
            p.setProvince(pConnection.rs.getString("P.province"));
            p.setProfession(pConnection.rs.getString("P.profession"));
        }

        pConnection.rs = pConnection.statement.executeQuery("SELECT RF.name, RF.risklevel, RF.description" +
                "FROM RiskFactor RF JOIN PatientRisk PR ON PR.risk = RF.name " +
                "WHERE PR.idpatient = '" + idPatient + "'");

        while (pConnection.rs.next()) {
            p.addRiskFactor(new RiskFactor(
                    new SimpleStringProperty(pConnection.rs.getString("RF.name")),
                    new SimpleStringProperty(pConnection.rs.getString("RF.description")),
                    new SimpleStringProperty(pConnection.rs.getString("RF.risklevel"))
            ));
        }

        pConnection.closeConnection();
        return null;
    }

    @Override
    public List<Vaccination> getPatientVaccinations(String idPatient) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Vaccination> vaccinations = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate" +
                "FROM Vaccination V WHERE R.idpatient = '" + idPatient + "'");

        while (pConnection.rs.next()) {
            vaccinations.add(new Vaccination(
                    new SimpleObjectProperty(pConnection.rs.getString("V.idpatient")),
                    new SimpleStringProperty(pConnection.rs.getString("V.vaccine")),
                    new SimpleStringProperty(pConnection.rs.getString("V.typesomministration")),
                    new SimpleStringProperty(pConnection.rs.getString("V.vaccinationsite")),
                    new SimpleStringProperty(pConnection.rs.getString("V.vaccinationdate"))
            ));
        }

        pConnection.closeConnection();
        return vaccinations;
    }

    @Override
    public void createPatient(String idPatient, String birthYear, String province, String profession, List<RiskFactor> risk_factor) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO Patient " +
                "VALUES('" + idPatient + "', '" + birthYear + "', " + province + "', " + profession + "'");

        for (RiskFactor risk : risk_factor)  {
            pConnection.rs = pConnection.statement.executeQuery("SELECT true WHERE EXISTS " +
                    "(SELECT name FROM RiskFactor WHERE name = '" + risk.getName() + "')");
            if(pConnection.rs.next()) {
                pConnection.rs = pConnection.statement.executeQuery("INSERT INTO PatientRisk " +
                        "VALUES('" + idPatient + "', '" + risk.getName() + "'");
            } else {
                pConnection.rs = pConnection.statement.executeQuery("INSERT INTO RiskFactor " +
                        "VALUES('" + risk.getName() + "', '" + risk.getRiskLevel() + "', " + risk.getDescription() + "'");
                pConnection.rs = pConnection.statement.executeQuery("INSERT INTO PatientRisk " +
                        "VALUES('" + idPatient + "', '" + risk.getName() + "'");
            }
        }

        pConnection.closeConnection();
    }

    @Override
    public int reactionsNumber(String idPatient) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        int reactionNumber = 0;
        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT COUNT id AS count FROM Report " +
                "WHERE idpatient = '" + idPatient + "'");

        while(pConnection.rs.next()){
            reactionNumber = pConnection.rs.getInt("count");
        }

        pConnection.closeConnection();
        return reactionNumber;
    }

    @Override
    public int vaccinationsNumber(String idPatient) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        int vaccinationNumber = 0;
        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT COUNT vaccine AS count FROM Vaccination " +
                "WHERE idpatient = '" + idPatient + "'");

        while(pConnection.rs.next()){
            vaccinationNumber = pConnection.rs.getInt("count");
        }

        pConnection.closeConnection();
        return vaccinationNumber;
    }
}
