package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Utils.DAO.ControlPhaseDAO;
import Model.Utils.Exceptions.NullStringException;
import View.Utils.Alerts;

import java.sql.SQLException;

public class ControlPhaseDAOImpl implements ControlPhaseDAO {

    DataBaseConnection pConnection;

    @Override
    public int getTotalNumberControlPhase(String vaccine) throws NullStringException {
        if (vaccine.isEmpty()){
            throw new NullStringException();
        }

        int totalNumber = 0;
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT COUNT(vaccine) AS count FROM ControlPhase " +
                    "WHERE vaccine = '" + vaccine + "'");

            while(pConnection.rs.next()){
                totalNumber = pConnection.rs.getInt("count");
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return totalNumber;
    }

    @Override
    public int getSixMonthsNumberControlPhase(String vaccine) throws NullStringException {
        if (vaccine.isEmpty()){
            throw new NullStringException();
        }

        int sixMonthsNumber = 0;
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT COUNT(vaccine) AS count FROM ControlPhase " +
                    "WHERE vaccine = '" + vaccine + "' AND date BETWEEN CURRENT_DATE - 60 AND CURRENT_DATE");

            while(pConnection.rs.next()){
                sixMonthsNumber = pConnection.rs.getInt("count");
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return sixMonthsNumber;
    }

    @Override
    public void proposeControlPhase(String vaccine, String pharmacologist) throws NullStringException {

        if (vaccine.isEmpty() || pharmacologist.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.statement.executeUpdate("INSERT INTO controlphase " +
                    "VALUES('" + pharmacologist + "', '" + vaccine + "', CURRENT_DATE)"
            );
        } catch (SQLException sqle) {
            Alerts.displayCPhaseError();
            System.out.println("Error: " + sqle.getMessage());
        } finally {
            pConnection.closeConnection();
        }

    }
}
