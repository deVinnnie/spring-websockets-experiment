package com.realdolmen.education.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Simple SocketHandler which echo's every input back to the client.
 * The connection is closed when an empty message is received.
 *
 */
public class MyWebSocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        logger.info("Incoming connection from " + session.getRemoteAddress().toString());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.info(message.getPayload());

        if(message.getPayload().isEmpty()){
            session.sendMessage(new TextMessage("Closing Connection"));
            session.close();
        }
        else {
            session.sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        logger.info("Closing Connection!");
    }
}
