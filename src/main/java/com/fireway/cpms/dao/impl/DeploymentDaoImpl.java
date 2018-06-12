package com.fireway.cpms.dao.impl;


import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.dao.DeploymentDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.Deployment;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@SuppressWarnings("unchecked")
public class DeploymentDaoImpl implements DeploymentDAO {
    private static final LoggerWrapper LOG = LoggerWrapper.getLogger(DeploymentDaoImpl.class);

    private static void initialize(Deployment deployment) throws HibernateException {
        Hibernate.initialize(deployment.getProjectStage());
        Hibernate.initialize(deployment.getProjectStage().getProject());
    }

    @Override
    public Deployment get(int id) throws DataAccessException {
        Deployment deployment;
        try {
            HibernateUtil.beginTransaction();
            deployment = (Deployment) HibernateUtil.getSession().createCriteria(Deployment.class)
                    .add(Restrictions.eq("id", id)).uniqueResult();
            initialize(deployment);
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return deployment;
    }

    @Override
    public List<Deployment> getForProjectStage(int stageId) throws DataAccessException {
        List<Deployment> list;
        try {
            HibernateUtil.beginTransaction();
            list = (List<Deployment>) HibernateUtil.getSession().createCriteria(Deployment.class)
                    .add(Restrictions.eq("projectStageId", stageId)).list();
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return list;
    }

    @Override
    public List<Deployment> getAll() throws DataAccessException {
        return (List<Deployment>) HibernateUtil.doGetAll(Deployment.class);
    }

    @Override
    public Deployment update(Deployment updateEntity) throws DataAccessException {
        return (Deployment) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(Deployment entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(Deployment entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(Deployment entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}
