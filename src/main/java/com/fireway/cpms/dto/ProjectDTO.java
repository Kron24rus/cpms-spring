package com.fireway.cpms.dto;


import com.fireway.cpms.model.Project;
import com.fireway.cpms.model.ProjectType;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.List;

public class ProjectDTO implements Serializable {
    private int id;
    private String name;
    private String description;
    private boolean active;
    private int priority;
    private ProjectTypeDTO projectType;
    private List<UserToProjectDTO> members;

    public ProjectDTO() {

    }

    public ProjectDTO(int id, String name, String description, boolean active, int priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.priority = priority;
    }

    public ProjectDTO(Project project) {
        if (project != null) {
            this.id = project.getId();
            this.name = project.getName();
            this.description = project.getDescription();
            this.active = project.isActive();
            this.priority = project.getPriority();
            if (Hibernate.isInitialized(project.getProjectType()) && project.getProjectType() != null) {
                this.projectType = new ProjectTypeDTO(project.getProjectType());
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ProjectTypeDTO getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectTypeDTO projectType) {
        this.projectType = projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = new ProjectTypeDTO(projectType);
    }

    public List<UserToProjectDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserToProjectDTO> members) {
        this.members = members;
    }
}
