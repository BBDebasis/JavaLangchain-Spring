package com.langchain.Controller;



import com.langchain.service.AssistantService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AssistantService assistantService;

    public AiController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return assistantService.ask(q);
    }
}

