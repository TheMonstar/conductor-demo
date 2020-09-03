package com.example.demo.workers;

import org.springframework.stereotype.Service;

@Service
public class SomeWorker extends AbstractWorker implements NotUsed {

    @Override
    public String getTaskDefName() {
        return "upload";
    }
}
