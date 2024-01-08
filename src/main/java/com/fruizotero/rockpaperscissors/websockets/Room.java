package com.fruizotero.rockpaperscissors.websockets;

import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de gestionar a los peers(usuarios)/sesiones conectadas a una Room.
 */
public class Room {

    private String idRoom;
    /**
     * Mapa donde se añaden las sesiones (peers/usuarios). La clave es el Id de la sesión.
     */
    private Map<String, Session> roomPeers = new HashMap<>();

    public Room(String idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * Devuelve la cantidad de sesiones (peers/usuarios) conectados a la room
     * @return
     */
    public int lenghtPeers() {
        return roomPeers.size();
    }

    /**
     * Devuelve una lista con los Ids de las sesiones (peers/usuarios) conectados.
     * @return devuelve una lista de strings
     */
    public ArrayList<String> idsPeers() {
        ArrayList<String> ids = new ArrayList<>();

        for (Map.Entry<String, Session> entry : roomPeers.entrySet()) {
            ids.add(entry.getKey());
        }

        return ids;
    }

    /**
     * Se encarga de agregar a la sesión (peer/usuario) a la Room.
     * @param session
     */
    public void joinToRoom(Session session) {
        roomPeers.put(session.getId(), session);
    }

    /**
     * Elimina a la sesión (peer/usuario) de la Room
     * @param session
     */
    public void exitToRoom(Session session) {
        Session s = roomPeers.remove(session.getId());
    }

    /**
     * Envía un mensaje con los resultados del juego a las sesiones (peers/usuarios) conectados.
     * @param peers
     */
    public void sendMessageResult(ArrayList<Peer> peers) {

        for (Peer p : peers) {
            Session s = roomPeers.get(p.getIdPeer());
            s.getAsyncRemote().sendText(Peer.toJson(setMsgReady(p, "")));
        }

    }

    /**
     * Settea las prrpiedades del Peer antes de su envio.
     * @param peer peer a modificar.
     * @param msg mensaje para settear en el peer
     * @return devuelve un peer
     */
    private Peer setMsgReady(Peer peer, String msg) {
        peer.setMessage(msg);
        peer.setReady(false);
        peer.setWaiting(false);

        return peer;
    }


}
