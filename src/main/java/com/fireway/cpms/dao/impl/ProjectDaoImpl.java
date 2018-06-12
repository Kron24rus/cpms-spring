package com.fireway.cpms.dao.impl;

import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.dao.ProjectDAO;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.Project;
import com.fireway.cpms.model.ProjectRole;
import com.fireway.cpms.model.ProjectType;
import com.fireway.cpms.model.UserToProject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ProjectDaoImpl implements ProjectDAO {
    private static final LoggerWrapper LOG = LoggerWrapper.getLogger(ProjectDaoImpl.class);

    @Override
    public List<Project> getProjectsForUser(int userId) throws DataAccessException {
        List<Project> result = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            List<UserToProject> userToProjects = HibernateUtil.getSession().createCriteria(UserToProject.class).add(Restrictions.eq("userId", userId)).list();
            for (UserToProject userToProject: userToProjects) {
                Hibernate.initialize(userToProject.getProject());
                result.add(userToProject.getProject());
            }
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public Project getProjectWithMembers(int projectId) throws DataAccessException {
        Project result;
        try {
            HibernateUtil.beginTransaction();
            result = (Project) HibernateUtil.getSession().createCriteria(Project.class)
                    .add(Restrictions.eq("id", projectId)).uniqueResult();
            Hibernate.initialize(result.getProjectToUsers());
            for (UserToProject userToProject: result.getProjectToUsers()) {
                Hibernate.initialize(userToProject.getUser());
                Hibernate.initialize(userToProject.getRole());
            }
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public ProjectRole getMemberRole(int userId, int projectId) throws DataAccessException {
        ProjectRole result = null;
        UserToProject userToProject;
        try {
            HibernateUtil.beginTransaction();
            userToProject = (UserToProject) HibernateUtil.getSession().createCriteria(UserToProject.class)
                    .add(Restrictions.eq("userId", userId))
                    .add(Restrictions.eq("projectId", projectId))
                    .uniqueResult();
            if (userToProject != null) {
                Hibernate.initialize(userToProject.getRole());
                result = userToProject.getRole();
            }
        } catch (HibernateException e) {
            LOG.error(e);
            HibernateUtil.rollback();
            throw new DataAccessException(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public ProjectType getProjectType(int projectTypeId) throws DataAccessException {
        return (ProjectType) HibernateUtil.doGetById(ProjectType.class, projectTypeId);
    }

    @Override
    public List<ProjectType> getAllTypes() throws DataAccessException {
        return (List<ProjectType>) HibernateUtil.doGetAll(ProjectType.class);
    }

    @Override
    public boolean isMember(int userId, int projectId) throws DataAccessException {
        return getMemberRole(userId, projectId) != null;
    }

    @Override
    public boolean isManager(int userId, int projectId) throws DataAccessException {
        ProjectRole role = getMemberRole(userId, projectId);
        return (role != null) && StringUtils.equals(role.getSlug(), ProjectRole.MANAGER);
    }

    @Override
    public List<Project> getAll() throws DataAccessException {
        return (List<Project>) HibernateUtil.doGetAll(Project.class);
    }

    @Override
    public Project update(Project updateEntity) throws DataAccessException {
        return (Project) HibernateUtil.doUpdate(updateEntity);
    }

    @Override
    public void delete(Project entity) throws DataAccessException {
        HibernateUtil.doDelete(entity);
    }

    @Override
    public void create(Project entity) throws DataAccessException {
        HibernateUtil.doCreate(entity);
    }

    @Override
    public void createOrUpdate(Project entity) throws DataAccessException {
        HibernateUtil.doCreateOrUpdate(entity);
    }
}
