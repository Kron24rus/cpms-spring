package com.fireway.cpms.dto;


import com.fireway.cpms.model.Project;
import com.fireway.cpms.model.ProjectStage;
import com.fireway.cpms.model.User;
import com.fireway.cpms.util.DataUtils;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ProjectStageDTO {
    private int id;
    private String name;
    private int order;
    private String start;
    private String end;

    private ProjectDTO project;
    private List<UserDTO> users;

    public ProjectStageDTO() {

    }

    public ProjectStageDTO(ProjectStage stage) {
        if (stage != null) {
            this.id = stage.getId();
            this.name = stage.getName();
            this.order = stage.getOrder();
            this.setStart(stage.getStartDate());
            this.setEnd(stage.getEndDate());
            if (Hibernate.isInitialized(stage.getProject())) {
                this.setProject(stage.getProject());
            }
            if (Hibernate.isInitialized(stage.getAssignedUsers())) {
                this.setUsers(stage.getAssignedUsers());
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setStart(Date start) {
        if (start != null) {
            this.start = DataUtils.formatDate(start);
        } else {
            this.start = null;
        }
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setEnd(Date end) {
        if (end != null) {
            this.end = DataUtils.formatDate(end);
        } else {
            this.end = null;
        }
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public void setProject(Project project) {
        if (project != null) {
            this.project = new ProjectDTO(project);
        } else {
            this.project = null;
        }
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public void setUsers(Set<User> users) {
        if (users != null) {
            List<UserDTO> userDTOS = new ArrayList<>();
            for (User user: users) {
                userDTOS.add(new UserDTO(user));
            }
            this.users = userDTOS;
        } else {
            this.users = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
