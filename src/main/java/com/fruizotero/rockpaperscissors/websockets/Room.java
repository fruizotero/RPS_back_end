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
        Session s = roomPeers.remove(session.getId());
    }

    public void sendMessageResult(ArrayList<Peer> peers) {

        for (Peer p : peers) {
            Session s = roomPeers.get(p.getIdPeer());
            s.getAsyncRemote().sendText(Peer.toJson(setMsgReady(p, "")));
        }

    }

    private Peer setMsgReady(Peer peer, String msg) {
        peer.setMessage(msg);
        peer.setReady(false);
        peer.setWaiting(false);

        return peer;
    }


}
