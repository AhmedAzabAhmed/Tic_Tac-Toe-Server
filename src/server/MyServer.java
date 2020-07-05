package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Azab
 */
public class MyServer {

    /**
     * @param args the command line arguments
     */
    ServerSocket myServerSocket;
    static int count = 0;

    public MyServer() {

        try {
            myServerSocket = new ServerSocket(5005);
            while (true) {
                Socket s = myServerSocket.accept();
                new Handler(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        new MyServer();
    }

}
