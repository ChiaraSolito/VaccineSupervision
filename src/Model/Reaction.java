package Model;

public class Reaction {

    private final String name;
    private final int gravity;
    private final String description;

    public Reaction(String name, int gravity, String description) {
        this.name = name;
        this.gravity = gravity;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getGravity() {
        return gravity;
    }

    public String getDescription() {
        return description;
    }
}
