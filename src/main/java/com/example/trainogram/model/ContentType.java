package com.example.trainogram.model;

public enum ContentType {
    POST(0, "POST"),
    COMMENT(1, "COMMENT"),
    ACCOUNT(2, "ACCOUNT");

    private String type;
    private Integer id;

    ContentType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public static ContentType typeFromId(Integer id) {
        for (ContentType c : ContentType.values()) {
            if (c.getId().compareTo(id) == 0) {
                return c;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }
}
