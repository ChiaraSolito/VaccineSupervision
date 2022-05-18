package Model;

import javafx.beans.property.SimpleStringProperty;

public class RiskFactor {

    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleStringProperty risk_level;

    public RiskFactor(SimpleStringProperty name, SimpleStringProperty description, SimpleStringProperty risk_level) {
        this.name = name;
        this.description = description;
        this.risk_level = risk_level;
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

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getRisk_level() {
        return risk_level.get();
    }

    public SimpleStringProperty risk_levelProperty() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level.set(risk_level);
    }
}
