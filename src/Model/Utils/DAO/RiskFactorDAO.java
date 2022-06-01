package Model.Utils.DAO;

import Model.RiskFactor;
import Model.Utils.Exceptions.NullStringException;

import java.sql.SQLException;
import java.util.List;

public interface RiskFactorDAO {
    List<RiskFactor> getAllRiskFactors(String idPatient) throws NullStringException;
    void createRiskFactor(String name, String description, int riskLevel) throws NullStringException;
}
