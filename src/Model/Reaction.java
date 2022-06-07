package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reaction {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty gravity;
    private final SimpleStringProperty description;

    public Reaction() {
        this.name = new SimpleStringProperty("");
        this.gravity = new SimpleIntegerProperty();
        this.description = new SimpleStringProperty("");
    }

    public Reaction(SimpleStringProperty name, SimpleIntegerProperty gravity, SimpleStringProperty description) {
        this.name = name;
        this.gravity = gravity;
        this.description = description;
    }

    public String getName() { return name.get(); }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getGravity() {
        return gravity.get();
    }

    public IntegerProperty gravityProperty() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity.set(gravity);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
