package com.fireway.cpms.service;


import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dao.impl.ProjectLogDaoImpl;
import com.fireway.cpms.dao.impl.ProjectStageDaoImpl;
import com.fireway.cpms.dao.impl.ProjectStageTemplateDaoImpl;
import com.fireway.cpms.dto.ProjectStageDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.LogType;
import com.fireway.cpms.model.Project;
import com.fireway.cpms.model.ProjectStage;
import com.fireway.cpms.model.ProjectStageTemplate;
import com.fireway.cpms.util.DataUtils;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "stage", urlPatterns = "/stage")
public class StageServlet extends GenericServlet{
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    private static ProjectStageDaoImpl stageDao = new ProjectStageDaoImpl();
    private static ProjectStageTemplateDaoImpl templateDao = new ProjectStageTemplateDaoImpl();
    private static ProjectLogDaoImpl logDao = new ProjectLogDaoImpl();

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        Integer userId = request.getCurrentUserId();
        String name = request.requireNotBlankParameterString("name");
        String start = request.getParameterTrimmedString("start");
        String end = request.getParameterTrimmedString("end");
        int projectId = request.requirePositiveParameterInteger("project");
        int templateId = request.requirePositiveParameterInteger("template");
        int order = request.requireNonNegativeParameterInteger("order");

        if (!request.isUserAdmin() && !projectDao.isManager(userId, projectId)) {
            throw new ForbiddenException("Not an admin or manager");
        }

        Project project = projectDao.getProjectWithMembers(projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }

        ProjectStageTemplate template = templateDao.get(templateId);
        if (template == null) {
            throw new NotFoundException("Template not found");
        }

        ProjectStage stage = new ProjectStage();
        stage.setName(name);
        stage.setOrder(order);
        stage.setProject(project);
        stage.setTemplate(template);
        if (start != null) {
            stage.setStartDate(DataUtils.parseDate(start));
        } else {
            stage.setStartDate(null);
        }
        if (end != null) {
            stage.setEndDate(DataUtils.parseDate(end));
        }

        stageDao.create(stage);
        logDao.log(LogType.PROJECT_STAGE_CREATED, request.getCurrentUser(), project, null, stage.getName());
        response.writeJson(new ProjectStageDTO(stage));
    }

    @Override
    protected void handleDelete(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        Integer userId = request.getCurrentUserId();
        Integer id = request.requirePositiveParameterInteger("id");
        ProjectStage stage = stageDao.get(id);
        if (stage == null) {
            throw new NotFoundException("Stage not found");
        }

        if (!request.isUserAdmin() && !projectDao.isManager(userId, stage.getProjectId())) {
            throw new ForbiddenException("Not an admin or manager");
        }

        stageDao.delete(stage);
        response.setNoContentStatus();
    }

    @Override
    protected void handlePut(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        Integer userId = request.getCurrentUserId();
        String name = request.getParameterTrimmedString("name");
        String start = request.getParameterTrimmedString("start");
        String end = request.getParameterTrimmedString("end");
        Integer id = request.requirePositiveParameterInteger("id");
        Integer order = request.getParameterNonNegativeInteger("order");
        ProjectStage stage = stageDao.get(id);
        if (stage == null) {
            throw new NotFoundException("Stage not found");
        }

        if (!request.isUserAdmin() && !projectDao.isManager(userId, stage.getProjectId())) {
            throw new ForbiddenException("Not an admin or manager");
        }
        if (name != null && StringUtils.isNotBlank(name)) {
            stage.setName(name);
        }
        if (order != null) {
            stage.setOrder(order);
        }
        if (start != null) {
            stage.setStartDate(DataUtils.parseDate(start));
        }
        if (end != null) {
            stage.setEndDate(DataUtils.parseDate(end));
        }

        stageDao.update(stage);
        response.writeJson(new ProjectStageDTO(stage));
    }

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Integer id = request.getParameterPositiveInteger("id");
        Integer projectId = request.getParameterPositiveInteger("project");

        Integer userId = request.getCurrentUserId();

        if (id != null) {
            ProjectStage stage = stageDao.get(id);
            if (stage == null) {
                throw new NotFoundException("Stage not found");
            }
            if (!request.isUserAdmin() && !projectDao.isMember(userId, stage.getProjectId())) {
                throw new ForbiddenException("Not a project member");
            }
            response.writeJson(new ProjectStageDTO(stage));
        } else if (projectId != null) {
            if (!request.isUserAdmin() && !projectDao.isMember(request.getCurrentUserId(), projectId)) {
                throw new ForbiddenException("Not a project member");
            }
            List<ProjectStage> list = stageDao.getForProject(projectId);
            response.writeJson(list.stream().map(ProjectStageDTO::new).collect(Collectors.toList()));
        } else {
            throw new BadRequestException("No ids provided");
        }
    }
}
