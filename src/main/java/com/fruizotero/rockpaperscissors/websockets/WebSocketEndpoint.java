package com.fruizotero.rockpaperscissors.websockets;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/rockpaperscissors/{idRoom}")
public class WebSocketEndpoint {

    private static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Peer> peers = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("idRoom") String idRoom) {
        String idPeer = session.getId();
        Room room = rooms.computeIfAbsent(idRoom, Room::new);
        room.joinToRoom(session);
        Peer peer = peers.computeIfAbsent(idPeer, Peer::new);

        session.getAsyncRemote().sendText(Peer.toJson(peer));

        System.out.println("Cantidad Room: " + rooms.size());
        System.out.println("Cantidad peers:" + peers.size());

    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("idRoom") String idRoom) {

        Room room = rooms.get(idRoom);
        String id = session.getId();

        peers.put(id, Peer.fromJson(message));

        if (!(room.lenghtPeers() > 1)) {
            Peer p = peers.get(id);
            p.setMessage("Esperando al otro jugador");
            session.getAsyncRemote().sendText(Peer.toJson(p));
            return;
        }

        ArrayList<String> ids = room.idsPeers();
        Peer p1 = peers.get(ids.get(0));
        Peer p2 = peers.get(ids.get(1));

        if (p1.isReady() && p2.isReady()) {
            room.sendMessageResult(RockPaperScissors.getWinner(p1, p2));
        } else{
            Peer p = peers.get(id);
            p.setMessage("Esperando al otro jugador");
            session.getAsyncRemote().sendText(Peer.toJson(p));
        }

    }

    @OnClose
    public void onClose(Session session, @PathParam("idRoom") String idRoom) {
        Room room = rooms.get(idRoom);
        if (room != null) {
            room.exitToRoom(session);
        }
    }

}
