package Model.Utils.DAO;

import Model.Utils.Exceptions.NullStringException;

import java.util.List;

public interface RiskFactorDAO {

    List<String> getAllExistingRisks();

    void createRiskFactor(String name, String description, int riskLevel) throws NullStringException;
}
