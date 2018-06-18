package com.fireway.cpms.bpmn.service;

import com.fireway.cpms.bpmn.dto.ProjectRequestDTO;
import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dto.ProjectDTO;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.Project;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectServiceTask implements JavaDelegate {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();

    @Override
    public void execute(DelegateExecution delegateExecution) {
        ProjectRequestDTO projectDTO = delegateExecution.getVariable("projectDTO", ProjectRequestDTO.class);
        Project project = new Project();
        try {
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setPriority(projectDTO.getPriority());
            project.setProjectType(projectDao.getProjectType(projectDTO.getType()));
            project.setActive(false);
            projectDao.create(project);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        delegateExecution.setVariable("responseProject", new ProjectDTO(project));
    }
}
