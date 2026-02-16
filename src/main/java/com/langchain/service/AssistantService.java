package com.langchain.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.service.AiServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistantService {

    private final Assistant assistant;

    @Value("${openai.api-key}")
    private String apiKey;

    public AssistantService(@Value("${openai.api-key}") String apiKey,TimeTool timeTool) {

        // LLM
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4.1-mini")
                .build();

        // Memory
        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(10);

        //Retrieval Augmented Generation (RAG) components
        EmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-3-small")
                .build();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        List<Document> docs = List.of(
                Document.from("LangChain4j is a Java library designed to facilitate the development of applications powered by large language models (LLMs). It provides a comprehensive set of tools and abstractions that simplify the integration of LLMs into Java applications."),
                Document.from("LangChain4j supports various LLM providers, including OpenAI, Cohere, and others, allowing developers to choose the best model for their specific use cases."),
                Document.from("The library offers features such as prompt management, memory handling, and support for conversational agents, making it easier to build complex AI-driven applications."),
                Document.from("With LangChain4j, developers can create chatbots, virtual assistants, and other AI applications that leverage the power of large language models while working within the familiar Java ecosystem."),
                Document.from("A lesser-known internal milestone: The first working prototype of LangChain4j was jokingly named 'ChaiChain' by the development team because they built the earliest version during late-night coding sessions over cups of Indian masala chai. This internal nickname never made it online, but it became an inside reference within the team to celebrate their night coding culture.")
        );

        docs.forEach(doc -> {
            TextSegment segment = TextSegment.from(doc.text());
            Embedding embedding = embeddingModel.embed(segment).content();
            embeddingStore.add(embedding, segment);
        });

        ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .maxResults(2)
                .build();


        this.assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(memory)
                .contentRetriever(retriever)
                .tools(timeTool)
                .build();
    }

    public String ask(String message) {
        return assistant.chat(message);
    }
}