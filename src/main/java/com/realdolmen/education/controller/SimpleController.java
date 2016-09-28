package com.realdolmen.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.WebSocketHandler;

import java.io.IOException;

@Controller
@RequestMapping("/socket")
public class SimpleController {

    @Autowired
    WebSocketHandler handler;

    @RequestMapping(method = RequestMethod.GET)
    public String sendMessageToAllWebSockets() throws IOException {
        ((MyWebSocketHandler) handler).sendMessageToSockets("Hey! Somebody used the web interface!");
        return "redirect:/";
    }
}
