package app.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.SocketException;
import app.view.ConfigView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*   Klasa w¹tku dla klienta, nas³uchuje odpowiedzi z serwera i do³acza ja do czatu*/

public class ClientThread implements Runnable {

    private ChatClient chatClient;
    private BufferedWriter outStream;
    private BufferedReader inStream;


    public ClientThread(ChatClient chatClient, BufferedWriter outStream,
            BufferedReader inStream) {
        this.chatClient = chatClient;
        this.outStream = outStream;
        this.inStream = inStream;
    }


    @Override
    public void run() {
        try {
            while (true) {
                String message = inStream.readLine();
                chatClient.getMainApp().appendToChat(message + "\n");
            }
        } catch (SocketException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("B³¹d");
                    alert.setHeaderText("Utracono po³aczenie z serwerem!");
                    alert.showAndWait();
                    chatClient.getMainApp().setView(new ConfigView());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedWriter getWriter() {
        return this.outStream;
    }

}
