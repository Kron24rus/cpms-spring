package com.fireway.cpms.service;



import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dao.impl.ProjectLogDaoImpl;
import com.fireway.cpms.dao.impl.ProjectStageDaoImpl;
import com.fireway.cpms.dao.impl.UserDaoImpl;
import com.fireway.cpms.dto.ProjectStageDTO;
import com.fireway.cpms.dto.UserDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.ProjectStage;
import com.fireway.cpms.model.User;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "assignee", urlPatterns = "/assignee")
public class AssigneeServlet extends GenericServlet {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    private static ProjectStageDaoImpl stageDao = new ProjectStageDaoImpl();
    private static ProjectLogDaoImpl logDao = new ProjectLogDaoImpl();
    private static UserDaoImpl userDao = new UserDaoImpl();

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        int currentUserId = request.getCurrentUserId();
        int stageId = request.requirePositiveParameterInteger("stage");
        int userId = request.requireParameterInteger("user");

        ProjectStage stage = stageDao.get(stageId);
        User user = userDao.get(userId);
        if (stage == null) {
            throw new NotFoundException("Stage not found");
        } else if (user == null) {
            throw new NotFoundException("User not found");
        } else if (!request.isUserAdmin() && !projectDao.isManager(currentUserId, stage.getProjectId())) {
            throw new ForbiddenException("Not a project manager");
        } else if (!projectDao.isMember(userId, stage.getProjectId())) {
            throw new NotFoundException("User not found in project");
        }

        if (!stage.getAssignedUsers().contains(user)) {
            stage.getAssignedUsers().add(user);
            stageDao.update(stage);
        }

        response.writeJson(new ProjectStageDTO(stage));
    }

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        int userId = request.getCurrentUserId();
        int stageId = request.requirePositiveParameterInteger("id");

        ProjectStage stage = stageDao.get(stageId);
        if (stage == null) {
            throw new NotFoundException("Stage not found");
        }
        if (!request.isUserAdmin() && !projectDao.isMember(userId, stage.getProjectId())) {
            throw new ForbiddenException("Not a project member");
        }

        response.writeJson(stage.getAssignedUsers().stream().map(UserDTO::new).collect(Collectors.toList()));
    }
}
