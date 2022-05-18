package Model.Utils.DAO;

import Model.ControlPhase;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;
import java.util.List;

public interface ControlPhaseDAO {
    List<ControlPhase> getAllControls();
    List<ControlPhase> getControls(String vaccine);
    void proposeControlPhase(Date reportDate, String vaccine, String pharmacologist);
}
