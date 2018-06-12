package com.fireway.cpms.dao.impl;


import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.dao.EmployeePositionDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.EmployeePosition;

import java.util.List;

@SuppressWarnings("unchecked")
public class EmployeePositionDaoImpl implements EmployeePositionDAO {
    private static final LoggerWrapper LOG = LoggerWrapper.getLogger(EmployeePositionDaoImpl.class);

    @Override
    public List<EmployeePosition> getAll() throws DataAccessException {
        return (List<EmployeePosition>) HibernateUtil.doGetAll(EmployeePosition.class);
    }

    @Override
    public EmployeePosition update(EmployeePosition updateEntity) throws DataAccessException {
        return (EmployeePosition) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(EmployeePosition entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(EmployeePosition entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(EmployeePosition entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}
