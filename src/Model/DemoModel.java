package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DemoModel {

    /*
        Metodi per l'accesso al database
     */
    private void manageSQLException(Exception e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }

    private ResultSet runQuery(String q){
        try{
            ResultSet r = null;
            //Statement stmt = c.createStatement();
            //r = stmt.executeQuery(q);
            return r;
        } catch (Exception e) {
            manageSQLException(e);
        }
        return null;
    }
}
