package com.fireway.cpms.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Deployment", schema = "", catalog = "dream_team_cpms")
public class Deployment implements Serializable {
    private int id;
    private String name;
    private String version;
    private String url;
    private String description;
    private boolean removed;

    private ProjectStage projectStage;

    private int projectStageId;

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Version", length = 64)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "URL", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "Description")
    @Type(type = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Removed", nullable = false, columnDefinition = "int")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectStageId")
    public ProjectStage getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(ProjectStage projectStage) {
        this.projectStage = projectStage;
    }

    @Basic
    @Column(name = "ProjectStageId", updatable = false, insertable = false)
    public int getProjectStageId() {
        return projectStageId;
    }

    public void setProjectStageId(int projectStageId) {
        this.projectStageId = projectStageId;
    }
}
