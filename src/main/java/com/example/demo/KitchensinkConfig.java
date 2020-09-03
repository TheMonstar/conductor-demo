package com.example.demo;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.client.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class KitchensinkConfig {

    public static final String ROOT_URI = "http://localhost:8080/api/";

    @Autowired
    public void initFlow(TaskClient client, List<Worker> workerList) {
        var configurer = new TaskRunnerConfigurer.Builder(client, workerList)
                .withThreadCount(8)
                .build();
        configurer.init();
    }

    @Bean
    public TaskClient taskClient() {
        var taskClient = new TaskClient();
        taskClient.setRootURI(ROOT_URI);

        return taskClient;
    }

    @Bean
    public WorkflowClient workflowClient() {
        var workflowClient = new WorkflowClient();
        workflowClient.setRootURI(ROOT_URI);

        return workflowClient;
    }

    @Bean
    public MetadataClient metadataClient() {
        var client = new MetadataClient();
        client.setRootURI(ROOT_URI);

        return client;
    }
}
