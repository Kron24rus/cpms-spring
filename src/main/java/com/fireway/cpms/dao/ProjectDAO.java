package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.Project;
import com.fireway.cpms.model.ProjectRole;
import com.fireway.cpms.model.ProjectType;

import java.util.List;

public interface ProjectDAO extends GenericDAO<Project, Integer> {
    List<Project> getProjectsForUser(int userId) throws DataAccessException;

    Project getProjectWithMembers(int projectId) throws DataAccessException;

    ProjectRole getMemberRole(int userId, int projectId) throws DataAccessException;

    ProjectType getProjectType(int projectTypeId) throws DataAccessException;

    List<ProjectType> getAllTypes() throws DataAccessException;

    boolean isMember(int userId, int projectId) throws DataAccessException;

    boolean isManager(int userId, int projectId) throws DataAccessException;
}
