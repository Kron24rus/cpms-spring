package com.fireway.cpms.service;

import com.fireway.cpms.dao.impl.MessageDaoImpl;
import com.fireway.cpms.dao.impl.UserDaoImpl;
import com.fireway.cpms.dto.UserDTO;
import com.fireway.cpms.dto.extra.DialogDTO;
import com.fireway.cpms.dto.extra.DialogEntryDTO;
import com.fireway.cpms.dto.extra.DialogListDTO;
import com.fireway.cpms.exception.BadRequestException;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.exception.ForbiddenException;
import com.fireway.cpms.exception.NotFoundException;
import com.fireway.cpms.model.Message;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "dialog", urlPatterns = "/dialog")
public class DialogServlet extends GenericServlet {
    private static UserDaoImpl userDao = new UserDaoImpl();
    private static MessageDaoImpl messageDao = new MessageDaoImpl();

    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException {
        Integer id = request.getParameterPositiveInteger("id");
        if (id != null) {
            List<Message> messages = messageDao.getDialog(request.getCurrentUserId(), id);
            DialogDTO dialogDTO = new DialogDTO();
            if (messages != null) {
                for (Message message : messages) {
                    dialogDTO.addEntry(message);
                }
            } else {
                dialogDTO.setEntries(new ArrayList<>());
            }
            response.writeJson(dialogDTO);
        } else {
            List<Message> messages = messageDao.getReceivedMessages(request.getCurrentUserId());
            response.writeJson(new DialogListDTO(messages));
        }
    }
}
