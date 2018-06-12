package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.*;

import java.util.List;

public interface ProjectLogDAO extends GenericDAO<ProjectLog, Integer> {
    void clear() throws DataAccessException;

    LogType getLogType(String slug) throws DataAccessException;

    List<ProjectLog> getForProject(int projectId) throws DataAccessException;

    void batchCreate(List<ProjectLog> list) throws DataAccessException;

    void log(String typeSlug, User author, Project project) throws DataAccessException;

    void log(String typeSlug, User author, Project project, String oldValue, String newValue) throws DataAccessException;

    void log(String typeSlug, User author, Project project, String oldValue, String newValue, User target, ProjectStage stage, Deployment deployment) throws DataAccessException;

    void logProjectCreated(User author) throws DataAccessException;

    void logProjectNameUpdated(User author, Project project, String oldValue, String newValue) throws DataAccessException;
}
