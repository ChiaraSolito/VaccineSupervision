package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {
    private final SimpleStringProperty id;
    private final SimpleObjectProperty<Patient> patient;
    private final SimpleObjectProperty<Reaction> reaction;
    private final SimpleStringProperty reportDate;
    private final SimpleStringProperty reactionDate;
    private final SimpleObjectProperty<Vaccination> vaccination;
    private final SimpleStringProperty medico;

    public Report(SimpleStringProperty id, SimpleObjectProperty<Patient> patient, SimpleObjectProperty<Reaction> reaction,
                  SimpleStringProperty reportDate, SimpleStringProperty reactionDate,
                  SimpleObjectProperty<Vaccination> vaccination, SimpleStringProperty medico) {
        this.id = id;
        this.patient = patient;
        this.reaction = reaction;
        this.reportDate = reportDate;
        this.reactionDate = reactionDate;
        this.vaccination = vaccination;
        this.medico = medico;
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

    public Vaccination getVaccination() {
        return vaccination.get();
    }

    public SimpleObjectProperty<Vaccination> vaccinationProperty() {
        return vaccination;
    }

    public void setVaccination(Vaccination vaccination) {
        this.vaccination.set(vaccination);
    }

    public String getMedico() {
        return medico.get();
    }

    public SimpleStringProperty medicoProperty() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico.set(medico);
    }
}
