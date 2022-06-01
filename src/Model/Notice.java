package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

public class Notice {
    private final SimpleStringProperty id;
    private final SimpleStringProperty content;

    public Notice(SimpleStringProperty id, SimpleStringProperty content) {
        this.id = id;
        this.content = content;
    }

    public Notice(){
        this.id =  new SimpleStringProperty("");
        this.content =  new SimpleStringProperty("");
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

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}
