package app.view;

import java.io.IOException;
import java.util.Random;

import app.ChatApp;
import app.model.UserConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ConfigView extends BaseView {

    private AnchorPane anchorPane = null;
    private VBox vbox;
    private TextField username;
    private TextField ip;
    private TextField port;
    private Button startBtn;
    private static String DEFAULT_PORT = "4444";

    private ChatApp mainApp = BaseView.getMainApp();

    public AnchorPane getView() {
        if (anchorPane == null)
            this.createView();
        return anchorPane;
    }

    private EventHandler<ActionEvent> onBtnEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            if (event.getSource() == startBtn) {
                UserConfig userCfg = new UserConfig();
                userCfg.setUsername(username.getText());
                userCfg.setHost(ip.getText());
                userCfg.setPort(port.getText());

                mainApp.setUserCfg(userCfg);

                try {
                    mainApp.startChatClient();
                } catch (IOException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Nie uda³o siê po³¹czyæ!");
                    alert.setHeaderText("Nie uda³o po³aczyæ siê z serwerem");
                    alert.showAndWait();
                    return;
                }

                mainApp.setView(mainApp.getChatView());
            }

        }
    };


    private void createView() {
        anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: wheat");
        vbox = new VBox(15);

        Label label = new Label("Nazwa u¿ytkownika");

        username = new TextField();
        username.setText("Uzytkownik-" + new Random().nextInt(1000));
        appendToBox(label, username);

        label = new Label("Serwer");

        ip = new TextField();
        ip.setText("127.1.0.0");
        appendToBox(label, ip);

        label = new Label("Port");

        port = new TextField();
        port.setText(DEFAULT_PORT);
        appendToBox(label, port);

        startBtn = new Button("Po³¹cz");
        startBtn.setPrefWidth(160.0);
        startBtn.setOnAction(onBtnEvent);
        appendToBox(startBtn);

        AnchorPane.setTopAnchor(vbox, 45.0);
        AnchorPane.setLeftAnchor(vbox, 130.0);
        AnchorPane.setRightAnchor(vbox, 130.0);

        anchorPane.getChildren().add(vbox);
    }
    
    
    private void appendToBox(Node ...nodes) {
    	for(Node n : nodes) {
    		vbox.getChildren().add(n);
    	}
    }

}
