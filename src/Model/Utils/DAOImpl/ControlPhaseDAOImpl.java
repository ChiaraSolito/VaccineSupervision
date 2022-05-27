package Model.Utils.DAOImpl;

import Model.ControlPhase;
import Model.DataBase.DataBaseConnection;
import Model.Utils.DAO.ControlPhaseDAO;
import Model.Vaccination;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControlPhaseDAOImpl implements ControlPhaseDAO {

    DataBaseConnection pConnection;

    @Override
    public List<ControlPhase> getAllControls() throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<ControlPhase> controls = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT C.pharmacologist, C.vaccine, C.date " +
                "FROM controlphase C ");

        while (pConnection.rs.next()) {
            controls.add(new ControlPhase(
                    new SimpleStringProperty(pConnection.rs.getString("C.pharmacologist")),
                    new SimpleStringProperty(pConnection.rs.getString("C.vaccine")),
                    new SimpleStringProperty(pConnection.rs.getString("C.date"))
            ));
        }

        pConnection.closeConnection();
        return controls;
    }

    @Override
    public List<ControlPhase> getControls(String vaccine) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<ControlPhase> controls = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT C.pharmacologist, C.vaccine, C.date " +
                "FROM controlphase C " +
                "WHERE C.vaccine = '" + vaccine + "'");

        while (pConnection.rs.next()) {
            controls.add(new ControlPhase(
                    new SimpleStringProperty(pConnection.rs.getString("C.pharmacologist")),
                    new SimpleStringProperty(pConnection.rs.getString("C.vaccine")),
                    new SimpleStringProperty(pConnection.rs.getString("C.date"))
            ));
        }

        pConnection.closeConnection();
        return controls;
    }

    @Override
    public void proposeControlPhase(Date date, String vaccine, String pharmacologist) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO controlphase " +
                "VALUES('" + vaccine + "', '" + pharmacologist + "', '" + date + "')"
        );

        pConnection.closeConnection();

    }
}
