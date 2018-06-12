package com.fireway.cpms.service;


import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dao.impl.ProjectLogDaoImpl;
import com.fireway.cpms.dto.LogDTO;
import com.fireway.cpms.exception.BadRequestException;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.exception.ForbiddenException;
import com.fireway.cpms.exception.NotFoundException;
import com.fireway.cpms.model.ProjectLog;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LogServlet extends GenericServlet{
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    private static ProjectLogDaoImpl logDao = new ProjectLogDaoImpl();

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Integer projectId = request.getParameterPositiveInteger("project");
        Integer userId = request.getCurrentUserId();
        List<ProjectLog> log;

        if (projectId != null) {
            if (!request.isUserAdmin() && !projectDao.isMember(userId, projectId)) {
                throw new ForbiddenException("Not a project member or admin");
            }
            log = logDao.getForProject(projectId);
        } else {
            if (!request.isUserAdmin()) {
                throw new ForbiddenException("Not an admin");
            }
            log = logDao.getAll();
        }
        response.writeJson(log.stream().map(LogDTO::new).collect(Collectors.toList()));
    }

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Boolean clear = request.getParameterBoolean("clear");

        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }
        if (clear) {
            logDao.clear();
        }
        response.setNoContentStatus();
    }
}
