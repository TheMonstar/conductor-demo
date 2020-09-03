package com.example.demo.workers;

import org.springframework.stereotype.Service;

@Service
public class UploadWorker extends AbstractWorker implements Ingestion,NotUsed {

    @Override
    public String getTaskDefName() {
        return "upload";
    }
}
