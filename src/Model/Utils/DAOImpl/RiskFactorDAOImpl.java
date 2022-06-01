package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.RiskFactor;
import Model.Utils.DAO.RiskFactorDAO;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiskFactorDAOImpl implements RiskFactorDAO {
    DataBaseConnection pConnection;

    @Override
    public List<RiskFactor> getAllRiskFactors(String idPatient) throws NullStringException {

        List<RiskFactor> risks = new ArrayList<>();

        if (idPatient.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT RF.name, RF.risklevel, RF.description " +
                    "FROM RiskFactor RF JOIN PatientRisk PR ON PR.risk = RF.name " +
                    "WHERE PR.idpatient = '" + idPatient + "'");

            while (pConnection.rs.next()) {
                risks.add(new RiskFactor(
                        new SimpleStringProperty(pConnection.rs.getString("name")),
                        new SimpleStringProperty(pConnection.rs.getString("description")),
                        new SimpleStringProperty(pConnection.rs.getString("risklevel"))
                ));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
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
            pConnection.rs = pConnection.statement.executeQuery("INSERT INTO RiskFactor " +
                    "VALUES('" + name + "', '" + description + "', '" + riskLevel + "')");
        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle.getMessage());
        } finally {
            pConnection.closeConnection();
        }

    }
}
