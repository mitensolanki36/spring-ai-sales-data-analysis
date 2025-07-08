package com.ms.salesanalysis.aiSalesAnalysis.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LlmService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public LlmService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public void addDocumentsToVectorStore(List<Document> documents) {
        vectorStore.add(documents);
    }

    public String chat(String userQuery) {
        // Retrieve relevant documents from the vector store
        List<Document> similarDocuments = vectorStore.similaritySearch(userQuery);
        String context = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        // Create a system message to provide context to the LLM
        String systemMessageContent = """
                You are a helpful AI assistant that analyzes sales data.
                You are given a subset of sales data as context.
                Answer the user's question based on the provided context.
                If the answer is not in the context, say "I don't have enough information to answer that."
                Context:
                {context}
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemMessageContent);
        var systemMessage = systemPromptTemplate.createMessage(Map.of("context", context));

        Prompt prompt = new Prompt(List.of(systemMessage, new org.springframework.ai.chat.messages.UserMessage(userQuery)));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}

