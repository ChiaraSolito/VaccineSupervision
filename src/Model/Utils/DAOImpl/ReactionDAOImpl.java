package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Reaction;
import Model.Utils.DAO.ReactionDAO;
import Model.Utils.Exceptions.NullStringException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        pConnection.statement.executeUpdate("INSERT INTO Reaction " +
                "VALUES('" + name + "', '" + gravity + "', '" + description + "')");

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
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
                reaction.setGravity(pConnection.rs.getInt("gravity"));
                reaction.setDescription(pConnection.rs.getString("description"));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return reaction;
    }

    @Override
    public List<String> getAllReactions() {
        List<String> reactions = new ArrayList<>();
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT R.name " +
                    "FROM Reaction R");

            while (pConnection.rs.next()) {
                reactions.add(pConnection.rs.getString("name"));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return reactions;
    }


}
