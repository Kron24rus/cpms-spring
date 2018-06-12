package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.Deployment;

import java.util.List;

public interface DeploymentDAO extends GenericDAO<Deployment, Integer> {
    Deployment get(int id) throws DataAccessException;

    List<Deployment> getForProjectStage(int stageId) throws DataAccessException;
}
