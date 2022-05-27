package Model.Utils.DAO;

import Model.RiskFactor;

import java.sql.SQLException;
import java.util.List;

public interface RiskFactorDAO {
    List<RiskFactor> getAllRiskFactors(String idPatient) throws SQLException;
    void createRiskFactor(String name, String description, int riskLevel) throws SQLException;
}
