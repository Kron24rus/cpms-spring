package com.fireway.cpms.bpmn.service;

import com.fireway.cpms.bpmn.dto.ProjectRequestDTO;
import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.exception.BadRequestException;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.exception.ForbiddenException;
import com.fireway.cpms.exception.NotFoundException;
import com.fireway.cpms.model.ProjectType;
import com.fireway.cpms.util.RequestWrapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidateProjectServiceTask implements JavaDelegate {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();

    @Override
    public void execute(DelegateExecution delegateExecution) {
        ProjectRequestDTO project = delegateExecution.getVariable("projectDTO", ProjectRequestDTO.class);
        Boolean isAdmin = delegateExecution.getVariable("isAdmin", Boolean.class);
        Map<String, Object> validationResult = new HashMap<>();
        boolean isValid = true;
        String errorMessage = null;
        try {
            if (!isAdmin) {
                throw new ForbiddenException("Not an admin");
            }
            if (project.getName() == null) isValid = false;
            if (project.getType() <= 0) isValid = false;
            ProjectType type = projectDao.getProjectType(project.getType());
            if (type == null) {
                throw new NotFoundException("Type " + project.getType() + " not found");
            }
        } catch (ForbiddenException | NotFoundException | DataAccessException e) {
            isValid = false;
            errorMessage = e.getMessage();
        }
        validationResult.put("validationMessage", errorMessage);
        validationResult.put("valid", isValid);
        delegateExecution.setVariables(validationResult);
    }
}
