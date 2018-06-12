package com.fireway.cpms.dto;


import com.fireway.cpms.model.ProjectStageTemplate;
import org.hibernate.Hibernate;

public class ProjectStageTemplateDTO {
    private int id;
    private String name;
    private String description;

    private ProjectTypeDTO type;

    public ProjectStageTemplateDTO() {

    }

    public ProjectStageTemplateDTO(ProjectStageTemplate template) {
        if (template != null) {
            this.id = template.getId();
            this.name = template.getName();
            this.description = template.getDescription();
            if (Hibernate.isInitialized(template.getProjectType()) && template.getProjectType() != null) {
                this.type = new ProjectTypeDTO(template.getProjectType());
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

    public ProjectTypeDTO getType() {
        return type;
    }

    public void setType(ProjectTypeDTO type) {
        this.type = type;
    }
}
