package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Report;
import Model.Utils.DAO.ReportDAO;
import Model.Utils.Exceptions.NullStringException;
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
    public List<Report> getAllReports(String username) throws NullStringException {

        List<Report> reports = new ArrayList<>();

        if(username.isEmpty()){
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {

            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT R.id, R.reportdate, R.reactiondate, R.reaction, R.idpatient, R.doctor " +
                    "FROM report R ");

            while (pConnection.rs.next()) {
                List<Vaccination> listaVuota = new ArrayList<>();
                reports.add(new Report(
                        new SimpleStringProperty(pConnection.rs.getString("id")),
                        new SimpleObjectProperty(pConnection.rs.getString("idpatient")),
                        new SimpleObjectProperty(pConnection.rs.getString("reaction")),
                        new SimpleStringProperty(pConnection.rs.getString("reportdate")),
                        new SimpleStringProperty(pConnection.rs.getString("reactiondate")),
                        listaVuota,
                        new SimpleStringProperty(pConnection.rs.getString("doctor"))
                ));
            }

            for (Report r : reports) {
                pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                        "FROM Vaccination V JOIN Report R ON R.idpatient = V.idpatient " +
                        "WHERE R.idpatient = '" + r.getPatient() + "' AND V.vaccinationdate BETWEEN R.reactiondate - 60 AND R.reactiondate");

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
        } catch (SQLException sqle){
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return reports;
    }

    @Override
    public void createReport(String idPatient, String reactionName, Date reactionDate, String vaccination, String doctor) throws NullStringException {

        if (idPatient.isEmpty() || reactionName.isEmpty() || vaccination.isEmpty() || doctor.isEmpty() ) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.statement.executeUpdate("INSERT INTO report " +
                    "VALUES( DEFAULT , CURRENT_DATE, '" + reactionDate + "', '" + reactionName + "', '" + idPatient + "', '" + doctor + "')"
            );
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

    }
}
