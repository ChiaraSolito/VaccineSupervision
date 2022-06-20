package Model;

import javafx.beans.property.SimpleStringProperty;

public class Notice {
    private final SimpleStringProperty id;
    private final SimpleStringProperty content;
    private final SimpleStringProperty noticeDate;

    public Notice(SimpleStringProperty id, SimpleStringProperty content, SimpleStringProperty noticeDate) {
        this.id = id;
        this.content = content;
        this.noticeDate = noticeDate;
    }


    public Notice(){
        this.id =  new SimpleStringProperty("");
        this.content =  new SimpleStringProperty("");
        this.noticeDate = new SimpleStringProperty("");
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

    public String getNoticeDate() { return noticeDate.get(); }

    public SimpleStringProperty noticeDateProperty() { return noticeDate; }

    public void setNoticeDate(String noticeDate) { this.noticeDate.set(noticeDate); }
}
