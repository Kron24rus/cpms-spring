package com.fireway.cpms.service;


import com.fireway.cpms.dao.impl.MessageDaoImpl;
import com.fireway.cpms.dao.impl.UserDaoImpl;
import com.fireway.cpms.dto.MessageDTO;
import com.fireway.cpms.exception.BadRequestException;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.exception.ForbiddenException;
import com.fireway.cpms.exception.NotFoundException;
import com.fireway.cpms.model.Message;
import com.fireway.cpms.model.User;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "message", urlPatterns = "/message")
public class MessageServlet extends GenericServlet {
    private static UserDaoImpl userDao = new UserDaoImpl();
    private static MessageDaoImpl messageDao = new MessageDaoImpl();

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        List<Message> messages = messageDao.getReceivedMessages(request.getCurrentUserId());
        response.writeJson(messages.stream().map(MessageDTO::new).collect(Collectors.toList()));
    }

    @Override
    protected void handlePost(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        String content = request.requireNotEmptyParameterString("content");
        Integer targetId = request.requireParameterInteger("target");

        User author = userDao.get(request.getCurrentUserId());
        User target = userDao.get(targetId);
        if (target == null) {
            throw new NotFoundException("Target user not found");
        }

        Message message = new Message();
        message.setContent(content);
        message.setCreationDate(new Timestamp(new Date().getTime()));
        message.setAuthor(author);
        message.setTarget(target);

        messageDao.create(message);

        response.writeJson(new MessageDTO(message));
    }
}
