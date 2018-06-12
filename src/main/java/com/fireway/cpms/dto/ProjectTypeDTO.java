package com.fireway.cpms.dto;


import com.fireway.cpms.model.ProjectType;

public class ProjectTypeDTO {
    private int id;
    private String slug;
    private String name;
    private String description;

    public ProjectTypeDTO() {

    }

    public ProjectTypeDTO(int id, String slug, String name, String description) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.description = description;
    }

    public ProjectTypeDTO(ProjectType type) {
        if (type != null) {
            this.id = type.getId();
            this.slug = type.getSlug();
            this.description = type.getDescription();
            this.name = type.getName();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
