package Model.Utils.DAO;

import Model.ControlPhase;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ControlPhaseDAO {
    List<ControlPhase> getAllControls();
    List<ControlPhase> getControls(String vaccine) throws NullStringException;
    void proposeControlPhase(Date date, String vaccine, String pharmacologist) throws NullStringException;
}
