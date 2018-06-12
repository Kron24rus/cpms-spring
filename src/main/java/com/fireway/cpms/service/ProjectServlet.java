package com.fireway.cpms.service;

import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dao.impl.ProjectLogDaoImpl;
import com.fireway.cpms.dto.ProjectDTO;
import com.fireway.cpms.dto.UserToProjectDTO;
import com.fireway.cpms.dto.extra.CurrentUserToProjectDTO;
import com.fireway.cpms.dto.extra.ProjectListDTO;
import com.fireway.cpms.dto.extra.SingleProjectDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.*;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "project", urlPatterns = "/project")
public class ProjectServlet extends GenericServlet {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    private static ProjectLogDaoImpl logDao = new ProjectLogDaoImpl();

    private List<ProjectDTO> getAllProjects() throws DataAccessException {
        return projectDao.getAll().stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    private List<ProjectDTO> getOwnProjects(int userId) throws DataAccessException {
        return projectDao.getProjectsForUser(userId).stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    private ProjectDTO getSingleProject(int projectId) throws NotFoundException, DataAccessException {
        Project project = projectDao.getProjectWithMembers(projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        ProjectDTO projectDTO = new ProjectDTO(project);
        projectDTO.setProjectType(project.getProjectType());
        List<UserToProjectDTO> members = new ArrayList<>();
        for (UserToProject userToProject : project.getProjectToUsers()) {
            UserToProjectDTO userToProjectDTO = new UserToProjectDTO();
            userToProjectDTO.setUser(userToProject.getUser());
            userToProjectDTO.setRole(userToProject.getRole());
            members.add(userToProjectDTO);
        }
        projectDTO.setMembers(members);
        return projectDTO;
    }

    private CurrentUserToProjectDTO getCurrentRole(int userId, int projectId) throws DataAccessException {
        ProjectRole role = projectDao.getMemberRole(userId, projectId);
        return new CurrentUserToProjectDTO(role);
    }

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }

        String name = request.requireNotBlankParameterString("name");
        String description = request.getParameterString("description");
        Integer priority = request.getParameterInteger("priority");
        Integer projectTypeId = request.requirePositiveParameterInteger("type");

        ProjectType type = projectDao.getProjectType(projectTypeId);
        if (type == null) {
            throw new NotFoundException("Type " + projectTypeId + " not found");
        }
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setPriority(priority);
        project.setProjectType(projectDao.getProjectType(projectTypeId));
        project.setActive(false);

        projectDao.create(project);

        logDao.log(LogType.PROJECT_CREATED, request.getCurrentUser(), project);

        response.writeJson(new ProjectDTO(project));
    }

    @Override
    protected void handlePut(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }
        User user = request.getCurrentUser();

        Integer id = request.requirePositiveParameterInteger("id");
        String name = request.getParameterTrimmedString("name");
        String description = request.getParameterTrimmedString("description");
        Boolean active = request.getParameterBoolean("active");
        Integer priority = request.getParameterInteger("priority");

        Project project = projectDao.getProjectWithMembers(id);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }

        String oldName = project.getName();
        String oldDescription = project.getDescription();
        Boolean oldActive = project.isActive();
        Integer oldPriority = project.getPriority();
        if (name != null) {
            project.setName(name);
        }
        if (description != null) {
            project.setDescription(description);
        }
        if (active != null) {
            project.setActive(active);
        }
        if (priority != null) {
            project.setPriority(priority);
        }
        projectDao.update(project);

        if (!StringUtils.equals(oldName, name)) {
            logDao.log(LogType.PROJECT_NAME_UPDATED, user, project, oldName, name);
        }
        if (!StringUtils.equals(oldDescription, description)) {
            logDao.log(LogType.PROJECT_DESCR_UPDATED, user, project, oldDescription, description);
        }
        if (oldActive != active) {
            String newValue = null;
            if (active != null) {
                newValue = active.toString();
            }

            logDao.log(LogType.PROJECT_ACTIVE_UPDATED, user, project, oldActive.toString(), newValue);
        }
        if (oldPriority != priority) {
            String oldValue = null;
            String newValue = null;
            if (oldPriority != null) {
                oldValue = oldPriority.toString();
            }
            if (priority != null) {
                newValue = priority.toString();
            }
            logDao.log(LogType.PROJECT_PRIORITY_UPDATED, user, project, oldValue, newValue);
        }

        response.writeJson(new ProjectDTO(project));
    }

    @Override
    protected void handleDelete(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }
        Integer id = request.requirePositiveParameterInteger("id");

        Project project = projectDao.getProjectWithMembers(id);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        projectDao.delete(project);
        response.setNoContentStatus();
    }

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Integer id = request.getCurrentUserId();
        Integer projectId = request.getParameterInteger("id");
        if (projectId == null) {
            response.writeJson(new ProjectListDTO(getOwnProjects(id), getAllProjects()));
        } else {
            response.writeJson(new SingleProjectDTO(getSingleProject(projectId), getCurrentRole(id, projectId)));
        }
    }
}
