package com.fireway.cpms.dao.impl;

import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.dao.UserToProjectDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.UserToProject;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@SuppressWarnings("unchecked")
public class UserToProjectDaoImpl implements UserToProjectDAO {
    private static final LoggerWrapper LOG = LoggerWrapper.getLogger(UserToProjectDaoImpl.class);

    private void fullInitialize(UserToProject userToProject) {
        if (userToProject != null) {
            Hibernate.initialize(userToProject.getUser());
            Hibernate.initialize(userToProject.getProject());
            Hibernate.initialize(userToProject.getRole());
        }
    }

    @Override
    public List<UserToProject> getAll() throws DataAccessException {
        return (List<UserToProject>) HibernateUtil.doGetAll(UserToProject.class);
    }

    @Override
    public UserToProject update(UserToProject updateEntity) throws DataAccessException {
        return (UserToProject) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(UserToProject entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(UserToProject entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(UserToProject entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }

    @Override
    public List<UserToProject> doFilter(Integer userId, Integer projectId, Integer roleId) throws DataAccessException {
        List<UserToProject> list;
        try {
            HibernateUtil.beginTransaction();
            Criteria criteria =  HibernateUtil.getSession().createCriteria(UserToProject.class);
            if (userId != null) {
                criteria.add(Restrictions.eq("userId", userId));
            }
            if (projectId != null) {
                criteria.add(Restrictions.eq("projectId", projectId));
            }
            if (roleId != null) {
                criteria.add(Restrictions.eq("roleId", roleId));
            }
            list = (List<UserToProject>) criteria.list();
            list.forEach(this::fullInitialize);
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
    public UserToProject get(Integer userId, Integer projectId) throws DataAccessException {
        UserToProject userToProject;
        try {
            HibernateUtil.beginTransaction();
            userToProject = (UserToProject) HibernateUtil.getSession().createCriteria(UserToProject.class)
                    .add(Restrictions.eq("userId", userId))
                    .add(Restrictions.eq("projectId", projectId)).uniqueResult();
            fullInitialize(userToProject);
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return userToProject;
    }
}
