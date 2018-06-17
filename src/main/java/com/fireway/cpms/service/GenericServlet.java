package com.fireway.cpms.service;

import com.fireway.cpms.LoggerWrapper;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GenericServlet extends HttpServlet {
    private static LoggerWrapper LOG = LoggerWrapper.getLogger(GenericServlet.class);

    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException,
            BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        throw new NotImplementedException("GET not implemented");
    }

    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException,
            BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException{
        throw new NotImplementedException("POST not implemented");
    }

    protected void handlePut(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException,
            BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        throw new NotImplementedException("PUT not implemented");
    }

    protected void handleDelete(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException,
            BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        throw new NotImplementedException("DELETE not implemented");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseWrapper response = new ResponseWrapper(resp);
        try {
            handleGet(new RequestWrapper(req), response);
        } catch (BadRequestException e) {
            response.setBadRequestError(e);
            LOG.error(e);
        } catch (DataAccessException e) {
            response.setInternalError(e);
            LOG.error(e);
        } catch (ForbiddenException e) {
            response.setForbiddenError(e);
            LOG.error(e);
        } catch (NotFoundException e) {
            response.setNotFoundError(e);
            LOG.error(e);
        } catch (NotImplementedException e) {
            response.setNotImplementedError(e);
            LOG.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestWrapper request = new RequestWrapper(req);
        ResponseWrapper response = new ResponseWrapper(resp);
        try {
            if (request.isDeleteMethod()) {
                handleDelete(request, response);
            } else if (request.isPutMethod()) {
                handlePut(request, response);
            } else {
                handlePost(new RequestWrapper(req), response);
            }
        } catch (BadRequestException e) {
            response.setBadRequestError(e);
            LOG.error(e);
        } catch (DataAccessException e) {
            response.setInternalError(e);
            LOG.error(e);
        } catch (ForbiddenException e) {
            response.setForbiddenError(e);
            LOG.error(e);
        } catch (NotFoundException e) {
            response.setNotFoundError(e);
            LOG.error(e);
        } catch (NotImplementedException e) {
            response.setNotImplementedError(e);
            LOG.error(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
