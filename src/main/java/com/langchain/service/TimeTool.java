package com.langchain.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeTool {

    @Tool("Get current system time")
    public String now() {
        return "Time: " + LocalDateTime.now();
    }
}

