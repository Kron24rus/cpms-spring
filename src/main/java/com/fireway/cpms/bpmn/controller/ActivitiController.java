package com.fireway.cpms.bpmn.controller;

import com.fireway.cpms.bpmn.dto.ProjectRequestDTO;
import com.google.common.collect.ImmutableMap;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivitiController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/activiti/project", produces = "application/json")
    public Object createProject(@RequestBody ProjectRequestDTO projectDTO) {
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("myProcess", ImmutableMap.of("projectDTO", projectDTO));
        return ((ExecutionEntityImpl) processInstance).getVariables().get("responseProject");
    }
}
