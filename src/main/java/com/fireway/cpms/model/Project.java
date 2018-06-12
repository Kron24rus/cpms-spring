package com.fireway.cpms.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Project", schema = "", catalog = "dream_team_cpms")
public class Project implements Serializable {
    private int id;
    private String name;
    private String description;
    private boolean active;
    private Integer priority;

    private ProjectType projectType;
    private Set<UserToProject> projectToUsers = new HashSet<>(0);
    private Set<ProjectStage> projectStages = new HashSet<>(0);

    private int projectTypeId;

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
    @Column(name = "Name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "Active", nullable = false, columnDefinition = "int")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "Priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @ManyToOne
    @JoinColumn(name = "ProjectTypeId")
    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    public Set<UserToProject> getProjectToUsers() {
        return projectToUsers;
    }

    public void setProjectToUsers(Set<UserToProject> projectToUsers) {
        this.projectToUsers = projectToUsers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    public Set<ProjectStage> getProjectStages() {
        return projectStages;
    }

    public void setProjectStages(Set<ProjectStage> projectStages) {
        this.projectStages = projectStages;
    }

    @Column(name = "ProjectTypeId", updatable = false, insertable = false)
    public int getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(int projectTypeId) {
        this.projectTypeId = projectTypeId;
    }
}
