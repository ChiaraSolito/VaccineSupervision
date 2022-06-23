package Model.Utils.DAO;

import Model.Utils.Exceptions.NullStringException;

public interface ControlPhaseDAO {
    int getTotalNumberControlPhase(String vaccine) throws NullStringException;
    int getSixMonthsNumberControlPhase(String vaccine) throws NullStringException;
    void proposeControlPhase(String vaccine, String pharmacologist) throws NullStringException;
}
