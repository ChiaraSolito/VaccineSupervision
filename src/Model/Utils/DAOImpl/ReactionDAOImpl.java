package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Patient;
import Model.Reaction;
import Model.RiskFactor;
import Model.Utils.DAO.ReactionDAO;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class ReactionDAOImpl implements ReactionDAO {
    DataBaseConnection pConnection;

    @Override
    public void createReaction(String name, int gravity, String description) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO Reaction " +
                "VALUES('" + name + "', '" + gravity + "', " + description+ "'");

        pConnection.closeConnection();
    }

    @Override
    public Reaction getReaction(String name) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT R.name, R.gravity, R.description" +
                "FROM Reaction R WHERE R.name = '" + name + "'");

        Reaction reaction = new Reaction();

        while(pConnection.rs.next()){
            reaction.setName(pConnection.rs.getString("R.name"));
            reaction.setGravity(pConnection.rs.getString("R.gravity"));
            reaction.setDescription(pConnection.rs.getString("R.description"));
        }

        pConnection.closeConnection();
        return reaction;
    }
}
