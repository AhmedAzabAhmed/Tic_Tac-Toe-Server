package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Azab
 */
public class Handler {

    DataInputStream dis;
    PrintStream ps;
    static int i = 0;
    static Vector<Handler> clientVector = new Vector<Handler>();

    //static Vector<Room> roomVector = new Vector<>();
    public Handler(Socket s) {
        try {
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            clientVector.add(this);
            if (clientVector.size() % 2 == 0 && clientVector.size() > 0) {
                new Room(clientVector.get(i), clientVector.get( ++i));
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
