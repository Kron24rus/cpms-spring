package com.fireway.cpms.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ProjectLog", schema = "", catalog = "dream_team_cpms")
public class ProjectLog {
    private int id;
    private Timestamp date;
    private String oldValue;
    private String newValue;

    private LogType logType;
    private User author;
    private User target;
    private Project project;
    private ProjectStage projectStage;
    private Deployment deployment;

    private Integer projectId;

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
    @Column(name = "Date", nullable = false, insertable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "OldValue")
    @Type(type = "text")
    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    @Basic
    @Column(name = "NewValue")
    @Type(type = "text")
    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LogTypeId")
    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuthorUserId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TargetUserId")
    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
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
    @JoinColumn(name = "ProjectStageId")
    public ProjectStage getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(ProjectStage projectStage) {
        this.projectStage = projectStage;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DeploymentId")
    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    @Basic
    @Column(name = "ProjectId", insertable = false, updatable = false)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
