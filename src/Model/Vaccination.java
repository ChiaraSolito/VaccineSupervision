package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Vaccination {
    private final SimpleObjectProperty<Patient> patient;
    private final SimpleStringProperty vaccine;
    private final SimpleStringProperty typeSomministration;
    private final SimpleStringProperty vaccinationSite;
    private final SimpleStringProperty vaccinationDate;

    public Vaccination(SimpleObjectProperty<Patient> patient, SimpleStringProperty vaccine,
                       SimpleStringProperty typeSomministration, SimpleStringProperty vaccinationSite,
                       SimpleStringProperty vaccinationDate) {
        this.patient = patient;
        this.vaccine = vaccine;
        this.typeSomministration = typeSomministration;
        this.vaccinationSite = vaccinationSite;
        this.vaccinationDate = vaccinationDate;
    }

    public Patient getPatient() {
        return patient.get();
    }

    public SimpleObjectProperty<Patient> patientProperty() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient.set(patient);
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

    public String getTypeSomministration() {
        return typeSomministration.get();
    }

    public SimpleStringProperty typeSomministrationProperty() {
        return typeSomministration;
    }

    public void setTypeSomministration(String typeSomministration) {
        this.typeSomministration.set(typeSomministration);
    }

    public String getVaccinationSite() {
        return vaccinationSite.get();
    }

    public SimpleStringProperty vaccinationSiteProperty() {
        return vaccinationSite;
    }

    public void setVaccinationSite(String vaccinationSite) {
        this.vaccinationSite.set(vaccinationSite);
    }

    public String getVaccinationDate() {
        return vaccinationDate.get();
    }

    public SimpleStringProperty vaccinationDateProperty() {
        return vaccinationDate;
    }

    public void setVaccinationDate(String vaccinationDate) {
        this.vaccinationDate.set(vaccinationDate);
    }
}
