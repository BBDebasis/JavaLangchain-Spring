package com.langchain.service;

import dev.langchain4j.service.*;

public interface MultiToolAgent {

    @SystemMessage("""
        You are a smart multi-tool agent. Decide whether to:
        - answer normally
        - call the time tool
        - call the math tool
    """)
    String chat(String query);
}

