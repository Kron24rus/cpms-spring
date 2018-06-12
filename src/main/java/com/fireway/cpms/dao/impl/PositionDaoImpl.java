package com.fireway.cpms.dao.impl;

import com.fireway.cpms.dao.PositionDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.EmployeeInfo;
import com.fireway.cpms.model.EmployeePosition;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@SuppressWarnings("unchecked")
public class PositionDaoImpl implements PositionDAO {
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
        try {
            HibernateUtil.beginTransaction();
            Session session = HibernateUtil.getSession();
            EmployeePosition newEntity = (EmployeePosition) session
                    .createCriteria(EmployeePosition.class)
                    .add(Restrictions.eq("id", entity.getId()))
                    .uniqueResult();
            Hibernate.initialize(newEntity.getInfos());
            for (EmployeeInfo info: newEntity.getInfos()) {
                Hibernate.initialize(info.getPosition());
            }
            HibernateUtil.commit();


            for (EmployeeInfo info: newEntity.getInfos()) {
                HibernateUtil.beginTransaction();
                info.setPosition(null);
                HibernateUtil.getSession().update(info);
                HibernateUtil.commit();
            }

            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().delete(newEntity);
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public void create(EmployeePosition entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(EmployeePosition entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }

    @Override
    public EmployeePosition getPosition(int id) throws DataAccessException {
        return (EmployeePosition) HibernateUtil.doGetById(EmployeePosition.class, id);
    }
}
