package Model.Utils.DAO;
import Model.Reaction;

import java.sql.SQLException;

public interface ReactionDAO {
    void createReaction(String name, int gravity, String description) throws SQLException;
    Reaction getReaction(String name) throws SQLException;
}
