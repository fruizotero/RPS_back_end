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

    public void sendMessage(String idWinner, String idLoser) {
//TODO:: mensaje para los participantes
        Session winner = sessions.get(idWinner);
        Session loser = sessions.get(idLoser);
        if (winner != null) {
            winner.getAsyncRemote().sendText("Ganador");
            loser.getAsyncRemote().sendText("Perdedor");
        } else {
            for (Map.Entry<String, Session> entry : sessions.entrySet()) {
                Session session = entry.getValue();
                session.getAsyncRemote().sendText("empate");
            }

        }
    }


}
