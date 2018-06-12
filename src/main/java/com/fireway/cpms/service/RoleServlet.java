package com.fireway.cpms.service;


import com.fireway.cpms.dao.impl.ProjectRoleDaoImpl;
import com.fireway.cpms.dto.ProjectRoleDTO;
import com.fireway.cpms.exception.BadRequestException;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.exception.ForbiddenException;
import com.fireway.cpms.exception.NotFoundException;
import com.fireway.cpms.model.ProjectRole;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "role", urlPatterns = "/role")
public class RoleServlet extends GenericServlet {
    private static ProjectRoleDaoImpl roleDao = new ProjectRoleDaoImpl();

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Integer id =  request.getParameterPositiveInteger("id");
        if (id == null) {
            response.writeJson(roleDao.getAll().stream().map(ProjectRoleDTO::new).collect(Collectors.toList()));
        } else {
            ProjectRole role = roleDao.get(id);
            if (role == null) {
                throw new NotFoundException("Role with id " + id + " not found");
            }
            response.writeJson(new ProjectRoleDTO(role));
        }
    }
}
