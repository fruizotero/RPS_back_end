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

/**
 * Clase con el endpoint para que los usuarios puedan crear una Room
 */
@ServerEndpoint("/rockpaperscissors/rooms")
public class Rooms {

    /**
     * Gestiona los numeros o ids de las Rooms.
     */
    private static int roomNumber = 0;
    /**
     * Mapa con las Room. Su clave es el roomNumber (idRoom)
     */
    private static Map<String, Room> rooms = new HashMap<>();
    /**
     * Mapa con los Peers (usuarios/sesiones) conectados. La clave es el id de la sesión.
     */
    private static Map<String, Peer> peers = new HashMap<>();
    /**
     * Lista con los ids
     */
    private static ArrayList<String> roomsIds = new ArrayList<>();
    /**
     * Lista con las sesiones
     */
    private static ArrayList<Session> sessions = new ArrayList<>();


    /**
     * Añade la sesión que ha establecido una conexión con el enpoint(wbsockets) a la lista de sesiones. Además envía un mensaje a todas las sesiones conectadas. El mensaje contendrá los ids de las Rooms creadas.
     *
     * @param session
     */
    @OnOpen
    public void addSession(Session session) {
        sessions.add(session);
        messageCurrentSessions();
    }

    /**
     * Si una sesión conectada envía un mensaje, se tomará como que quiere crear una nueva Room. La función se encarga de crear la nueva Room y de enviar un mensaje con los Ids de las Rooms creadas a todas las sesiones conectadas.
     *
     * @param message mensaje que se recibe
     * @param session sesión que envia el mensaje
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String idRoom = String.format("%03d", roomNumber++);
        rooms.computeIfAbsent(idRoom, Room::new);
        roomsIds.add(idRoom);

        messageCurrentSessions();
    }

    /**
     * Cuando se cierra la conexión (normalmente cuando el cliente cierra su ventana), se elimina todo lo asociado  a dicha sesión.
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        Peer peerRemove = peers.remove(session.getId());
    }

    /**
     * Añade las sesiones (peers/usuarios) a la Room
     *
     * @param idPeer  id del Peer
     * @param idRoom  id de la Room
     * @param session sesión
     */
    public static void addToRoom(String idPeer, String idRoom, Session session) {
        Room room = rooms.get(idRoom);
        room.joinToRoom(session);
        Peer peer = peers.computeIfAbsent(idPeer, Peer::new);
        session.getAsyncRemote().sendText(Peer.toJson(peer));

    }

    /**
     * Envia un mensaje a todos las sesiones (peers/usuarios) conectados con todos los IDS de las salas existentes.
     */
    private void messageCurrentSessions() {
        Gson gson = new Gson();
        String json = gson.toJson(roomsIds);

        for (Session s : sessions) {
            s.getAsyncRemote().sendText(json);
        }

    }

    /**
     * Devuellve una Room
     *
     * @param idRoom id de la Room
     * @return
     */
    public static Room getRoom(String idRoom) {
        return rooms.get(idRoom);
    }

    /**
     * Devuelve un Peer
     *
     * @param idPeer id del Peer
     * @return
     */
    public static Peer getPeer(String idPeer) {
        return peers.get(idPeer);
    }

    /**
     * Añade o actualiza el estado de un Peer.
     *
     * @param idPeer id del Peer
     * @param peer   Peer
     */
    public static void setPeer(String idPeer, Peer peer) {
        peers.put(idPeer, peer);
    }

    /**
     * Elimina un Peer
     *
     * @param idPeer id del Peer
     */
    public static void removePeer(String idPeer) {
        Peer p = peers.remove(idPeer);
    }

}
