package com.fireway.cpms.service;

import com.fireway.cpms.dao.impl.*;
import com.fireway.cpms.dto.UserToProjectDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.*;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "member", urlPatterns = "/member")
public class MemberServlet extends GenericServlet {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    private static ProjectLogDaoImpl logDao = new ProjectLogDaoImpl();
    private static ProjectRoleDaoImpl roleDao = new ProjectRoleDaoImpl();
    private static UserDaoImpl userDao = new UserDaoImpl();
    private static UserToProjectDaoImpl userProjectDao = new UserToProjectDaoImpl();

    @Override
    protected void handlePut(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        handlePost(request, response);
    }

    @Override
    protected void handleDelete(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        int currentUserId = request.getCurrentUserId();
        int userId = request.requirePositiveParameterInteger("user");
        int projectId = request.requirePositiveParameterInteger("project");

        User user = userDao.get(userId);
        Project project = projectDao.getProjectWithMembers(projectId);

        if (user == null) {
            throw new NotFoundException("User not found");
        } else if (project == null) {
            throw new NotFoundException("Project not found");
        }

        if (!request.isUserAdmin() && !projectDao.isManager(currentUserId, projectId)) {
            throw new ForbiddenException("Not a project manager");
        }

        UserToProject userToProject = userProjectDao.get(userId, projectId);
        if (userToProject != null) {
            String oldRole = userToProject.getRole().getName();
            userProjectDao.delete(userToProject);
            logDao.log(LogType.MEMBER_UNASSIGNED, request.getCurrentUser(), project, oldRole, null);
        }
        response.writeJson(new HashMap<String, Object>());
    }

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        int currentUserId = request.getCurrentUserId();
        int userId = request.requirePositiveParameterInteger("user");
        int projectId = request.requirePositiveParameterInteger("project");
        int roleId = request.requirePositiveParameterInteger("role");

        User user = userDao.get(userId);
        Project project = projectDao.getProjectWithMembers(projectId);
        ProjectRole role = roleDao.get(roleId);

        if (user == null) {
            throw new NotFoundException("User not found");
        } else if (project == null) {
            throw new NotFoundException("Project not found");
        } else if (role == null) {
            throw new NotFoundException("Role not found");
        }

        if (StringUtils.equals(role.getSlug(), ProjectRole.MANAGER)) {
            if (!request.isUserAdmin()) {
                throw new ForbiddenException("Not an admin");
            }
        } else {
            if (!request.isUserAdmin() && !projectDao.isManager(currentUserId, projectId)) {
                throw new ForbiddenException("Not a project manager");
            }
        }

        UserToProject userToProject = userProjectDao.get(userId, projectId);
        if (userToProject != null) {
            if (role.getId() != userToProject.getRole().getId()) {
                String oldRole = userToProject.getRole().getName();
                userToProject.setRole(role);
                userProjectDao.update(userToProject);
                logDao.log(LogType.MEMBER_ROLE_UPDATED, request.getCurrentUser(), project, oldRole, role.getName());
            }
        } else {
            userToProject = new UserToProject();
            userToProject.setUser(user);
            userToProject.setProject(project);
            userToProject.setRole(role);
            userProjectDao.create(userToProject);
            logDao.log(LogType.MEMBER_ASSIGNED, request.getCurrentUser(), project, null, role.getName());
        }

        response.writeJson(new UserToProjectDTO(userToProject));
    }

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Integer projectId = request.getParameterPositiveInteger("project");
        Integer userId = request.getParameterPositiveInteger("user");
        Integer roleId = request.getParameterPositiveInteger("role");

        if (projectId == null && userId == null && roleId == null) {
            throw new BadRequestException("No filter specified");
        }
        List<UserToProject> list = userProjectDao.doFilter(userId, projectId, roleId);
        response.writeJson(list.stream().map(UserToProjectDTO::new).collect(Collectors.toList()));
    }
}
