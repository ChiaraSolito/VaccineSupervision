package Model.Utils.DAO;

import Model.ControlPhase;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ControlPhaseDAO {
    List<ControlPhase> getAllControls() throws SQLException;
    List<ControlPhase> getControls(String vaccine) throws SQLException;
    void proposeControlPhase(Date date, String vaccine, String pharmacologist) throws SQLException;
}
