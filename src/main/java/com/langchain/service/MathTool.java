package com.langchain.service;


import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class MathTool {

    @Tool("Add two numbers")
    public String add(int a, int b) {
        System.out.println("Adding " + a + " and " + b);
        return "Sum = " + (a + b);
    }
}

