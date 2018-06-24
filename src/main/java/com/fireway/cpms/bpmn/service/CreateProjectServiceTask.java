package com.fireway.cpms.bpmn.service;

import com.fireway.cpms.bpmn.dto.ProjectRequestDTO;
import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dao.impl.ProjectLogDaoImpl;
import com.fireway.cpms.dto.ProjectDTO;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.LogType;
import com.fireway.cpms.model.Project;
import com.fireway.cpms.model.User;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectServiceTask implements JavaDelegate {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    private static ProjectLogDaoImpl logDao = new ProjectLogDaoImpl();

    @Override
    public void execute(DelegateExecution delegateExecution) {
        ProjectRequestDTO projectDTO = delegateExecution.getVariable("projectDTO", ProjectRequestDTO.class);
        User user = delegateExecution.getVariable("user", User.class);
        Project project = new Project();
        try {
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setPriority(projectDTO.getPriority());
            project.setProjectType(projectDao.getProjectType(projectDTO.getType()));
            project.setActive(false);
            projectDao.create(project);

            logDao.log(LogType.PROJECT_CREATED, user, project);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProjectDTO responseProject = new ProjectDTO(project);
        delegateExecution.setVariable("responseProject", responseProject);
    }
}
