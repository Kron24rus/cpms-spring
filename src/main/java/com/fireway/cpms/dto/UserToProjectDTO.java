package com.fireway.cpms.dto;

import com.fireway.cpms.model.Project;
import com.fireway.cpms.model.ProjectRole;
import com.fireway.cpms.model.User;
import com.fireway.cpms.model.UserToProject;
import org.hibernate.Hibernate;

public class UserToProjectDTO {
    private UserDTO user;
    private ProjectDTO project;
    private ProjectRoleDTO role;

    public UserToProjectDTO() {

    }

    public UserToProjectDTO(UserToProject userToProject) {
        if (userToProject != null) {
            if (Hibernate.isInitialized(userToProject.getUser()) && userToProject.getUser() != null) {
                setUser(userToProject.getUser());
            }
            if (Hibernate.isInitialized(userToProject.getProject()) && userToProject.getProject() != null) {
                setProject(userToProject.getProject());
            }
            if (Hibernate.isInitialized(userToProject.getRole()) && userToProject.getRole() != null) {
                setRole(userToProject.getRole());
            }
        }
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setUser(User user) {
        this.user = new UserDTO(user);
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public void setProject(Project project) {
        this.project = new ProjectDTO(project);
    }

    public ProjectRoleDTO getRole() {
        return role;
    }

    public void setRole(ProjectRoleDTO role) {
        this.role = role;
    }

    public void setRole(ProjectRole role) {
        this.role = new ProjectRoleDTO(role);
    }
}
