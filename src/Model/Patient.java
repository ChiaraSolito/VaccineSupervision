package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private SimpleStringProperty idPatient;
    private SimpleStringProperty birthYear;
    private SimpleStringProperty province;
    private SimpleStringProperty profession;

    private List<RiskFactor> risk_factor;

    public Patient(SimpleStringProperty birthYear, SimpleStringProperty province, SimpleStringProperty profession, List<RiskFactor> risk_factor) {
        this.idPatient = new SimpleStringProperty("");
        this.birthYear = birthYear;
        this.province = province;
        this.profession = profession;
        this.risk_factor = risk_factor;
    }

    public Patient() {
        this.idPatient = new SimpleStringProperty("");
        this.birthYear = new SimpleStringProperty("");
        this.province = new SimpleStringProperty("");
        this.profession = new SimpleStringProperty("");
        this.risk_factor = new ArrayList<>();
    }

    public String getIdPatient() {
        return idPatient.get();
    }

    public SimpleStringProperty idPatientProperty() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient.set(idPatient);
    }

    public String getBirthYear() {
        return birthYear.get();
    }

    public SimpleStringProperty birthYearProperty() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear.set(birthYear);
    }

    public String getProvince() {
        return province.get();
    }

    public SimpleStringProperty provinceProperty() {
        return province;
    }

    public void setProvince(String province) {
        this.province.set(province);
    }

    public String getProfession() {
        return profession.get();
    }

    public SimpleStringProperty professionProperty() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession.set(profession);
    }

    public List<RiskFactor> getAllRiskFactor() {
        return risk_factor;
    }

    public void addRiskFactor(RiskFactor newRiskFactor){
        this.risk_factor.add(newRiskFactor);
    }
}
