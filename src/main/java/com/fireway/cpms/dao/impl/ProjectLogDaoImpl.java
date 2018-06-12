package com.fireway.cpms.dao.impl;

import com.fireway.cpms.dao.ProjectLogDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@SuppressWarnings("unchecked")
public class ProjectLogDaoImpl implements ProjectLogDAO {
    @Override
    public void clear() throws DataAccessException {
        try {
            HibernateUtil.beginTransaction();
            List<ProjectLog> list = (List<ProjectLog>) HibernateUtil.getSession().createCriteria(ProjectLog.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            for (ProjectLog log: list) {
                HibernateUtil.getSession().delete(log);
            }
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public LogType getLogType(String slug) throws DataAccessException {
        LogType result;
        try {
            HibernateUtil.beginTransaction();
            result = (LogType) HibernateUtil.getSession().createCriteria(LogType.class)
                    .add(Restrictions.eq("slug", slug)).uniqueResult();
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public List<ProjectLog> getForProject(int projectId) throws DataAccessException {
        List<ProjectLog> result = null;
        try {
            HibernateUtil.beginTransaction();
            result = (List<ProjectLog>) HibernateUtil.getSession().createCriteria(ProjectLog.class)
                    .add(Restrictions.eq("projectId", projectId)).list();
            for (ProjectLog log: result) {
                Hibernate.initialize(log.getLogType());
                Hibernate.initialize(log.getAuthor());
                Hibernate.initialize(log.getTarget());
                Hibernate.initialize(log.getProjectStage());
                Hibernate.initialize(log.getDeployment());
            }
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public void batchCreate(List<ProjectLog> list) throws DataAccessException {
        try {
            HibernateUtil.beginTransaction();
            Session session = HibernateUtil.getSession();
            for (ProjectLog log: list) {
                session.save(log);
            }
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public void log(String typeSlug, User author, Project project) throws DataAccessException {
        log(typeSlug, author, project, null, null);
    }

    @Override
    public void log(String typeSlug, User author, Project project, String oldValue, String newValue) throws DataAccessException {
        log(typeSlug, author, project, oldValue, newValue, null, null, null);
    }

    @Override
    public void log(String typeSlug, User author, Project project, String oldValue, String newValue, User target, ProjectStage stage, Deployment deployment) throws DataAccessException {
        ProjectLog log = new ProjectLog();
        log.setLogType(getLogType(typeSlug));
        log.setAuthor(author);
        log.setProject(project);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setTarget(target);
        log.setProjectStage(stage);
        log.setDeployment(deployment);
        HibernateUtil.doCreate(log);
    }

    @Override
    public void logProjectCreated(User author) throws DataAccessException {
        ProjectLog log = new ProjectLog();
        log.setLogType(getLogType(LogType.PROJECT_CREATED));
        log.setAuthor(author);
        HibernateUtil.doCreate(log);
    }

    @Override
    public void logProjectNameUpdated(User author, Project project, String oldValue, String newValue) throws DataAccessException {
        ProjectLog log = new ProjectLog();
        log.setLogType(getLogType(LogType.PROJECT_NAME_UPDATED));
        log.setAuthor(author);
        log.setProject(project);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        HibernateUtil.doCreate(log);
    }

    @Override
    public List<ProjectLog> getAll() throws DataAccessException {
        return (List<ProjectLog>) HibernateUtil.doGetAll(ProjectLog.class);
    }

    @Override
    public ProjectLog update(ProjectLog updateEntity) throws DataAccessException {
        return (ProjectLog) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(ProjectLog entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(ProjectLog entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(ProjectLog entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}
