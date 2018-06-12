package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.ProjectStageTemplate;

public interface ProjectStageTemplateDAO extends GenericDAO<ProjectStageTemplate, Integer>{
    public ProjectStageTemplate get(int id) throws DataAccessException;
}
