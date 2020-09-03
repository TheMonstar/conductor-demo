package com.example.demo.workers;

import org.springframework.stereotype.Service;

@Service
public class ThumbnailGenerationWorker extends AbstractWorker implements Ingestion {

    @Override
    public String getTaskDefName() {
        return "thumbnailGen";
    }
}
