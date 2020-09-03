package com.example.demo.workers;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.stereotype.Service;

@Service
public class AssetCreatWorker implements Ingestion {

    @Override
    public String getTaskDefName() {
        return "createAsset";
    }

    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);

        result.getOutputData().put("id", 1111);

        return result;
    }
}
