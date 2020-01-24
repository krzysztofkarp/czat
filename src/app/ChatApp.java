package app;

import java.io.IOException;

import app.client.ChatClient;
import app.model.UserConfig;
import app.view.BaseView;
import app.view.ChatView;
import app.view.ConfigView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private UserConfig userCfg;

    private ChatClient chatClient;

    private ConfigView configView;
    private ChatView chatView;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Czat");
        this.primaryStage.setResizable(false);
        this.primaryStage.setOnCloseRequest(e -> System.exit(0));

        chatClient = new ChatClient(this); 

        BaseView.setMainApp(this);
        configView = new ConfigView();
        chatView = new ChatView();

        this.initRoot();
    }


    private void initRoot() {
        rootLayout = new BorderPane();
        Scene scene = new Scene(rootLayout, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        this.setView(getConfigView());
    }


    public void appendToChat(String message) {
        chatView.appendTextToConversation(message);
    }
    
    public void startChatClient() throws IOException {
        getChatClient().start();
    }

 
    public void setView(BaseView newView) {
        rootLayout.setCenter(newView.getView());
    }


    public UserConfig getUserCfg() {
        return userCfg;
    }


    public void setUserCfg(UserConfig userCfg) {
        this.userCfg = userCfg;
    }


    public ChatClient getChatClient() {
        return chatClient;
    }

    public ChatView getChatView() {
        return chatView;
    }

    public ConfigView getConfigView() {
        return configView;
    }

}