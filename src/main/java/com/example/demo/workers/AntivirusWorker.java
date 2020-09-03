package com.example.demo.workers;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.stereotype.Service;

@Service
public class AntivirusWorker extends AbstractWorker implements Ingestion {

    @Override
    public String getTaskDefName() {
        return "antivirus";
    }
}
