package com.fireway.cpms.service;


import com.fireway.cpms.dao.impl.PositionDaoImpl;
import com.fireway.cpms.dto.EmployeePositionDTO;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.model.EmployeePosition;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PositionServlet extends GenericServlet {
    private static PositionDaoImpl positionDao = new PositionDaoImpl();

    @Override
    protected void handleDelete(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }

        Integer id = request.requirePositiveParameterInteger("id");
        EmployeePosition position = positionDao.getPosition(id);
        if (position == null) {
            throw new NotFoundException("Position " + id + " not found");
        }
        positionDao.delete(position);
        response.setNoContentStatus();
    }

    @Override
    protected void handlePut(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }

        Integer id = request.requirePositiveParameterInteger("id");
        String name = request.getParameterTrimmedString("name");
        String description = request.getParameterTrimmedString("description");

        EmployeePosition position = positionDao.getPosition(id);
        if (position == null) {
            throw new NotFoundException("Position " + id + " not found");
        }
        if (name != null) {
            position.setName(name);
        }
        if (description != null) {
            position.setDescription(description);
        }
        positionDao.update(position);
        response.writeJson(new EmployeePositionDTO(position));
    }

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Integer id = request.getParameterPositiveInteger("id");
        if (id != null) {
            EmployeePosition position = positionDao.getPosition(id);
            if (position == null) {
                throw new NotFoundException("Position " + id + " not found");
            }
            response.writeJson(new EmployeePositionDTO(position));
        } else {
            List<EmployeePositionDTO> list =
                    positionDao.getAll().stream().map(EmployeePositionDTO::new).collect(Collectors.toList());
            response.writeJson(list);
        }
    }

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        if (!request.isUserAdmin()) {
            throw new ForbiddenException("Not an admin");
        }

        String name = request.requireNotBlankParameterString("name");
        String description = request.getParameterTrimmedString("description");

        EmployeePosition position = new EmployeePosition();
        position.setName(name);
        position.setDescription(description);

        positionDao.create(position);
        response.writeJson(new EmployeePositionDTO(position));
    }
}
