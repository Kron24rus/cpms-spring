package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.ProjectRole;

public interface ProjectRoleDAO extends GenericDAO<ProjectRole, Integer>{
    ProjectRole get(int id) throws DataAccessException;
}
