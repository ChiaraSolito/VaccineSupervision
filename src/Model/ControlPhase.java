package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ControlPhase {
    private final SimpleStringProperty date;
    private final SimpleStringProperty vaccine;
    private final SimpleStringProperty pharmacologist;

    public ControlPhase(SimpleStringProperty reportDate, SimpleStringProperty vaccine, SimpleStringProperty pharmacologist) {
        this.date = reportDate;
        this.vaccine = vaccine;
        this.pharmacologist = pharmacologist;
    }

    public ControlPhase(){
        this.date = new SimpleStringProperty("");
        this.vaccine =  new SimpleStringProperty("");
        this.pharmacologist =  new SimpleStringProperty("");
    }

    public String getReportDate() {
        return date.get();
    }

    public SimpleStringProperty reportDateProperty() {
        return date;
    }

    public void setReportDate(String reportDate) {
        this.date.set(reportDate);
    }

    public String getVaccine() {
        return vaccine.get();
    }

    public SimpleStringProperty vaccineProperty() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine.set(vaccine);
    }

    public String getPharmacologist() {
        return pharmacologist.get();
    }

    public SimpleStringProperty pharmacologistProperty() {
        return pharmacologist;
    }

    public void setPharmacologist(String pharmacologist) {
        this.pharmacologist.set(pharmacologist);
    }
}
