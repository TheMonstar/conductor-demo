package com.example.demo;

import com.example.demo.builders.WorkflowTaskBuilder;
import com.google.common.collect.ImmutableMap;
import com.netflix.conductor.client.exceptions.ConductorClientException;
import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.TaskType;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/workflow")
public class KitchensinkController {

    public static final String NAME = "ingestion";
    public static final int VERSION = 1;
    private WorkflowClient workflowClient;
    private MetadataClient metadataClient;

    public KitchensinkController(WorkflowClient workflowClient, MetadataClient metadataClient) {
        this.workflowClient = workflowClient;
        this.metadataClient = metadataClient;
    }

    @PostConstruct
    public void init() {
        try {
//            metadataClient.unregisterWorkflowDef(NAME, VERSION);
            metadataClient.getWorkflowDef(NAME, VERSION);
        } catch (ConductorClientException e) {
            var definition = new WorkflowDef();
            definition.setName(NAME);
            definition.setVersion(VERSION);
            definition.setDescription("Simple ingestion description");
            definition.setOwnerEmail("vkorzh@luxoft.com");

            /*
            -antivirus
            -extraction
            -createAsset
            >caseProject
             *-adToProject
            *-upload
             >caseFileType
              *-video
               !notification
              *-thumbnailGen
               -thumbnailSave
               !notification
            *-updateHistory
            *-metasync
            -index
            !notification
             */
            var videoBranch = Arrays.asList(
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("video")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("video")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("case", "${workflow.input.case}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("videoNotify")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("videoNotify")
                            .withType(TaskType.TASK_TYPE_EVENT)
                            .withSink("conductor")
                            .withInputParameters(ImmutableMap.of("path", "${video.output.path}"))
                            .build()
            );
            var thumbnailBranch = Arrays.asList(
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("thumbnailGen")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("thumbnailGeneration")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("case", "${workflow.input.case}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("thumbnailSave")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("thumbnailSave")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("case", "${workflow.input.case}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("thumbnailNotify")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("thumbnailNotify")
                            .withType(TaskType.TASK_TYPE_EVENT)
                            .withSink("conductor")
                            .withInputParameters(ImmutableMap.of("path", "${thumbnailSave.output.path}"))
                            .build()
            );
            var uploadBranch = Arrays.asList(
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("upload")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("upload")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("case", "${workflow.input.case}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("decide")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("decide_file")
                            .withType(TaskType.TASK_TYPE_DECISION)
                            .withInputParameters(ImmutableMap.of("case", "${md_extractor.output.type}"))
                            .withCaseValueParam("case")
                            .withDecisionCases(ImmutableMap.of("video", videoBranch, "image", thumbnailBranch))
                            .build()
            );

            definition.setTasks(Arrays.asList(
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("antivirus")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("antivirus")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("case", "${workflow.input.case}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("md_extractor")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("md_extractor")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("ext", "${workflow.input.ext}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("createAsset")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("createAsset")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("metadata", "${md_extractor.output.*}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("fork")
                            .withType(TaskType.TASK_TYPE_FORK_JOIN)
                            .withTaskReferenceName("forkX3")
                            .withForkTasks(Arrays.asList(
                                    Arrays.asList(WorkflowTaskBuilder.aWorkflowTask()
                                            .withName("updateHistory")
                                            .withTaskDefinition(new TaskDef())
                                            .withTaskReferenceName("updateHistory")
                                            .withType(TaskType.TASK_TYPE_SIMPLE)
                                            .withInputParameters(ImmutableMap.of("id", "${createAsset.output.id}"))
                                            .build()),
                                    Arrays.asList(WorkflowTaskBuilder.aWorkflowTask()
                                            .withName("metasync")
                                            .withTaskDefinition(new TaskDef())
                                            .withTaskReferenceName("metasync")
                                            .withType(TaskType.TASK_TYPE_SIMPLE)
                                            .withInputParameters(ImmutableMap.of("metadata", "${md_extractor.output.*}"))
                                            .build()),
                                    uploadBranch
                            ))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("join")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("joinX3")
                            .withType(TaskType.TASK_TYPE_JOIN)
                            .withJoinOn(Arrays.asList("updateHistory", "metasync"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("index")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("index")
                            .withType(TaskType.TASK_TYPE_SIMPLE)
                            .withInputParameters(ImmutableMap.of("id", "${createAsset.output.id}"))
                            .build(),
                    WorkflowTaskBuilder.aWorkflowTask()
                            .withName("workflowNotify")
                            .withTaskDefinition(new TaskDef())
                            .withTaskReferenceName("workflowNotify")
                            .withType(TaskType.TASK_TYPE_EVENT)
                            .withSink("conductor")
                            .withInputParameters(ImmutableMap.of("status", "DONE"))
                            .build()
            ));
            try {
                metadataClient.registerWorkflowDef(definition);
            } catch (ConductorClientException e1) {
                System.out.println(e1.getValidationErrors());
            }
        }
    }

    @GetMapping
    public ResponseEntity test() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<String> startFlow() {
        var workflow = new StartWorkflowRequest();
        workflow.setName(NAME);
        var result = workflowClient.startWorkflow(workflow);

        return ResponseEntity.ok(result);
    }
}
