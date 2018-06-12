package com.fireway.cpms.service;


import com.fireway.cpms.dao.impl.DeploymentDaoImpl;
import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dao.impl.ProjectLogDaoImpl;
import com.fireway.cpms.dao.impl.ProjectStageDaoImpl;
import com.fireway.cpms.dto.DeploymentDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.*;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeploymentServlet extends GenericServlet {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    private static ProjectStageDaoImpl projectStageDao = new ProjectStageDaoImpl();
    private static DeploymentDaoImpl deploymentDao = new DeploymentDaoImpl();
    private static ProjectLogDaoImpl logDao = new ProjectLogDaoImpl();

    @Override
    protected void handlePut(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        int userId = request.getCurrentUserId();
        User user = request.getCurrentUser();

        int deploymentId = request.requirePositiveParameterInteger("id");
        String name = request.getParameterTrimmedString("name");
        String url = request.getParameterTrimmedString("url");
        String version = request.getParameterTrimmedString("version");
        String description = request.getParameterTrimmedString("description");
        Boolean removed = request.getParameterBoolean("removed");

        List<ProjectLog> logs = new ArrayList<>();

        Deployment deployment = deploymentDao.get(deploymentId);
        if (deployment == null) {
            throw new NotFoundException("Deployment not found");
        }
        if (!request.isUserAdmin() && !projectDao.isManager(userId, deployment.getProjectStage().getProjectId())) {
            throw new ForbiddenException("Not a project member");
        }
        if (name != null && !StringUtils.equals(name, deployment.getName())) {
            ProjectLog log = new ProjectLog();
            log.setAuthor(user);
            log.setLogType(logDao.getLogType(LogType.DEPLOYMENT_NAME_UPDATED));
            log.setOldValue(deployment.getName());
            log.setNewValue(name);
            log.setProject(deployment.getProjectStage().getProject());
            logs.add(log);
            deployment.setName(name);
        }
        if (url != null && !StringUtils.equals(url, deployment.getUrl())) {
            ProjectLog log = new ProjectLog();
            log.setAuthor(user);
            log.setLogType(logDao.getLogType(LogType.DEPLOYMENT_URL_UPDATED));
            log.setOldValue(deployment.getUrl());
            log.setNewValue(url);
            log.setProject(deployment.getProjectStage().getProject());
            logs.add(log);
            deployment.setUrl(url);
        }
        if (version != null && !StringUtils.equals(version, deployment.getVersion())) {
            ProjectLog log = new ProjectLog();
            log.setAuthor(user);
            log.setLogType(logDao.getLogType(LogType.DEPLOYMENT_VERSION_UPDATED));
            log.setOldValue(deployment.getVersion());
            log.setNewValue(version);
            log.setProject(deployment.getProjectStage().getProject());
            logs.add(log);
            deployment.setVersion(version);
        }
        if (description != null && !StringUtils.equals(description, deployment.getDescription())) {
            ProjectLog log = new ProjectLog();
            log.setAuthor(user);
            log.setLogType(logDao.getLogType(LogType.DEPLOYMENT_DESCR_UPDATED));
            log.setOldValue(deployment.getDescription());
            log.setNewValue(description);
            log.setProject(deployment.getProjectStage().getProject());
            logs.add(log);
            deployment.setDescription(description);
        }
        if (removed != null && removed != deployment.isRemoved()) {
            ProjectLog log = new ProjectLog();
            log.setAuthor(user);
            log.setLogType(logDao.getLogType(LogType.DEPLOYMENT_REMOVED_UPDATED));
            log.setOldValue(((Boolean)deployment.isRemoved()).toString());
            log.setNewValue(removed.toString());
            log.setProject(deployment.getProjectStage().getProject());
            logs.add(log);
            deployment.setRemoved(removed);
        }

        deploymentDao.update(deployment);
        for (ProjectLog log : logs) {
            logDao.create(log);
        }
        response.writeJson(new DeploymentDTO(deployment));
    }

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        int userId = request.getCurrentUserId();
        String name = request.requireNotBlankParameterString("name");
        String url = request.requireNotBlankParameterString("url");
        int stageId = request.requirePositiveParameterInteger("stage");
        String version = request.getParameterTrimmedString("version");
        String description = request.getParameterTrimmedString("description");

        ProjectStage stage = projectStageDao.get(stageId);
        if (stage == null) {
            throw new NotFoundException("Stage not found");
        }
        if (!request.isUserAdmin() && !projectDao.isMember(userId, stage.getProjectId())) {
            throw new ForbiddenException("Not a member");
        }

        Deployment deployment = new Deployment();
        deployment.setName(name);
        deployment.setUrl(url);
        deployment.setProjectStage(stage);
        if (version != null) {
            deployment.setVersion(version);
        }
        if (description != null) {
            deployment.setDescription(description);
        }

        deploymentDao.create(deployment);
        logDao.log(LogType.DEPLOYMENT_CREATED, request.getCurrentUser(), stage.getProject(), null, deployment.getName());
        response.writeJson(new DeploymentDTO(deployment));
    }

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        Integer userId = request.getCurrentUserId();
        Integer deploymentId = request.getParameterPositiveInteger("id");
        Integer stageId = request.getParameterPositiveInteger("stage");

        if (deploymentId != null) {
            Deployment deployment = deploymentDao.get(deploymentId);
            if (deployment == null) {
                throw new NotFoundException("Deployment not found");
            }
            if (!request.isUserAdmin() && !projectDao.isMember(userId, deployment.getProjectStage().getProjectId())) {
                throw new ForbiddenException("Not a project member");
            }
            response.writeJson(new DeploymentDTO(deployment));
        } else if (stageId != null) {
            ProjectStage stage = projectStageDao.get(stageId);
            if (stage == null) {
                throw new NotFoundException("Stage not found");
            }
            if (!request.isUserAdmin() && !projectDao.isMember(userId, stage.getProjectId())) {
                throw new ForbiddenException("Not a project member");
            }
            response.writeJson(stage.getDeployments().stream().map(DeploymentDTO::new).collect(Collectors.toList()));
        } else {
            throw new BadRequestException("No ids provided");
        }
    }
}
