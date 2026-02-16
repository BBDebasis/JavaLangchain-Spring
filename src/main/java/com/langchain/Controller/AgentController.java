package com.langchain.Controller;

import com.langchain.service.MultiToolAgentService;
import com.langchain.service.ReActAgentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final MultiToolAgentService multiTool;
    private final ReActAgentService reactAgent;

    public AgentController(
            MultiToolAgentService multiTool,
            ReActAgentService reactAgent
    ) {
        this.multiTool = multiTool;
        this.reactAgent = reactAgent;
    }

    @GetMapping("/multi")
    public String askMulti(@RequestParam String q) {
        return multiTool.ask(q);
    }

    @GetMapping("/react")
    public String askReact(@RequestParam String q) {
        return reactAgent.ask(q);
    }
}

