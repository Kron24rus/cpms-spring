package com.fireway.cpms.service;



import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dto.ProjectTypeDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.ProjectType;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "type", urlPatterns = "/type")
public class TypeServlet extends GenericServlet {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        Integer id = request.getParameterPositiveInteger("id");

        if (id != null) {
            ProjectType type = projectDao.getProjectType(id);
            if (type == null) {
                throw new NotFoundException("Project type not found");
            }
            response.writeJson(new ProjectTypeDTO(type));
        } else {
            List<ProjectType> typeList = projectDao.getAllTypes();
            response.writeJson(typeList.stream().map(ProjectTypeDTO::new).collect(Collectors.toList()));
        }
    }
}
