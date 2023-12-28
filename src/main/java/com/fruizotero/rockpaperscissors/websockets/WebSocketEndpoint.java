package com.fruizotero.rockpaperscissors.websockets;


import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/rockpaperscissors/{idRoom}")
public class WebSocketEndpoint {

    private Map<String, Room> rooms = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("idRoom") String idRoom) {
        Room room = rooms.computeIfAbsent(idRoom, Room::new);
        room.joinToRoom(session);
    }


}
