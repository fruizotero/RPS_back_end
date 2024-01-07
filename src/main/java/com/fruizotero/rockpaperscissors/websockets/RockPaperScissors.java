package com.fruizotero.rockpaperscissors.websockets;

public class RockPaperScissors {


    public static void getWinner(Peer p1, Peer p2) {

        String stringResults = "R|S P|R S|P";
        String choicePeer1 = p1.getChoice();
        String choicePeer2 = p2.getChoice();
        String choices = "";

        if (choicePeer1.equalsIgnoreCase(choicePeer2)) {
            p1.setDraw(true);
            p2.setDraw(true);
            return;
        }

        choices = String.format("%s|%s", p1.getChoice(), p2.getChoice());
        if (stringResults.contains(choices)) {
            p1.setWinner(true);
            p2.setWinner(false);
        } else {
            p1.setWinner(false);
            p2.setWinner(true);
        }

    }

}
