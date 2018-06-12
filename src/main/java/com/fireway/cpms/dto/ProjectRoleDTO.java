package com.fireway.cpms.dto;


import com.fireway.cpms.model.ProjectRole;

public class ProjectRoleDTO {
    private int id;
    private String slug;
    private String name;

    public ProjectRoleDTO(){

    }

    public ProjectRoleDTO(int id, String slug, String name) {
        this.id = id;
        this.slug = slug;
        this.name = name;
    }

    public ProjectRoleDTO(ProjectRole role) {
        if (role != null) {
            this.id = role.getId();
            this.name = role.getName();
            this.slug = role.getSlug();
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
