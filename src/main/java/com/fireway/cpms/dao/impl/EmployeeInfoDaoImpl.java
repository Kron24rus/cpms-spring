package com.fireway.cpms.dao.impl;


import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.dao.EmployeeInfoDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.EmployeeInfo;

import java.util.List;

@SuppressWarnings("unchecked")
public class EmployeeInfoDaoImpl implements EmployeeInfoDAO {
    private static final LoggerWrapper LOG = LoggerWrapper.getLogger(EmployeeInfoDaoImpl.class);

    @Override
    public List<EmployeeInfo> getAll() throws DataAccessException {
        return (List<EmployeeInfo>) HibernateUtil.doGetAll(EmployeeInfo.class);
    }

    @Override
    public EmployeeInfo update(EmployeeInfo updateEntity) throws DataAccessException {
        return (EmployeeInfo) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(EmployeeInfo entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(EmployeeInfo entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(EmployeeInfo entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}