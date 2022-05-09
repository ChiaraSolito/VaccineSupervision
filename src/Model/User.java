package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    //Stringa per lo username
    private StringProperty username = new SimpleStringProperty("");

    //Stringa per la password
    private StringProperty password = new SimpleStringProperty("");

    //Booleano che mi dice se lo user è un medico
    private BooleanProperty type = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    /*
        Metodo per il binding della BooleanProperty
     */
    public BooleanProperty typeProperty(){
        return type;
    }
    public boolean getType(){
        return type.get();
    }

    public String getUsername() {
        return username.get();
    }

    /*
        Metodo per il binding della StringProperty
     */
    public StringProperty userProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    /*
        Metodo per il binding della StringProperty
     */
    public StringProperty passwordProperty() {
        return password;
    }
}