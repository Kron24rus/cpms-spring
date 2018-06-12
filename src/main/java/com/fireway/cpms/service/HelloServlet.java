package com.fireway.cpms.service;

import com.fireway.cpms.dao.impl.UserDaoImpl;
import com.fireway.cpms.dto.UserDTO;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloServlet extends HttpServlet {
    private static UserDaoImpl userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        request.setCharacterEncoding("UTF-8");
        try {
            List<User> userList = userDao.getAll();
            List<UserDTO> userDTOS = new ArrayList<>();
            for (User user : userList) {
                userDTOS.add(new UserDTO(user));
        }
            response.getWriter().write(new Gson().toJson(userDTOS));
        } catch (DataAccessException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
