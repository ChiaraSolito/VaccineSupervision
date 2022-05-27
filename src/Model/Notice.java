package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

public class Notice {
    private final SimpleStringProperty id;
    private final SimpleStringProperty Content;

    public Notice(SimpleStringProperty id, SimpleStringProperty content) {
        this.id = id;
        this.Content = content;
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
        return Content.get();
    }

    public SimpleStringProperty contentProperty() {
        return Content;
    }

    public void setContent(String content) {
        this.Content.set(content);
    }
}
