package com.fruizotero.rockpaperscissors.websockets;

import jakarta.websocket.Session;

import java.util.HashMap;

public class Room {

    private String nameRoom;
    private HashMap<String, Session> sessions = new HashMap<>();

    public Room (String nameRoom){
        this.nameRoom=nameRoom;
    }





}
