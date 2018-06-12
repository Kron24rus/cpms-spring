package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.Message;

import java.util.List;

public interface MessageDAO extends GenericDAO<Message, Integer> {
    List<Message> getReceivedMessages(int userId) throws DataAccessException;
}
