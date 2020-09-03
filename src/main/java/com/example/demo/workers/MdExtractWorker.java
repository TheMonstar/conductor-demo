package com.example.demo.workers;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.stereotype.Service;

@Service
public class MdExtractWorker implements Ingestion {

    @Override
    public String getTaskDefName() {
        return "md_extractor";
    }

    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);

        String ext = (String) task.getInputData().get("ext");

        //Register the output of the task
        switch (ext) {
            case "jpg" ->  result.getOutputData().put("type", "image");
            case "mp4" ->  result.getOutputData().put("type", "video");
        }
        result.getOutputData().put("title", "video");

        return result;
    }
}
