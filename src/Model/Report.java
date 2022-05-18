package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Report {
    private final SimpleStringProperty id;
    private final SimpleObjectProperty<Patient> patient;
    private final SimpleObjectProperty<Reaction> reaction;
    private final SimpleStringProperty reportDate;
    private final SimpleStringProperty reactionDate;
    private final List<Vaccination> vaccination;
    private final SimpleStringProperty doctor;

    public Report(SimpleStringProperty id, SimpleObjectProperty<Patient> patient, SimpleObjectProperty<Reaction> reaction,
                  SimpleStringProperty reportDate, SimpleStringProperty reactionDate,
                  List<Vaccination> vaccination, SimpleStringProperty doctor) {
        this.id = id;
        this.patient = patient;
        this.reaction = reaction;
        this.reportDate = reportDate;
        this.reactionDate = reactionDate;
        this.vaccination = vaccination;
        this.doctor = doctor;
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
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

    public Reaction getReaction() {
        return reaction.get();
    }

    public SimpleObjectProperty<Reaction> reactionProperty() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction.set(reaction);
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

    public String getReactionDate() {
        return reactionDate.get();
    }

    public SimpleStringProperty reactionDateProperty() {
        return reactionDate;
    }

    public void setReactionDate(String reactionDate) {
        this.reactionDate.set(reactionDate);
    }

    public List<Vaccination> vaccinationProperty() {
        return vaccination;
    }

    public void addVaccination(Vaccination vaccination) {
        this.vaccination.add(vaccination);
    }

    public String getDoctor() {
        return doctor.get();
    }

    public SimpleStringProperty doctorProperty() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }
}
