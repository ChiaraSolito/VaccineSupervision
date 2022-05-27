package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.RiskFactor;
import Model.Utils.DAO.VaccinationDAO;
import Model.Vaccination;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VaccinationDAOImpl implements VaccinationDAO {

    DataBaseConnection pConnection;

    @Override
    public void createVaccination(String idPatient, String vaccine, String typeSomministration, String vaccinationSite, Date vaccinationDate) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO vaccination " +
                        "VALUES('" + idPatient + "', '" + vaccine + "', '" + typeSomministration + "', '" + vaccinationSite + "', ' " + vaccinationDate +" ' )"
        );

        pConnection.closeConnection();
    }

    @Override
    public List<Vaccination> getAllVaccination(String idPatient) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Vaccination> vaccinations = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                "FROM vaccination V " +
                "WHERE V.idpatient = '" + idPatient + "'");

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
    public List<Vaccination> getTwoMonthsVaccination(String idPatient) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Vaccination> vaccinations = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                "FROM vaccination V " +
                "WHERE V.idpatient = '" + idPatient + "'" +
                "AND V.vaccinationdate >= CURRENT_DATE");

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
}
