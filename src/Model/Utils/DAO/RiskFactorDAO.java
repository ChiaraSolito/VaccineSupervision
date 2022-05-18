package Model.Utils.DAO;

import Model.RiskFactor;
import java.util.List;

public interface RiskFactorDAO {
    List<RiskFactor> getAllRiskFactors(String idPatient);
    void createRiskFactor(String description, int riskLevel);
}
