package com.fruizotero.rockpaperscissors.websockets;

import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissors {




    public static Map<String, String> getWinner(Peer p1, Peer p2) {

        String idPeer1 = p1.getIdPeer();
        String idPeer2 = p2.getIdPeer();
        Map<String, String> listResults = new HashMap<>();
        listResults.put("R|S", idPeer1);
        listResults.put("P|R", idPeer1);
        listResults.put("S|P", idPeer1);
        Map<String, String> results = new HashMap<>();

        String choicePeer1 = p1.getChoice();
        String choicePeer2 = p2.getChoice();

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
