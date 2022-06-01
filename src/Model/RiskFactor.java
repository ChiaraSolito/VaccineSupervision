package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RiskFactor {

    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleIntegerProperty riskLevel;

    public RiskFactor(SimpleStringProperty name, SimpleStringProperty description, SimpleIntegerProperty riskLevel) {
        this.name = name;
        this.description = description;
        this.riskLevel = riskLevel;
    }

    public RiskFactor(){
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.riskLevel = new SimpleIntegerProperty();
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

    public int getRiskLevel() {
        return riskLevel.get();
    }

    public SimpleIntegerProperty riskLevelProperty() {
        return riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel.set(riskLevel);
    }
}
