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

        while(pConnection.rs.next()){
            Reaction reaction = new Reaction(
                new SimpleStringProperty(pConnection.rs.getString("R.name")),
                new SimpleStringProperty(pConnection.rs.getString("P.birthyear")),
                new SimpleStringProperty(pConnection.rs.getString("P.province"))
            );
        }

        pConnection.closeConnection();
        //sarebbe da ritornare reaction...fare costruttore vuoto così dichiariamo la variabile fuori dal while
        //e poi così ritorniamo reaction
        return null;
    }
}
