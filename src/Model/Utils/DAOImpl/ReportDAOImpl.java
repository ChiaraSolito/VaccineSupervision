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
import java.util.List;

public class ReportDAOImpl implements ReportDAO {
    DataBaseConnection pConnection;

    @Override
    public List<Report> getAllReports() throws NullStringException {

        List<Report> reports = new ArrayList<>();

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
                        new SimpleStringProperty(pConnection.rs.getString("idpatient")),
                        new SimpleStringProperty(pConnection.rs.getString("reaction")),
                        new SimpleStringProperty(pConnection.rs.getString("reportdate")),
                        new SimpleStringProperty(pConnection.rs.getString("reactiondate")),
                        listaVuota,
                        new SimpleStringProperty(pConnection.rs.getString("doctor"))
                ));
            }

            for (Report r : reports) {
                String id = r.getPatient();
                pConnection.rs = pConnection.statement.executeQuery("SELECT V.idpatient, V.vaccine, V.typesomministration, V.vaccinationsite, V.vaccinationdate " +
                        "FROM Vaccination V JOIN Report R ON R.idpatient = V.idpatient " +
                        "WHERE R.idpatient = '" + id + "' AND V.vaccinationdate BETWEEN R.reactiondate - 60 AND R.reactiondate");

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
    public void createReport(String idPatient, String reactionName, String reactionDate, String doctor) throws NullStringException {

        if (idPatient.isEmpty() || reactionName.isEmpty() || reactionDate.isEmpty() || doctor.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();
        int idReport = 0;

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT max(id) FROM report");
            while (pConnection.rs.next()) {
                idReport = pConnection.rs.getInt("max") + 1;
            }
            pConnection.statement.executeUpdate("INSERT INTO report " +
                    "VALUES(" + idReport + " , DEFAULT, '" + reactionDate + "', '" + reactionName + "', '" + idPatient + "', '" + doctor + "')"
            );
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

    }

    public int getReportNumber() {
        int reportNumber = 0;

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT COUNT(DISTINCT id) FROM report " +
                    "WHERE reportdate >= CURRENT_DATE - 7");
            while (pConnection.rs.next()) {
                reportNumber = pConnection.rs.getInt("count");
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return reportNumber;
    }

    public List<String> countSevereReaction() {
        List<String> vaccines = new ArrayList<>();
        int countV = 0;

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT V.vaccine, COUNT(V.vaccine) FROM vaccination V " +
                    "JOIN patient P ON P.idpatient = V.idpatient " +
                    "JOIN report R ON R.idpatient = V.idpatient " +
                    "JOIN reaction RE ON R.reaction = RE.name " +
                    "WHERE V.vaccine <> 'Antinfluenzale%' " +
                    "AND R.reportdate > current_date - 7 " +
                    "AND V.vaccinationdate > current_date - 60 " +
                    "AND RE.gravity > 3 " +
                    "GROUP BY V.vaccine"
            );
            while (pConnection.rs.next()) {
                countV = pConnection.rs.getInt("count");
                if (countV > 5) {
                    vaccines.add(pConnection.rs.getString("vaccine"));
                }
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

