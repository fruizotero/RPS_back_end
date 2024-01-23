package com.fruizotero.rockpaperscissors.websockets;

import com.google.gson.Gson;

/**
 * Se encarga de gestionar los peers (usuarios) que se conectan a los Endpoints.
 */
public class Peer {
    private String message;
    private String choice;
    private String choiceOtherPeer;
    private String idPeer;
    private boolean ready;
    private boolean winner;
    private boolean draw;
    private boolean isWaiting;

    public Peer(String idPeer) {
        this.message = "";
        this.ready = false;
        this.choice = "";
        this.idPeer = idPeer;
        this.winner = false;
        this.draw = false;
        this.isWaiting = true;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getIdPeer() {
        return idPeer;
    }

    public void setIdPeer(String idPeer) {
        this.idPeer = idPeer;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setIsWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }

    public String getChoiceOtherPeer() {
        return choiceOtherPeer;
    }

    public void setChoiceOtherPeer(String choiceOtherPeer) {
        this.choiceOtherPeer = choiceOtherPeer;
    }

    /**
     * Convierte un objeto de tipo Peer a JSON.
     * @param peer Objeto a convertir
     * @return Devuelve una cadena
     */
    public static String toJson(Peer peer) {
        Gson gson = new Gson();
        return gson.toJson(peer);
    }

    /**
     * Convierte una cadena JSON a un objeto de tipo Peer.
     * @param peerString Cadena String a parsear.
     * @return devuelve un obeto de tipo Peer.
     */
    public static Peer fromJson(String peerString) {
        Gson gson = new Gson();
        return gson.fromJson(peerString, Peer.class);
    }
}
