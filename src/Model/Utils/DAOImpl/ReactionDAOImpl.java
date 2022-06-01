package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Patient;
import Model.Reaction;
import Model.RiskFactor;
import Model.Utils.DAO.ReactionDAO;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class ReactionDAOImpl implements ReactionDAO {
    DataBaseConnection pConnection;

    @Override
    public void createReaction(String name, int gravity, String description) throws NullStringException {

        if (name.isEmpty() || gravity == 0 || description.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO Reaction " +
                "VALUES('" + name + "', '" + gravity + "', '" + description + "')");

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        } finally {
            pConnection.closeConnection();
        }
    }

    @Override
    public Reaction getReaction(String name) throws NullStringException {

        if (name.isEmpty()) {
            throw new NullStringException();
        }

        Reaction reaction = new Reaction();
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT R.name, R.gravity, R.description " +
                    "FROM Reaction R WHERE R.name = '" + name + "'");

            while(pConnection.rs.next()){
                reaction.setName(pConnection.rs.getString("name"));
                reaction.setGravity(pConnection.rs.getString("gravity"));
                reaction.setDescription(pConnection.rs.getString("description"));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        } finally {
            pConnection.closeConnection();
        }

        return reaction;
    }
}
