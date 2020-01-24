package app.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ChatServer {

    final private int PORT = 4444;
    final private List<ClientThread> clients = new ArrayList<ClientThread>();
    final private static Logger LOG = Logger.getLogger(ChatServer.class.getName());


    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

    public void start() {
        try {
            ServerSocket socket = new ServerSocket(PORT);
            LOG.info("Server started, port: " + PORT);

            while (true) {
                Socket clientSocket = socket.accept();
                BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(
                        clientSocket.getOutputStream(), "UTF-8"));
                outStream.flush();
                BufferedReader inStream = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream(), "UTF-8"));

                ClientThread clientThread = new ClientThread(this, outStream, inStream);
                clients.add(clientThread);

                Thread thread = new Thread(clientThread);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


    protected void removeClient(ClientThread client) {
        clients.remove(clients.indexOf(client));
    }

    protected void sendToClients(String message, ClientThread sender) {

        for (ClientThread clientThread : clients) {

            if (clientThread == sender)
                continue;

            try {
                clientThread.getWriter().write(message + "\n");
                clientThread.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
