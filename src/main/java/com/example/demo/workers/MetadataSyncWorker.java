package com.example.demo.workers;

import org.springframework.stereotype.Service;

@Service
public class MetadataSyncWorker extends AbstractWorker implements Ingestion {

    @Override
    public String getTaskDefName() {
        return "metasync";
    }
}
