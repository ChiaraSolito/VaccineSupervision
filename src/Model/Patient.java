package Model;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Patient {
    private final SimpleStringProperty idPatient;
    private final SimpleStringProperty birthYear;
    private final SimpleStringProperty province;
    private final SimpleStringProperty profession;

    private final List<RiskFactor> risk_factor;

    public Patient(SimpleStringProperty idPatient, SimpleStringProperty birthYear, SimpleStringProperty province, SimpleStringProperty profession, List<RiskFactor> risk_factor) {
        this.idPatient = idPatient;
        this.birthYear = birthYear;
        this.province = province;
        this.profession = profession;
        this.risk_factor = risk_factor;
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
