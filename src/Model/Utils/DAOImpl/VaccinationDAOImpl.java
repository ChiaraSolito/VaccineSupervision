package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Utils.DAO.VaccinationDAO;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccinationDAOImpl implements VaccinationDAO {

    DataBaseConnection pConnection;

    @Override
    public void createVaccination(String idPatient, String vaccine, String typeSomministration, String vaccinationSite, String vaccinationDate) throws NullStringException {

        if (idPatient.isEmpty() || vaccine.isEmpty() || typeSomministration.isEmpty() || vaccinationSite.isEmpty() || vaccinationDate.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.statement.executeUpdate("INSERT INTO vaccination " +
                    "VALUES('" + idPatient + "', '" + vaccine + "', '" + typeSomministration + "', '" + vaccinationSite + "', ' " + vaccinationDate + " ' )"
            );
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }
    }

    @Override
    public List<Vaccination> getAllVaccination(String idPatient) throws NullStringException {

        List<Vaccination> vaccinations = new ArrayList<>();

        if (idPatient.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try{
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                    "FROM vaccination V " +
                    "WHERE V.idpatient = '" + idPatient + "'");

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
    public List<Vaccination> getTwoMonthsVaccination(String idPatient, String reactionDate) throws NullStringException {

        List<Vaccination> vaccinations = new ArrayList<>();

        if (idPatient.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {

            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                    "FROM vaccination V JOIN Report R ON R.idpatient = V.idpatient " +
                    "WHERE V.idpatient = '" + idPatient + "' " +
                    "AND V.vaccinationdate BETWEEN date '" + reactionDate + "' - 60 AND date '" + reactionDate + "'");

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

    public List<String> getAllVaccines() {

        List<String> vaccines = new ArrayList<>();

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {

            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT vaccine " +
                    "FROM vaccination ");
            while (pConnection.rs.next()) {
                vaccines.add(pConnection.rs.getString("vaccine"));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return vaccines;
    }

    public List<String> getAllCovidVaccines() {

        List<String> vaccines = new ArrayList<>();

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {

            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT vaccine " +
                    "FROM vaccination " +
                    "WHERE vaccine NOT LIKE '%Antinfluenzale%'");
            while (pConnection.rs.next()) {
                vaccines.add(pConnection.rs.getString("vaccine"));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return vaccines;
    }
}
