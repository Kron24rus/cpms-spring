package com.fireway.cpms.dao.impl;

import com.fireway.cpms.dao.MessageDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.Message;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@SuppressWarnings("unchecked")
public class MessageDaoImpl implements MessageDAO {
    @Override
    public List<Message> getAll() throws DataAccessException {
        return (List<Message>) HibernateUtil.doGetAll(Message.class);
    }

    @Override
    public Message update(Message updateEntity) throws DataAccessException {
        return (Message) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(Message entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(Message entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(Message entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }

    @Override
    public List<Message> getReceivedMessages(int userId) throws DataAccessException {
        List<Message> resultList = null;
        try {
            HibernateUtil.beginTransaction();
            resultList = HibernateUtil.getSession()
                    .createCriteria(Message.class)
                    .add(Restrictions.eq("targetForeignKey", userId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            for (Message message: resultList) {
                Hibernate.initialize(message.getAuthor());
            }
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return resultList;
    }
}
