package com.fireway.cpms.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserToProject", schema = "", catalog = "dream_team_cpms")
public class UserToProject implements Serializable {
    private User user;
    private Project project;
    private ProjectRole role;

    private int userId;
    private int projectId;
    private int roleId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleId")
    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    @Column(name = "UserId", updatable = false, insertable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userForeignKey) {
        this.userId = userForeignKey;
    }

    @Column(name = "ProjectId", updatable = false, insertable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectForeignKey) {
        this.projectId = projectForeignKey;
    }

    @Column(name = "RoleId", updatable = false, insertable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
