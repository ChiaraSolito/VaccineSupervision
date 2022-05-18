package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ControlPhase {
    private final SimpleStringProperty reportDate;
    private final SimpleStringProperty vaccine;
    private final SimpleStringProperty pharmacologist;

    public ControlPhase(SimpleStringProperty reportDate, SimpleStringProperty vaccine, SimpleStringProperty pharmacologist) {
        this.reportDate = reportDate;
        this.vaccine = vaccine;
        this.pharmacologist = pharmacologist;
    }

    public String getReportDate() {
        return reportDate.get();
    }

    public SimpleStringProperty reportDateProperty() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate.set(reportDate);
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
