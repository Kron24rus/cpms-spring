package com.fireway.cpms.dao;


import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.EmployeePosition;

public interface PositionDAO extends GenericDAO<EmployeePosition,Integer> {
    EmployeePosition getPosition(int id) throws DataAccessException;
}
