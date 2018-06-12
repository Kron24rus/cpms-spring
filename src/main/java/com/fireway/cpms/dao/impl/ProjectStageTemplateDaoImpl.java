package com.fireway.cpms.dao.impl;


import com.fireway.cpms.dao.ProjectStageTemplateDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.ProjectStageTemplate;

import java.util.List;

@SuppressWarnings("unchecked")
public class ProjectStageTemplateDaoImpl implements ProjectStageTemplateDAO {
    @Override
    public ProjectStageTemplate get(int id) throws DataAccessException {
        return (ProjectStageTemplate) HibernateUtil.doGetById(ProjectStageTemplate.class, id);
    }

    @Override
    public List<ProjectStageTemplate> getAll() throws DataAccessException {
        return (List<ProjectStageTemplate>) HibernateUtil.doGetAll(ProjectStageTemplate.class);
    }

    @Override
    public ProjectStageTemplate update(ProjectStageTemplate updateEntity) throws DataAccessException {
        return (ProjectStageTemplate) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(ProjectStageTemplate entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(ProjectStageTemplate entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(ProjectStageTemplate entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}
