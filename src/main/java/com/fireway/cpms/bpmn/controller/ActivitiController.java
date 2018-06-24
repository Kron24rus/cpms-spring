package com.fireway.cpms.bpmn.controller;

import com.fireway.cpms.bpmn.dto.ProjectRequestDTO;
import com.fireway.cpms.exception.DataAccessException;
import com.fireway.cpms.model.User;
import com.fireway.cpms.util.RequestWrapper;
import com.google.common.collect.ImmutableMap;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableScopeImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class ActivitiController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/activiti/project", produces = "application/json")
    public Object createProject(HttpServletRequest req, HttpServletResponse resp, @RequestBody ProjectRequestDTO projectDTO) throws DataAccessException {
        RequestWrapper requestWrapper = new RequestWrapper(req);
        User user = requestWrapper.getCurrentUser();
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("myProcess", ImmutableMap.of("projectDTO", projectDTO,
                        "isAdmin", requestWrapper.isUserAdmin(),
                        "user", user));
        boolean success = (boolean) ((ExecutionEntityImpl) processInstance).getVariables().get("valid");
        Map<String, Object> processObjects = ((ExecutionEntityImpl) processInstance).getVariables();
        if (success) {
            return processObjects.get("jsonProject");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(processObjects.get("errorMessage"));
        }
    }
}
