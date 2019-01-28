package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/x/{msg}")
    @ResponseBody
    public void send(@PathVariable String msg) {
        template.convertAndSend("/topic/greetings", new Greeting(HtmlUtils.htmlEscape("Admin: " + msg)));
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) {
        return new Greeting(HtmlUtils.htmlEscape(message.getName() + ": " + message.getMsg()));
    }

}
