package org.springaimcpclientsse.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BasicController {
    private final ChatClient chatClient;

    public BasicController(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {

        this.chatClient = chatClientBuilder
                .defaultSystem("Please prioritise context information for answering questions. Give short, concise and to the point answers.")
                .defaultToolCallbacks(tools)
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String query) {
        PromptTemplate promptTemplate = new PromptTemplate(query);
        Prompt prompt = promptTemplate.create();
        ChatClient.CallResponseSpec res = chatClient.prompt(prompt).call();

        return res.content();
    }

}
