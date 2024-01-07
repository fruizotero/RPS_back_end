package com.fruizotero.rockpaperscissors.websockets;

import com.google.gson.Gson;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/rockpaperscissors/rooms")
public class Rooms {

    private static int roomNumber = 0;
    private static Map<String, Room> rooms = new HashMap<>();
    private static Map<String, Peer> peers = new HashMap<>();
    private static ArrayList<String> roomsIds = new ArrayList<>();

    private static ArrayList<Session> sessions = new ArrayList<>();



    @OnOpen
    public void addSession(Session session) {
        sessions.add(session);
        messageCurrentSessions();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String idRoom = String.format("%03d", roomNumber++);
        rooms.computeIfAbsent(idRoom, Room::new);
        roomsIds.add(idRoom);

        messageCurrentSessions();
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        Peer peerRemove = peers.remove(session.getId());
    }

    public static Room getRoom(String idRoom) {
        return rooms.get(idRoom);
    }

    public static Peer getPeer(String idPeer) {
        return peers.get(idPeer);
    }

    public static void setPeer(String idPeer, Peer peer) {
        peers.put(idPeer, peer);
    }

    public static void addToRoom(String idPeer, String idRoom, Session session) {
        Room room = rooms.get(idRoom);
        room.joinToRoom(session);
        Peer peer = peers.computeIfAbsent(idPeer, Peer::new);
        session.getAsyncRemote().sendText(Peer.toJson(peer));

    }

    public static void removePeer(String idPeer) {
        Peer p = peers.remove(idPeer);
    }

    public void messageCurrentSessions() {
        Gson gson = new Gson();
        String json = gson.toJson(roomsIds);

        for (Session s : sessions) {
            s.getAsyncRemote().sendText(json);
        }

    }

}
