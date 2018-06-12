package com.fireway.cpms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "ProjectStage", schema = "", catalog = "dream_team_cpms")
public class ProjectStage implements Serializable {
    private int id;
    private String name;
    private int order;
    private Timestamp startDate;
    private Timestamp endDate;

    private Project project;
    private ProjectStageTemplate template;
    private Set<User> assignedUsers;
    private Set<Deployment> deployments;

    private int projectId;

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
    @Column(name = "Ordr", nullable = false)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Basic
    @Column(name = "StartDate")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "EndDate")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TemplateId")
    public ProjectStageTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ProjectStageTemplate template) {
        this.template = template;
    }

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class, mappedBy = "projectStagesAssigned")
    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    @Basic
    @Column(name = "ProjectId", updatable = false, insertable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectStage", cascade = CascadeType.ALL)
    public Set<Deployment> getDeployments() {
        return deployments;
    }

    public void setDeployments(Set<Deployment> deployments) {
        this.deployments = deployments;
    }

    @Basic
    @Column(name = "Name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
