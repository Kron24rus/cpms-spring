package com.fireway.cpms.util;

import com.fireway.cpms.dao.impl.UserDaoImpl;
import com.fireway.cpms.exception.BadRequestException;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestWrapper {
    private static UserDaoImpl userDao = new UserDaoImpl();

    private HttpServletRequest request;
    private SessionWrapper session;

    public RequestWrapper(HttpServletRequest request) {
        this.request = request;
        this.session = new SessionWrapper(request.getSession());
    }

    public HttpServletRequest getInnerRequest() {
        return request;
    }

    public Integer getCurrentUserId() {
        if (session.isValid()) {
            return session.getLoggedUserId();
        }
        return null;
    }

    public User getCurrentUser() throws DataAccessException {
        Integer id = getCurrentUserId();

        if (id != null) {
            return userDao.get(id);
        }
        return null;
    }

    public boolean isUserAuthorized() {
        return getCurrentUserId() != null;
    }

    public boolean isUserAdmin() throws DataAccessException {
        User user = getCurrentUser();

        if (user != null) {
            return user.isAdmin();
        }
        return false;
    }

    public boolean isPostMethod() {
        return DataUtils.isPostMethod(getParameterString("_method"));
    }

    public boolean isPutMethod() {
        return DataUtils.isPutMethod(getParameterString("_method"));
    }

    public boolean isDeleteMethod() {
        return DataUtils.isDeleteMethod(getParameterString("_method"));
    }

    public String getParameterString(String parameter) {
        return request.getParameter(parameter);
    }

    public String getParameterTrimmedString(String parameter) {
        return StringUtils.trim(getParameterString(parameter));
    }

    public Integer getParameterInteger(String parameter) throws BadRequestException {
        String data = getParameterTrimmedString(parameter);
        if (data != null) {
            return DataUtils.parseInt(data);
        }
        return null;
    }

    public Integer getParameterNonNegativeInteger(String parameter) throws BadRequestException {
        Integer result = getParameterInteger(parameter);
        if (result != null && result < 0) {
            throw new BadRequestException("Negative int found");
        }
        return result;
    }

    public Integer getParameterPositiveInteger(String parameter) throws BadRequestException {
        Integer result = getParameterInteger(parameter);
        if (result != null && result <= 0) {
            throw new BadRequestException("Non-positive int found");
        }
        return result;
    }


    public Boolean getParameterBoolean(String parameter) throws BadRequestException {
        String data = getParameterTrimmedString(parameter);
        if (data != null) {
            return DataUtils.parseBoolean(data);
        }
        return null;
    }

    public String requireParameterString(String parameter) throws BadRequestException {
        String data = request.getParameter(parameter);
        if (data == null) {
            throw new BadRequestException("Parameter " + parameter + " is null");
        }
        return data;
    }

    public String requireNotEmptyParameterString(String parameter) throws BadRequestException {
        String data = request.getParameter(parameter);
        if (StringUtils.isEmpty(data)) {
            throw new BadRequestException("Parameter " + parameter + " is empty");
        }
        return data;
    }

    public String requireNotBlankParameterString(String parameter) throws BadRequestException {
        String data = request.getParameter(parameter);
        if (StringUtils.isBlank(data)) {
            throw new BadRequestException("Parameter " + parameter + " is blank");
        }
        return data;
    }

    public int requireParameterInteger(String parameter) throws BadRequestException {
        String data = StringUtils.trim(requireNotBlankParameterString(parameter));
        return DataUtils.parseInt(data);
    }

    public int requireNonNegativeParameterInteger(String parameter) throws BadRequestException {
        int result = requireParameterInteger(parameter);
        if (result < 0) {
            throw new BadRequestException("Parameter " + parameter + " is not positive");
        }
        return result;
    }

    public int requirePositiveParameterInteger(String parameter) throws BadRequestException {
        int result = requireParameterInteger(parameter);
        if (result <= 0) {
            throw new BadRequestException("Parameter " + parameter + " is not positive");
        }
        return result;
    }

    public boolean requireParameterBoolean(String parameter) throws BadRequestException {
        String data = StringUtils.trim(requireNotBlankParameterString(parameter));
        return DataUtils.parseBoolean(data);
    }
}
