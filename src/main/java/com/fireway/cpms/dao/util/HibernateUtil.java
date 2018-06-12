package com.fireway.cpms.dao.util;

import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.exception.DataAccessException;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateUtil {
    private static final LoggerWrapper LOG = LoggerWrapper.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            return configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build());
        } catch (Throwable ex) {
            LOG.error(ex);
            return null;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public static Transaction beginTransaction() {
        return getSession().beginTransaction();
    }

    public static void commit() {
        getSession().getTransaction().commit();
    }

    public static void rollback() {
        getSession().getTransaction().rollback();
    }

    public static void closeSession() {
        if (getSession() != null && getSession().isOpen())
            getSession().close();
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }

    public static Object doGetById(Class entityClass, int id) throws DataAccessException {
        Object entity = null;
        try {
            beginTransaction();
            entity = getSession()
                    .createCriteria(entityClass)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult();
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException(e.getMessage(), e);
        } finally {
            closeSession();
        }
        return entity;
    }

    public static List doGetAll(Class entityClass) throws DataAccessException {
        List resultList = null;
        try {
            HibernateUtil.beginTransaction();
            resultList = getSession()
                    .createCriteria(entityClass)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }
        return resultList;
    }

    public static Object doUpdate(Object updateEntity) throws DataAccessException {
        try {
            beginTransaction();
            getSession().update(updateEntity);
            commit();
        } catch (HibernateException e) {
            LOG.error(e);
            rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            closeSession();
        }
        return updateEntity;
    }

    public static void doDelete(Object deleteEntity) throws DataAccessException{
        try {
            beginTransaction();
            getSession().delete(deleteEntity);
            commit();
        } catch (Exception e) {
            LOG.error(e);
            rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            closeSession();
        }
    }

    public static void doCreate(Object createEntity) throws DataAccessException {
        try {
            beginTransaction();
            getSession().save(createEntity);
            commit();
        } catch (Exception e) {
            LOG.error(e);
            rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            closeSession();
        }
    }

    public static void doCreateOrUpdate(Object entity) throws DataAccessException {
        try {
            beginTransaction();
            getSession().saveOrUpdate(entity);
            commit();
        } catch (Exception e) {
            LOG.error(e);
            rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            closeSession();
        }
    }
}
