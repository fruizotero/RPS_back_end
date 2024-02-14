package com.fruizotero.rockpaperscissors.websockets;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase con un Endpoint para que los usuarios puedan conectarse a una Room
 */
@ServerEndpoint("/rockpaperscissors/{idRoom}")
public class WebSocketEndpoint {

    /**
     * Se conecta a una Room al establecer la conexión
     *
     * @param session sesión
     * @param idRoom  id de la Room
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("idRoom") String idRoom) {
        String idPeer = session.getId();
        Rooms.addToRoom(idPeer, idRoom, session);
        System.out.println(idPeer);

    }

    /**
     * Gestiona los mensajes entre los usuarios conectados a una Room. Maneja algo de lógica para la funcionalidad del juego.
     *
     * @param message mensaje con el json
     * @param session sesión
     * @param idRoom  id de la Room
     */
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

        if (room.lenghtPeers() > 2) {
            Peer p = Rooms.getPeer(id);
            p.setMessage("La sala está completa, dirigete a otra");
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

    /**
     * Elimina las sesiones vinculadas a la Room cuando se cierra la conexión.
     *
     * @param session sesión
     * @param idRoom  id de la Room
     */
    @OnClose
    public void onClose(Session session, @PathParam("idRoom") String idRoom) {
        Room room = Rooms.getRoom(idRoom);
        if (room != null) {
            room.exitToRoom(session);
            Rooms.removePeer(session.getId());
        }
    }

}
