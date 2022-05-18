package Model.Utils.DAO;
import Model.Reaction;

public interface ReactionDAO {
    void createReaction(String name, int gravity, String description);
    Reaction getReaction(String name);
}
