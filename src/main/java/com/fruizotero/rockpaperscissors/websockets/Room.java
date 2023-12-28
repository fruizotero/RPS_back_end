package com.fruizotero.rockpaperscissors.websockets;

import jakarta.websocket.Session;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private String nameRoom;
    private HashMap<String, Session> sessions = new HashMap<>();

    public Room(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public void joinToRoom(Session session) {
        sessions.put(session.getId(), session);
    }

    public void exitToRoom(Session session) {
        sessions.remove(session.getId());
    }

    public void sendMessage(String idWinner) {
//TODO:: mensaje para los participantes
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            String id = entry.getKey();
            Session session = entry.getValue();
            if (id.equalsIgnoreCase(idWinner)) {
                session.getAsyncRemote().sendText("Has ganado");
            } else{
                session.getAsyncRemote().sendText("Has perdido");
            }
        }
    }


}
