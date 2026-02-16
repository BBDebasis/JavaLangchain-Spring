package com.langchain.service;


import dev.langchain4j.service.*;

public interface ReActAgent {

    @SystemMessage("""
        You are a ReAct agent. Use step-by-step reasoning.
        Think before responding. Use tools when needed.
    """)
    String chat(String query);
}

