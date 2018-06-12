package com.fireway.cpms.dto;


import com.fireway.cpms.model.ProjectLog;
import com.fireway.cpms.util.DataUtils;
import org.hibernate.Hibernate;

public class LogDTO {
    private int id;
    private String date;
    private String oldValue;
    private String newValue;

    private LogTypeDTO type;
    private UserDTO author;
    private UserDTO target;
    private ProjectDTO project;
    private ProjectStageDTO stage;
    private DeploymentDTO deployment;

    public LogDTO() {

    }

    public LogDTO(ProjectLog log) {
        if (log != null) {
            this.id = log.getId();
            this.date = DataUtils.formatDate(log.getDate());
            this.oldValue = log.getOldValue();
            this.newValue = log.getNewValue();

            if (Hibernate.isInitialized(log.getLogType()) && log.getLogType() != null) {
                this.type = new LogTypeDTO(log.getLogType());
            }
            if (Hibernate.isInitialized(log.getAuthor()) && log.getAuthor() != null) {
                this.author = new UserDTO(log.getAuthor());
            }
            if (Hibernate.isInitialized(log.getTarget()) && log.getTarget() != null) {
                this.target = new UserDTO(log.getTarget());
            }
            if (Hibernate.isInitialized(log.getProject()) && log.getProject() != null) {
                this.project = new ProjectDTO(log.getProject());
            }
            if (Hibernate.isInitialized(log.getProjectStage()) && log.getProjectStage() != null) {
                this.stage = new ProjectStageDTO(log.getProjectStage());
            }
            if (Hibernate.isInitialized(log.getDeployment()) && log.getDeployment() != null) {
                this.deployment = new DeploymentDTO(log.getDeployment());
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public LogTypeDTO getType() {
        return type;
    }

    public void setType(LogTypeDTO type) {
        this.type = type;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public UserDTO getTarget() {
        return target;
    }

    public void setTarget(UserDTO target) {
        this.target = target;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public ProjectStageDTO getStage() {
        return stage;
    }

    public void setStage(ProjectStageDTO stage) {
        this.stage = stage;
    }

    public DeploymentDTO getDeployment() {
        return deployment;
    }

    public void setDeployment(DeploymentDTO deployment) {
        this.deployment = deployment;
    }
}
