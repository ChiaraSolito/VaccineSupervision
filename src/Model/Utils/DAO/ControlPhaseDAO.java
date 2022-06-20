package Model.Utils.DAO;

import Model.ControlPhase;
import Model.Utils.Exceptions.NullStringException;

import java.util.List;

public interface ControlPhaseDAO {
    List<ControlPhase> getAllControls();
    List<ControlPhase> getControls(String vaccine) throws NullStringException;

    void proposeControlPhase(String vaccine, String pharmacologist) throws NullStringException;
}
