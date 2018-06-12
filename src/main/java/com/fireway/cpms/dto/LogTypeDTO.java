package com.fireway.cpms.dto;


import com.fireway.cpms.model.LogType;

public class LogTypeDTO {
    private int id;
    private String slug;
    private String name;

    public LogTypeDTO() {

    }

    public LogTypeDTO(LogType logType) {
        if (logType != null) {
            this.id = logType.getId();
            this.slug = logType.getSlug();
            this.name = logType.getName();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
