package com.fruizotero.rockpaperscissors.websockets;

import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {

    private String idRoom;
    private Map<String, Session> roomPeers = new HashMap<>();

    public Room(String idRoom) {
        this.idRoom = idRoom;
    }


    public int lenghtPeers() {
        return roomPeers.size();
    }

    public ArrayList<String> idsPeers() {
        ArrayList<String> ids = new ArrayList<>();

        for (Map.Entry<String, Session> entry : roomPeers.entrySet()) {
            ids.add(entry.getKey());
        }

        return ids;
    }

    public void joinToRoom(Session session) {
        roomPeers.put(session.getId(), session);
    }

    public void exitToRoom(Session session) {
        roomPeers.remove(session.getId());
    }

    public void sendMessageResult(Map<String, Peer> results) {
        Peer winner = results.get("winner");
        Peer loser = results.get("loser");

        if (winner != null && loser != null) {
            winner = results.get("winner");
            loser = results.get("loser");
            Session sessionWinner = roomPeers.get(winner.getIdPeer());
            Session sessionLoser = roomPeers.get(loser.getIdPeer());
            sessionWinner.getAsyncRemote().sendText(Peer.toJson(setMsgReady(winner, "ganador")));
            sessionLoser.getAsyncRemote().sendText(Peer.toJson(setMsgReady(loser, "perdedor")));

        } else {
            for (Map.Entry<String, Session> entry : roomPeers.entrySet()) {
                Session session = entry.getValue();
                Peer peerDraw = WebSocketEndpoint.peers.get(entry.getKey());
                session.getAsyncRemote().sendText(Peer.toJson(setMsgReady(peerDraw, "empate")));
            }

        }

    }

    private Peer setMsgReady(Peer peer, String msg) {
        peer.setMessage(msg);
        peer.setReady(false);
        return peer;
    }


}
