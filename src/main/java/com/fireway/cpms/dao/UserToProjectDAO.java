package com.fireway.cpms.dao;



import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.UserToProject;

import java.util.List;

public interface UserToProjectDAO extends GenericDAO<UserToProject, Integer>{
    List<UserToProject> doFilter(Integer userId, Integer projectId, Integer roleId) throws DataAccessException;

    UserToProject get(Integer userId, Integer projectId) throws DataAccessException;
}
