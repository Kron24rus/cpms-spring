package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.EmployeeInfo;
import com.fireway.cpms.model.EmployeePosition;
import com.fireway.cpms.model.User;
import com.fireway.cpms.model.expand.UserExpansion;
import org.hibernate.criterion.Criterion;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserDAO extends GenericDAO<User, Integer> {
    User authenticate(String login, String password) throws DataAccessException, InvalidKeySpecException, NoSuchAlgorithmException;

    User get(int userId) throws DataAccessException;

    User getEntityWithRestrictions(Criterion... criteria) throws DataAccessException;

    User getByLogin(String login) throws DataAccessException;

    User getAndExpand(int userId, UserExpansion... expand) throws DataAccessException;

    EmployeePosition getPosition(int positionId) throws DataAccessException;

    void create(User user, EmployeeInfo info) throws DataAccessException;
}
