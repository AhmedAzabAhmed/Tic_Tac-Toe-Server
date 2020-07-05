/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import winner.TheWinner;

/**
 *
 * @author Azab
 */
class Room {

    Handler player1;
    Handler player2;
    Thread t1, t2;
    boolean youTurn = false;

    int i = 0;
    int k = 0;
    String[] moves = {" ", " ", " ", " ", " ", " ", " ", " ", " "};

    public Room(Handler player1, Handler player2) {

        this.player1 = player1;
        this.player2 = player2;

        t1 = new Thread("player1") {
            @Override
            public void run() {
                String str;
                while (true) {
                    try {
                        str = player1.dis.readLine();
                        k = Integer.parseInt(str);
                        moves[k - 1] = "x";
                        k++;
                        i++;
                        sendMessageToAll(str);
                    } catch (IOException ex) {
                        Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                }
            }

        };

        t2 = new Thread("player2") {
            @Override
            public void run() {
                String str;
                while (true) {
                    try {
                        str = player2.dis.readLine();
                        k = Integer.parseInt(str);
                        moves[k - 1] = "o";
                        k++;
                        i++;
                        sendMessageToAll(str);
                    } catch (IOException ex) {
                        Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                }
            }
        };
        t1.start();
        t2.start();
    }

    void sendMessageToAll(String msg) {

        if (Thread.currentThread().getName().equals("player1") && !youTurn) {
            player2.ps.println(msg);
            player1.ps.println(msg);
            youTurn = !youTurn;

            if (new TheWinner().checkWinner(moves).equals("x")) {
                player2.ps.println("L");
                player1.ps.println("W");
            }

        } else if (Thread.currentThread().getName().equals("player2") && youTurn) {
            player1.ps.println(msg);
            player2.ps.println(msg);
            youTurn = !youTurn;
            if (new TheWinner().checkWinner(moves).equals("o")) {
                player2.ps.println("W");
                player1.ps.println("L");
                System.out.println("player2 thread");
            }
        } else {
            System.out.println("None Thread");
        }
    }
}
