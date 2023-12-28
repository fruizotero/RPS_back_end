package com.fruizotero.rockpaperscissors.websockets;

import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissors {

    private Map<String, Session> peersGame;

    public RockPaperScissors(HashMap<String, Session> peersGame) {
        this.peersGame = peersGame;
    }

    private ArrayList<Session> hashMapToArray() {
        ArrayList<Session> listSessions = new ArrayList<>();
        for (Map.Entry<String, Session> entry : peersGame.entrySet()) {
            listSessions.add(entry.getValue());
        }
        return listSessions;
    }

    public Map<String, String> getWinner() {

        ArrayList<Session> listSessions = hashMapToArray();
        String idPeer1 = listSessions.get(0).getId();
        String idPeer2 = listSessions.get(1).getId();
        Map<String, String> listResults = new HashMap<>();
        listResults.put("R|S", idPeer1);
        listResults.put("P|R", idPeer1);
        listResults.put("S|P", idPeer1);
        Map<String, String> results = new HashMap<>();

        String choicePeer1 = (String) listSessions.get(0).getUserProperties().get("choice");
        String choicePeer2 = (String) listSessions.get(1).getUserProperties().get("choice");

        if (choicePeer1.equalsIgnoreCase(choicePeer2)) {
            results.put("draw", null);
            return results;
        }

        String idWinner = listResults.getOrDefault(String.format("%s|%s", choicePeer1, choicePeer2), null);

        if (idWinner != null) {
            results.put("winner", idPeer1);
        } else {
            results.put("winner", idPeer2);
        }


        return results;
    }

}
