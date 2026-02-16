package com.langchain.service;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.*;
import dev.langchain4j.model.chat.*;
import dev.langchain4j.model.openai.*;
import dev.langchain4j.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReActAgentService {

    private final ReActAgent agent;

    public ReActAgentService(@Value("${openai.api-key}") String apiKey) {

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4.1-mini")
                .build();

        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(20);

        this.agent = AiServices.builder(ReActAgent.class)
                .chatLanguageModel(model)
                .chatMemory(memory)
                .tools(new TimeTool(), new MathTool())
                .build();
    }
    public String ask(String q) {
        return agent.chat(q);
    }
}

