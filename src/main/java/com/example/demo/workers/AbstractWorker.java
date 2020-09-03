package com.example.demo.workers;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractWorker {
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);

        //Register the output of the task
        result.getOutputData().put("outputKey1", "value");
        result.getOutputData().put("oddEven", 0);
        result.getOutputData().put("mod", 4);

        log.info(task.toString());
        return result;
    }
}
