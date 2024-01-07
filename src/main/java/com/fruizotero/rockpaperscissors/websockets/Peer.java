package com.fruizotero.rockpaperscissors.websockets;

import com.google.gson.Gson;

public class Peer {
    private String message;
    private String choice;
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

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public static String toJson(Peer peer) {
        Gson gson = new Gson();
        return gson.toJson(peer);
    }

    public static Peer fromJson(String peerString) {
        Gson gson = new Gson();
        return gson.fromJson(peerString, Peer.class);
    }
}
