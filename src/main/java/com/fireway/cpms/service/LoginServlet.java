package com.fireway.cpms.service;

import com.fireway.cpms.dao.impl.UserDaoImpl;
import com.fireway.cpms.dto.UserDTO;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.User;
import com.fireway.cpms.util.Routes;
import com.fireway.cpms.util.SessionWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static UserDaoImpl userDao = new UserDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionWrapper sessionWrapper = new SessionWrapper(request.getSession());

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (StringUtils.isNoneEmpty(login, password)) {
            try {
                User user = userDao.authenticate(login, password);
                if (user != null) {
                    sessionWrapper.setLoggedUserId(user.getId());
                    UserDTO userDTO = new UserDTO(user);
                    userDTO.setOwnUser(true);
                    request.setAttribute("user", userDTO);
                    response.sendRedirect(Routes.STARTUP);
                    return;
                }
            } catch (DataAccessException | InvalidKeySpecException | NoSuchAlgorithmException e) {

            }
        }
//        request.setAttribute("error", true);
//        request.getRequestDispatcher(Routes.LOGIN_HTML).forward(request, response);

        response.sendRedirect(Routes.LOGIN_HTML);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(Routes.LOGIN_HTML).forward(request, response);
    }
}
