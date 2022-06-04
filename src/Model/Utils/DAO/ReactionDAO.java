package Model.Utils.DAO;

import Model.Reaction;
import Model.Utils.Exceptions.NullStringException;

import java.util.List;

public interface ReactionDAO {
    void createReaction(String name, int gravity, String description) throws NullStringException;

    Reaction getReaction(String name) throws NullStringException;

    List<String> getAllReactions();
}
