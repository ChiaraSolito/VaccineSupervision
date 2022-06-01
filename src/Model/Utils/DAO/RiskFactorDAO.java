package Model.Utils.DAO;

import Model.RiskFactor;
import Model.Utils.Exceptions.NullStringException;

import java.util.List;

public interface RiskFactorDAO {
    List<RiskFactor> getAllRiskFactors(String idPatient) throws NullStringException;

    List<String> getAllExistingRisks();

    void createRiskFactor(String name, String description, int riskLevel) throws NullStringException;
}
