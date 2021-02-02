package xyz.txcplus.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import xyz.txcplus.websocket.entity.Greeting;
import xyz.txcplus.websocket.entity.HelloMessage;

/**
 * ClassName: GreetingController
 * Description:
 * date: 2020/12/15 17:02
 *
 * @author TXC
 */
@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        // simulated delay
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
