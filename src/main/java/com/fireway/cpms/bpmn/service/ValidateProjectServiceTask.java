package com.fireway.cpms.bpmn.service;

import com.fireway.cpms.bpmn.dto.ProjectRequestDTO;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ValidateProjectServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        ProjectRequestDTO project = delegateExecution.getVariable("projectDTO", ProjectRequestDTO.class);
        delegateExecution.setVariable("valid", true);
    }
}
