package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.RiskFactor;
import Model.Utils.DAO.RiskFactorDAO;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiskFactorDAOImpl implements RiskFactorDAO {
    DataBaseConnection pConnection;

    public List<String> getAllExistingRisks() {

        List<String> risks = new ArrayList<>();


        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT RF.name, RF.risklevel, RF.description " +
                    "FROM RiskFactor RF");

            while (pConnection.rs.next()) {
                risks.add(pConnection.rs.getString("name"));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return risks;
    }


    @Override
    public void createRiskFactor(String name, String description, int riskLevel) throws NullStringException {

        if (name.isEmpty() || description.isEmpty() || riskLevel == 0) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.statement.executeUpdate("INSERT INTO RiskFactor " +
                    "VALUES('" + name + "', '" + riskLevel + "', '" + description + "')");
        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

    }

    public RiskFactor getRisk(String name) throws NullStringException {

        RiskFactor risk = null;

        if (name.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT RF.name, RF.risklevel, RF.description " +
                    "FROM RiskFactor RF " +
                    "WHERE RF.name = '" + name + "'");

            while (pConnection.rs.next()) {
                risk = new RiskFactor(
                        new SimpleStringProperty(pConnection.rs.getString("name")),
                        new SimpleStringProperty(pConnection.rs.getString("description")),
                        new SimpleIntegerProperty(pConnection.rs.getInt("risklevel"))
                );
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return risk;
    }

}
