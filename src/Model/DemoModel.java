package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

public class DemoModel {

    private StringProperty user = new SimpleStringProperty("");

    private StringProperty password = new SimpleStringProperty("");

    private BooleanProperty Medico = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    private BooleanProperty Farmacologo = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };


    public BooleanProperty medicoProperty(){
        return Medico;
    }

    public BooleanProperty farmacologoProperty(){
        return Farmacologo;
    }

    public void setFarmacologo(boolean b){
        Farmacologo.set(b);
    }

    public void setMedico(boolean b){
        Medico.set(b);
    }

    public boolean getFarmacologo(){
        return Farmacologo.get();
    }

    public boolean getMedico(){
        return Medico.get();
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
