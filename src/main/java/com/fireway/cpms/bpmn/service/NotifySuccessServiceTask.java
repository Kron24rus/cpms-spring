package com.fireway.cpms.bpmn.service;

import com.fireway.cpms.dto.ProjectDTO;
import com.google.gson.Gson;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class NotifySuccessServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        ProjectDTO project = delegateExecution.getVariable("responseProject", ProjectDTO.class);
        String jsonProject = new Gson().toJson(project);
        delegateExecution.setVariable("jsonProject", jsonProject);
    }
}
