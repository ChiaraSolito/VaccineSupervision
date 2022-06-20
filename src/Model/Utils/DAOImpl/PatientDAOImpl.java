package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Patient;
import Model.Report;
import Model.RiskFactor;
import Model.Utils.DAO.PatientDAO;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    DataBaseConnection pConnection;

    @Override
    public List<String> getAllPatients(String username) throws NullStringException {

        if (username.isEmpty()) {
            throw new NullStringException();
        }

        List<String> patients = new ArrayList<>();
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT R.idpatient " +
                    "FROM Report R WHERE R.doctor = '" + username + "'");

           while(pConnection.rs.next()){
                patients.add(pConnection.rs.getString("idpatient"));
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return patients;
    }

    @Override
    public List<Report> getPatientReports(String username, String idPatient) throws NullStringException {

        if (username.isEmpty() || idPatient.isEmpty()) {
            throw new NullStringException();
        }

        List<Report> reports = new ArrayList<>();
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT R.id, R.reportdate, R.reactiondate, R.reaction, R.idpatient, R.doctor " +
                    "FROM Report R WHERE R.idpatient = '" + idPatient +"' AND R.doctor = '" + username + "'");

            while (pConnection.rs.next()) {
                List<Vaccination> listaVuota = new ArrayList<>();
                reports.add(new Report(
                        new SimpleStringProperty(pConnection.rs.getString("id")),
                        new SimpleStringProperty(pConnection.rs.getString("idpatient")),
                        new SimpleStringProperty(pConnection.rs.getString("reaction")),
                        new SimpleStringProperty(pConnection.rs.getString("reportdate")),
                        new SimpleStringProperty(pConnection.rs.getString("reactiondate")),
                        listaVuota,
                        new SimpleStringProperty(pConnection.rs.getString("doctor"))
                ));
            }

            for (Report r : reports){
                pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                        "FROM Vaccination V JOIN Report R ON R.idpatient = V.idpatient " +
                        "WHERE R.idpatient = '" + idPatient + "' AND V.vaccinationdate BETWEEN R.reactiondate - 60 AND R.reactiondate");

                while (pConnection.rs.next()) {
                    r.addVaccination(new Vaccination(
                            new SimpleObjectProperty(pConnection.rs.getString("idpatient")),
                            new SimpleStringProperty(pConnection.rs.getString("vaccine")),
                            new SimpleStringProperty(pConnection.rs.getString("typesomministration")),
                            new SimpleStringProperty(pConnection.rs.getString("vaccinationsite")),
                            new SimpleStringProperty(pConnection.rs.getString("vaccinationdate"))
                    ));
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return reports;
    }

    @Override
    public Patient getPatient(String idPatient) throws NullStringException {

        if (idPatient.isEmpty()) {
            throw new NullStringException();
        }

        Patient p = new Patient();
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT P.idpatient, P.birthyear, P.province, P.profession " +
                    "FROM Patient P WHERE P.idpatient = '" + idPatient + "'");

            while(pConnection.rs.next()){
                p.setIdPatient(pConnection.rs.getString("idpatient"));
                p.setBirthYear(pConnection.rs.getString("birthyear"));
                p.setProvince(pConnection.rs.getString("province"));
                p.setProfession(pConnection.rs.getString("profession"));
            }

            pConnection.rs = pConnection.statement.executeQuery("SELECT RF.name, RF.risklevel, RF.description " +
                    "FROM RiskFactor RF JOIN PatientRisk PR ON PR.risk = RF.name " +
                    "WHERE PR.idpatient = '" + idPatient + "'");

            while (pConnection.rs.next()) {
                p.addRiskFactor(new RiskFactor(
                        new SimpleStringProperty(pConnection.rs.getString("name")),
                        new SimpleStringProperty(pConnection.rs.getString("description")),
                        new SimpleIntegerProperty(pConnection.rs.getInt("risklevel"))
                ));
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return p;
    }

    @Override
    public List<Vaccination> getPatientVaccinations(String idPatient) throws NullStringException {

        if (idPatient.isEmpty()){
            throw new NullStringException();
        }

        List<Vaccination> vaccinations = new ArrayList<>();
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                    "FROM Vaccination V WHERE V.idpatient = '" + idPatient + "'");

            while (pConnection.rs.next()) {
                vaccinations.add(new Vaccination(
                        new SimpleObjectProperty(pConnection.rs.getString("idpatient")),
                        new SimpleStringProperty(pConnection.rs.getString("vaccine")),
                        new SimpleStringProperty(pConnection.rs.getString("typesomministration")),
                        new SimpleStringProperty(pConnection.rs.getString("vaccinationsite")),
                        new SimpleStringProperty(pConnection.rs.getString("vaccinationdate"))
                ));
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return vaccinations;
    }

    @Override
    public String createPatient(String birthYear, String province, String profession, List<RiskFactor> risk_factor) throws NullStringException {

        if (birthYear.isEmpty() || province.isEmpty() || profession.isEmpty() || risk_factor.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();
        String idCurrent = "";

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.statement.executeUpdate("INSERT INTO Patient " +
                    "VALUES( DEFAULT , '" + birthYear + "', '" + province + "', '" + profession + "')");

            pConnection.rs = pConnection.statement.executeQuery("SELECT MAX(P.idpatient) " +
                    "FROM patient P");

            while (pConnection.rs.next()) {
                idCurrent = pConnection.rs.getString("max");
            }

            for (RiskFactor risk : risk_factor) {
                pConnection.rs = pConnection.statement.executeQuery("SELECT true WHERE EXISTS " +
                        "(SELECT name FROM RiskFactor WHERE name = '" + risk.getName() + "')");
                if (pConnection.rs.next()) {
                    pConnection.statement.executeUpdate("INSERT INTO PatientRisk " +
                            "VALUES('" + idCurrent + "', '" + risk.getName() + "')");
                } else {
                    pConnection.statement.executeUpdate("INSERT INTO RiskFactor " +
                            "VALUES('" + risk.getName() + "', '" + risk.getRiskLevel() + "', " + risk.getDescription() + "')");
                    pConnection.statement.executeUpdate("INSERT INTO PatientRisk " +
                            "VALUES('" + idCurrent + "', '" + risk.getName() + "')");
                }
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }
        return idCurrent;
    }

    @Override
    public int reactionsNumber(String idPatient) throws NullStringException {

        if (idPatient.isEmpty()){
            throw new NullStringException();
        }

        int reactionNumber = 0;
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT COUNT(id) AS count FROM Report " +
                    "WHERE idpatient = '" + idPatient + "'");

            while(pConnection.rs.next()){
                reactionNumber = pConnection.rs.getInt("count");
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return reactionNumber;
    }

    @Override
    public int vaccinationsNumber(String idPatient) throws NullStringException {

        if (idPatient.isEmpty()){
            throw new NullStringException();
        }

        int vaccinationNumber = 0;
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT COUNT(vaccine) AS count FROM Vaccination " +
                    "WHERE idpatient = '" + idPatient + "'");

            while(pConnection.rs.next()){
                vaccinationNumber = pConnection.rs.getInt("count");
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return vaccinationNumber;
    }
}
