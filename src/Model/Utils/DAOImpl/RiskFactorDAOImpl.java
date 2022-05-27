package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.RiskFactor;
import Model.Utils.DAO.RiskFactorDAO;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiskFactorDAOImpl implements RiskFactorDAO {
    DataBaseConnection pConnection;

    @Override
    public List<RiskFactor> getAllRiskFactors(String idPatient) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<RiskFactor> risks = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT RF.name, RF.risklevel, RF.description" +
                "FROM RiskFactor RF JOIN PatientRisk PR ON PR.risk = RF.name " +
                "WHERE PR.idpatient = '" + idPatient + "'");

        while (pConnection.rs.next()) {
            risks.add(new RiskFactor(
                    new SimpleStringProperty(pConnection.rs.getString("RF.name")),
                    new SimpleStringProperty(pConnection.rs.getString("RF.description")),
                    new SimpleStringProperty(pConnection.rs.getString("RF.risklevel"))
            ));
        }

        pConnection.closeConnection();
        return risks;
    }

    @Override
    public void createRiskFactor(String name, String description, int riskLevel) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO RiskFactor " +
                "VALUES('" + name + "', '" + description + "', " + riskLevel + "'");

        pConnection.closeConnection();
    }
}
