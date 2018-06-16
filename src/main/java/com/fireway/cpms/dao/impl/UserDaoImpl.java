package com.fireway.cpms.dao.impl;

import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.dao.UserDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.dao.util.PasswordHash;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.EmployeeInfo;
import com.fireway.cpms.model.EmployeePosition;
import com.fireway.cpms.model.User;
import com.fireway.cpms.model.expand.UserExpansion;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDAO {
    private static final LoggerWrapper LOG = LoggerWrapper.getLogger(UserDaoImpl.class);

    private void doExpand(User user, UserExpansion expansion) throws DataAccessException {
        switch (expansion) {
            case RECEIVED_MESSAGES:
                Hibernate.initialize(user.getReceivedMessages());
                break;
            case EMPLOYEE_INFO:
                Hibernate.initialize(user.getInfo());
                break;
        }
    }

    @Override
    public User authenticate(String login, String password) throws DataAccessException,
            InvalidKeySpecException, NoSuchAlgorithmException {
        User user = getByLogin(login);
        if (user != null && PasswordHash.validatePassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(int userId) throws DataAccessException {
        return getEntityWithRestrictions(Restrictions.eq("id", userId));
    }

    @Override
    public User getEntityWithRestrictions(Criterion... criteria) throws DataAccessException {
        User user = null;
        try {
            HibernateUtil.beginTransaction();
            Criteria request = HibernateUtil.getSession().createCriteria(User.class);
            for (Criterion criterion : criteria) {
                request = request.add(criterion);
            }
            user = (User) request.uniqueResult();
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return user;
    }

//    @Override
//    public User getByLogin(String login) throws DataAccessException {
//        return getEntityWithRestrictions(Restrictions.eq("login", login));
//    }


    @Override
    public User getByLogin(String login) throws DataAccessException {
        User user;
        try {
            HibernateUtil.beginTransaction();
            CriteriaBuilder builder = HibernateUtil.getSession().getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery(User.class);
            Root root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("login"), login));
            user = (User) HibernateUtil.getSession().createQuery(query).uniqueResult();
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return user;
    }

    @Override
    public User getAndExpand(int userId, UserExpansion... expand) throws DataAccessException {
        User user;
        try {
            HibernateUtil.beginTransaction();
            user = (User) HibernateUtil.getSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq("id", userId))
                    .uniqueResult();
            for (UserExpansion expansion : expand) {
                doExpand(user, expansion);
            }
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return user;
    }

    @Override
    public EmployeePosition getPosition(int positionId) throws DataAccessException {
        return (EmployeePosition) HibernateUtil.doGetById(EmployeePosition.class, positionId);
    }

    @Override
    public void create(User user, EmployeeInfo info) throws DataAccessException {
        try {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().save(info);
            user.setInfo(info);
            HibernateUtil.getSession().save(user);
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public User getWithInfo(int id) throws DataAccessException {
        User user = null;
        try {
            HibernateUtil.beginTransaction();
            user = (User) HibernateUtil.getSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult();
            if (user != null) {
                Hibernate.initialize(user.getInfo());
                if (user.getInfo() != null) {
                    Hibernate.initialize(user.getInfo().getPosition());
                }
            }
            HibernateUtil.commit();
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DataAccessException {
        return (List<User>) HibernateUtil.doGetAll(User.class);
    }

    @Override
    public User update(User updateEntity) throws DataAccessException {
        return (User) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(User entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(User entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(User entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}
