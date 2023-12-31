package com.fruizotero.rockpaperscissors.websockets;

import com.google.gson.Gson;

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

    public static String toJson(Peer peer){
        Gson gson = new Gson();
        return gson.toJson(peer);
    }

    public static Peer fromJson(String peerString){
        Gson gson = new Gson();
        return gson.fromJson(peerString, Peer.class);
    }
}
