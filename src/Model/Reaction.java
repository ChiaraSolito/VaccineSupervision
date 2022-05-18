package Model;

import javafx.beans.property.SimpleStringProperty;

public class Reaction {

    private final SimpleStringProperty name;
    private final SimpleStringProperty gravity;
    private final SimpleStringProperty description;

    public Reaction(SimpleStringProperty name, SimpleStringProperty gravity, SimpleStringProperty description) {
        this.name = name;
        this.gravity = gravity;
        this.description = description;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGravity() {
        return gravity.get();
    }

    public SimpleStringProperty gravityProperty() {
        return gravity;
    }

    public void setGravity(String gravity) {
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
