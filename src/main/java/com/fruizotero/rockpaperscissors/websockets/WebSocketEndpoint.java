package com.fruizotero.rockpaperscissors.websockets;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.Arrays;

@ServerEndpoint("/rockpaperscissors/{idRoom}")
public class WebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("idRoom") String idRoom) {
        String idPeer = session.getId();
        Rooms.addToRoom(idPeer, idRoom, session);
        System.out.println(idPeer);

    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("idRoom") String idRoom) {

        Room room = Rooms.getRoom(idRoom);
        String id = session.getId();

        Rooms.setPeer(id, Peer.fromJson(message));

        if (room.lenghtPeers() == 1) {
            Peer p = Rooms.getPeer(id);
            p.setMessage("Esperando al otro jugador");
            session.getAsyncRemote().sendText(Peer.toJson(p));
            return;
        }

        ArrayList<String> ids = room.idsPeers();
        Peer p1 = Rooms.getPeer(ids.get(0));
        Peer p2 = Rooms.getPeer(ids.get(1));

        if (p1.isReady() && p2.isReady()) {
            RockPaperScissors.getWinner(p1, p2);
            room.sendMessageResult(new ArrayList<>(Arrays.asList(p1, p2)));
        } else {
            Peer p = Rooms.getPeer(id);
            p.setMessage("Esperando al otro jugador");
            session.getAsyncRemote().sendText(Peer.toJson(p));
        }

    }

    @OnClose
    public void onClose(Session session, @PathParam("idRoom") String idRoom) {
        Room room = Rooms.getRoom(idRoom);
        if (room != null) {
            room.exitToRoom(session);
            Rooms.removePeer(session.getId());
        }
    }

}
