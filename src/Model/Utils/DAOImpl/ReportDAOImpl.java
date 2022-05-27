package Model.Utils.DAOImpl;

import Model.ControlPhase;
import Model.DataBase.DataBaseConnection;
import Model.Report;
import Model.Utils.DAO.ReportDAO;
import Model.Vaccination;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {
    DataBaseConnection pConnection;

    @Override
    public List<Report> getAllReports(String username) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Report> reports = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT R.id, R.reportdate, R.reactiondate, R.reaction, R.idpatient, R.doctor " +
                "FROM report R ");

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

        for (Report r : reports) {
            pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                    "FROM Vaccination V JOIN Report R ON R.idpatient = V.idpatient " +
                    "WHERE R.idpatient = '" + r.getPatient() + "' AND V.vaccinationdate BETWEEN R.reactiondate - 60 AND R.reactiondate");

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
    public void createReport(String idPatient, String reactionName, Date reactionDate, String vaccination, SimpleStringProperty doctor) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO report " +
                "VALUES( DEFAULT , CURRENT_DATE, '" + reactionDate + "', '" + reactionName + "', '" + idPatient + "', '" + doctor + "')"
        );

        pConnection.closeConnection();
    }
}
