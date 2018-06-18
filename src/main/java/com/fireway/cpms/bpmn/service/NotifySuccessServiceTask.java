package com.fireway.cpms.bpmn.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class NotifySuccessServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {

    }
}
