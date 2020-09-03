package com.example.demo.builders;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.SubWorkflowParams;
import com.netflix.conductor.common.metadata.workflow.TaskType;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;

import javax.validation.Valid;
import java.util.*;

public final class WorkflowTaskBuilder {
    private String name;
    private String taskReferenceName;
    private String description;
    private Map<String, Object> inputParameters = new HashMap<>();
    private String type = TaskType.SIMPLE.name();
    private String dynamicTaskNameParam;
    private String caseValueParam;
    private String caseExpression;
    private String scriptExpression;
    //Populates for the tasks of the decision type
    private Map<String, @Valid List<@Valid WorkflowTask>> decisionCases = new LinkedHashMap<>();
    private String dynamicForkJoinTasksParam;
    private String dynamicForkTasksParam;
    private String dynamicForkTasksInputParamName;
    private List<@Valid WorkflowTask> defaultCase = new LinkedList<>();
    private List<@Valid List<@Valid WorkflowTask>> forkTasks = new LinkedList<>();
    private int startDelay;    //No. of seconds (at-least) to wait before starting a task.
    private SubWorkflowParams subWorkflowParam;
    private List<String> joinOn = new LinkedList<>();
    private String sink;
    private boolean optional = false;
    private TaskDef taskDefinition;
    private Boolean rateLimited;
    private List<String> defaultExclusiveJoinTask = new LinkedList<>();
    private Boolean asyncComplete = false;
    private String loopCondition;
    private List<WorkflowTask> loopOver = new LinkedList<>();

    private WorkflowTaskBuilder() {
    }

    public static WorkflowTaskBuilder aWorkflowTask() {
        return new WorkflowTaskBuilder();
    }

    public WorkflowTaskBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public WorkflowTaskBuilder withTaskReferenceName(String taskReferenceName) {
        this.taskReferenceName = taskReferenceName;
        return this;
    }

    public WorkflowTaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public WorkflowTaskBuilder withInputParameters(Map<String, Object> inputParameters) {
        this.inputParameters = inputParameters;
        return this;
    }

    public WorkflowTaskBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public WorkflowTaskBuilder withDynamicTaskNameParam(String dynamicTaskNameParam) {
        this.dynamicTaskNameParam = dynamicTaskNameParam;
        return this;
    }

    public WorkflowTaskBuilder withCaseValueParam(String caseValueParam) {
        this.caseValueParam = caseValueParam;
        return this;
    }

    public WorkflowTaskBuilder withCaseExpression(String caseExpression) {
        this.caseExpression = caseExpression;
        return this;
    }

    public WorkflowTaskBuilder withScriptExpression(String scriptExpression) {
        this.scriptExpression = scriptExpression;
        return this;
    }

    public WorkflowTaskBuilder withDecisionCases(Map<String, List<WorkflowTask>> decisionCases) {
        this.decisionCases = decisionCases;
        return this;
    }

    public WorkflowTaskBuilder withDynamicForkJoinTasksParam(String dynamicForkJoinTasksParam) {
        this.dynamicForkJoinTasksParam = dynamicForkJoinTasksParam;
        return this;
    }

    public WorkflowTaskBuilder withDynamicForkTasksParam(String dynamicForkTasksParam) {
        this.dynamicForkTasksParam = dynamicForkTasksParam;
        return this;
    }

    public WorkflowTaskBuilder withDynamicForkTasksInputParamName(String dynamicForkTasksInputParamName) {
        this.dynamicForkTasksInputParamName = dynamicForkTasksInputParamName;
        return this;
    }

    public WorkflowTaskBuilder withDefaultCase(List<WorkflowTask> defaultCase) {
        this.defaultCase = defaultCase;
        return this;
    }

    public WorkflowTaskBuilder withForkTasks(List<List<WorkflowTask>> forkTasks) {
        this.forkTasks = forkTasks;
        return this;
    }

    public WorkflowTaskBuilder withStartDelay(int startDelay) {
        this.startDelay = startDelay;
        return this;
    }

    public WorkflowTaskBuilder withSubWorkflowParam(SubWorkflowParams subWorkflowParam) {
        this.subWorkflowParam = subWorkflowParam;
        return this;
    }

    public WorkflowTaskBuilder withJoinOn(List<String> joinOn) {
        this.joinOn = joinOn;
        return this;
    }

    public WorkflowTaskBuilder withSink(String sink) {
        this.sink = sink;
        return this;
    }

    public WorkflowTaskBuilder withOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public WorkflowTaskBuilder withTaskDefinition(TaskDef taskDefinition) {
        this.taskDefinition = taskDefinition;
        return this;
    }

    public WorkflowTaskBuilder withRateLimited(Boolean rateLimited) {
        this.rateLimited = rateLimited;
        return this;
    }

    public WorkflowTaskBuilder withDefaultExclusiveJoinTask(List<String> defaultExclusiveJoinTask) {
        this.defaultExclusiveJoinTask = defaultExclusiveJoinTask;
        return this;
    }

    public WorkflowTaskBuilder withAsyncComplete(Boolean asyncComplete) {
        this.asyncComplete = asyncComplete;
        return this;
    }

    public WorkflowTaskBuilder withLoopCondition(String loopCondition) {
        this.loopCondition = loopCondition;
        return this;
    }

    public WorkflowTaskBuilder withLoopOver(List<WorkflowTask> loopOver) {
        this.loopOver = loopOver;
        return this;
    }

    public WorkflowTask build() {
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setName(name);
        workflowTask.setTaskReferenceName(taskReferenceName);
        workflowTask.setDescription(description);
        workflowTask.setInputParameters(inputParameters);
        workflowTask.setType(type);
        workflowTask.setDynamicTaskNameParam(dynamicTaskNameParam);
        workflowTask.setCaseValueParam(caseValueParam);
        workflowTask.setCaseExpression(caseExpression);
        workflowTask.setScriptExpression(scriptExpression);
        workflowTask.setDecisionCases(decisionCases);
        workflowTask.setDynamicForkJoinTasksParam(dynamicForkJoinTasksParam);
        workflowTask.setDynamicForkTasksParam(dynamicForkTasksParam);
        workflowTask.setDynamicForkTasksInputParamName(dynamicForkTasksInputParamName);
        workflowTask.setDefaultCase(defaultCase);
        workflowTask.setForkTasks(forkTasks);
        workflowTask.setStartDelay(startDelay);
        workflowTask.setSubWorkflowParam(subWorkflowParam);
        workflowTask.setJoinOn(joinOn);
        workflowTask.setSink(sink);
        workflowTask.setOptional(optional);
        workflowTask.setTaskDefinition(taskDefinition);
        workflowTask.setRateLimited(rateLimited);
        workflowTask.setDefaultExclusiveJoinTask(defaultExclusiveJoinTask);
        workflowTask.setAsyncComplete(asyncComplete);
        workflowTask.setLoopCondition(loopCondition);
        workflowTask.setLoopOver(loopOver);
        return workflowTask;
    }
}
