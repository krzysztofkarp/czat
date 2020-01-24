package app.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

public class ClientThread implements Runnable {

    private BufferedWriter outStream;
    private BufferedReader inStream;
    private ChatServer chatServer;

    final private Logger LOG = Logger.getLogger(ClientThread.class.getName());


    public ClientThread(ChatServer chatServer, BufferedWriter outStream,
            BufferedReader inStream) {
        this.chatServer = chatServer;
        this.outStream = outStream;
        this.inStream = inStream;
    }


    @Override
    public void run() {
        try {
            while (true)
                chatServer.sendToClients(inStream.readLine(), this);
        } catch (SocketException e) {
            chatServer.removeClient(this);
            LOG.info("Client was lost.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedWriter getWriter() {
        return this.outStream;
    }
    
}
