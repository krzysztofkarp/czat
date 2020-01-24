package app.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import app.ChatApp;



public class ChatClient {

    private ChatApp mainApp;

    private Socket clientSocket;
    private ClientThread clientThread;


    public ChatClient(ChatApp mainApp) {
        this.mainApp = mainApp;
    }

//start klienta i utworzenie klasy w¹tku
    public void start() throws IOException {
        String host = mainApp.getUserCfg().getHost();
        int port = Integer.valueOf(mainApp.getUserCfg().getPort());

        clientSocket = new Socket(host, port);

        BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(
                clientSocket.getOutputStream(), "UTF-8"));
        outStream.flush(); 
        BufferedReader inStream = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream(), "UTF-8"));

        clientThread = new ClientThread(this, outStream, inStream);

        Thread thread = new Thread(clientThread);
        thread.start();
    }


    public void sendMessage(String message) {
        try {
            clientThread.getWriter().write(message);
            clientThread.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected ChatApp getMainApp() {
        return mainApp;
    }

}
