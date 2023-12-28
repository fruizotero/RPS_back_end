package com.fruizotero.rockpaperscissors.websockets;

public class Peer {
    private String message;
    private boolean ready;
    private String choice;
    private String idPeer;

    public Peer(String idPeer) {
        this.message = "";
        this.ready = false;
        this.choice = "";
        this.idPeer = idPeer;
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
}
