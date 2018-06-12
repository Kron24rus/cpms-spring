package com.fireway.cpms.dao;



import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.ProjectStage;

import java.util.List;

public interface ProjectStageDAO extends GenericDAO<ProjectStage, Integer> {
    ProjectStage get(int id) throws DataAccessException;

    List<ProjectStage> getForProject(int projectId) throws DataAccessException;
}
