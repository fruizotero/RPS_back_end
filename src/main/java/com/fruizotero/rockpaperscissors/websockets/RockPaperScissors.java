package com.fruizotero.rockpaperscissors.websockets;

/**
 * Clase encargada de gestionar la lógica del juego.
 */
public class RockPaperScissors {


    /**
     * Se encarga de determinar el ganador.
     * @param p1 jugador 1
     * @param p2 jugador 2
     */
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
