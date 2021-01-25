package com.kapil.kapil.algosexplained.Sorting;

public class JSONData {
    //type: 0 means comment, 1 means reply
    private int type;
    private String username;
    private String comment;
    private String id;

    public JSONData() {

    }

    public JSONData(int type, String username, String comment) {
        this.type = type;
        this.username = username;
        this.comment = comment;
    }

    public JSONData(int type, String username, String comment, String id) {
        this.type = type;
        this.username = username;
        this.comment = comment;
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getId() { return  id; }
}
