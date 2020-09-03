package com.example.demo.workers;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.stereotype.Service;

@Service
public class ThumbnailSaveWorker implements Ingestion {

    @Override
    public String getTaskDefName() {
        return "thumbnailSave";
    }

    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);

        result.getOutputData().put("path", "/localhost");

        return result;
    }
}
