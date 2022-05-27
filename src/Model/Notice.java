package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

public class Notice {
    private final SimpleStringProperty id;
    private final SimpleStringProperty Content;
    private Map<String, String> readNotice;

    public Notice(SimpleStringProperty id, SimpleStringProperty content, Map<String, String> readNotice) {
        this.id = id;
        this.Content = content;
        this.readNotice = readNotice;
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

    public Map<String, String> getReadNotice() {
        return readNotice;
    }

    public void setReadNotice(Map<String, String> readNotice) {
        this.readNotice = readNotice;
    }
}
