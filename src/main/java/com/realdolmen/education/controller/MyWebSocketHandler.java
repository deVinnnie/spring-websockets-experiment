package com.realdolmen.education.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple SocketHandler which echo's every input back to the client.
 * The connection is closed when an empty message is received.
 *
 */
public class MyWebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    private Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        logger.info("Incoming connection from " + session.getRemoteAddress().toString());
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Servlet alike system: A single WebSocketHandler handles many requests.
        logger.info(Integer.toString(this.hashCode()));

        logger.info(message.getPayload());


        if(message.getPayload().isEmpty()){
            session.sendMessage(new TextMessage("Closing Connection"));
            session.close();
        }
        else {
            this.sendMessageToSockets(message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        logger.info("Closing Connection!");
        sessions.remove(session);
        logger.info("Number of connections: " + sessions.size());
    }


    public void sendMessageToSockets(String message) throws IOException {
        for(WebSocketSession aSession : sessions) {
            if(aSession.isOpen()) {
                // Not a complete solution.
                // Thread may be suspended after this call and then the session may be closed.

                aSession.sendMessage(new TextMessage(message));
            }
        }

    }
}
