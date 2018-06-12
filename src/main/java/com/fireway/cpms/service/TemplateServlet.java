package com.fireway.cpms.service;



import com.fireway.cpms.dao.impl.ProjectStageTemplateDaoImpl;
import com.fireway.cpms.dto.ProjectStageTemplateDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.ProjectStageTemplate;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "template", urlPatterns = "/template")
public class TemplateServlet extends GenericServlet{
    private static ProjectStageTemplateDaoImpl templateDao = new ProjectStageTemplateDaoImpl();

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        List<ProjectStageTemplate> list = templateDao.getAll();
        response.writeJson(list.stream().map(ProjectStageTemplateDTO::new).collect(Collectors.toList()));
    }

    @Override
    protected void handlePut(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }
        Integer id = request.requirePositiveParameterInteger("id");
        String name = request.getParameterTrimmedString("name");
        String description = request.getParameterTrimmedString("description");

        ProjectStageTemplate template = templateDao.get(id);
        if (template == null) {
            throw new NotFoundException("Template not found");
        }
        if (name != null) {
            template.setName(name);
        }
        if (description != null) {
            template.setDescription(description);
        }

        templateDao.update(template);
        response.writeJson(new ProjectStageTemplateDTO(template));
    }

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }

        String name = request.requireNotBlankParameterString("name");
        String description = request.getParameterTrimmedString("description");

        ProjectStageTemplate template = new ProjectStageTemplate();
        template.setName(name);
        if (description != null) {
            template.setDescription(description);
        }
        templateDao.create(template);
        response.writeJson(new ProjectStageTemplateDTO(template));
    }
}
