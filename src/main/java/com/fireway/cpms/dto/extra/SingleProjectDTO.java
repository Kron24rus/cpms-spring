package com.fireway.cpms.dto.extra;


import com.fireway.cpms.dto.ProjectDTO;
import com.fireway.cpms.dto.ProjectRoleDTO;

public class SingleProjectDTO {
    private ProjectDTO project;
    private ProjectRoleDTO role;

    public SingleProjectDTO() {

    }

    public SingleProjectDTO(ProjectDTO project, ProjectRoleDTO role) {
        setProject(project);
        setRole(role);
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public ProjectRoleDTO getRole() {
        return role;
    }

    public void setRole(ProjectRoleDTO role) {
        this.role = role;
    }
}
