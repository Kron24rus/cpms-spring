package com.fireway.cpms.dao.impl;

import com.fireway.cpms.dao.ProjectStageDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.ProjectStage;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@SuppressWarnings("unchecked")
public class ProjectStageDaoImpl implements ProjectStageDAO {
    private void fullInitialize(ProjectStage stage) {
        Hibernate.initialize(stage.getProject());
        Hibernate.initialize(stage.getTemplate());
        Hibernate.initialize(stage.getAssignedUsers());
        Hibernate.initialize(stage.getDeployments());
    }

    @Override
    public ProjectStage get(int id) throws DataAccessException {
        ProjectStage stage;
        try {
            HibernateUtil.beginTransaction();
            stage = (ProjectStage) HibernateUtil.getSession().createCriteria(ProjectStage.class)
                    .add(Restrictions.eq("id", id)).uniqueResult();
            fullInitialize(stage);
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }
        return stage;
    }

    @Override
    public List<ProjectStage> getForProject(int projectId) throws DataAccessException {
        List<ProjectStage> list;
        try {
            HibernateUtil.beginTransaction();
            list = HibernateUtil.getSession().createCriteria(ProjectStage.class)
                    .add(Restrictions.eq("projectId", projectId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            for (ProjectStage stage: list) {
                Hibernate.initialize(stage.getTemplate());
            }
            HibernateUtil.commit();
        } catch (HibernateException e) {
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }
        return list;
    }

    @Override
    public List<ProjectStage> getAll() throws DataAccessException {
        return (List<ProjectStage>) HibernateUtil.doGetAll(ProjectStage.class);
    }

    @Override
    public ProjectStage update(ProjectStage updateEntity) throws DataAccessException {
        return (ProjectStage) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(ProjectStage entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(ProjectStage entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(ProjectStage entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}
