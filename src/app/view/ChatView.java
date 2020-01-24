package app.view;

import app.ChatApp;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

//widok okna czatu
public class ChatView extends BaseView {

    private AnchorPane anchorPane = null;
    private TextArea conversation;
    private TextArea inputArea;
    private Label nameLabel;

    private ChatApp mainApp = BaseView.getMainApp();

    public AnchorPane getView() {
        if (anchorPane == null)
            this.createView();
        return anchorPane;
    }

    private EventHandler<KeyEvent> onKeyEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {

                String sendingUser = String.join(mainApp.getUserCfg().getUsername() , "<", ">");
                String message = inputArea.getText() + "\n";

                mainApp.getChatClient().sendMessage(sendingUser + " " + message);
                conversation.appendText("<Ty> " + message);

                inputArea.clear(); 
                event.consume();
            }
        }
    };


    public void appendTextToConversation(String message) {
    	conversation.appendText(message);
    }
    

    private void createView() {
        anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: wheat");
                
        
        //Username label
        nameLabel = new Label(mainApp.getUserCfg().getUsername());
        AnchorPane.setTopAnchor(nameLabel, 10.0);
        AnchorPane.setRightAnchor(nameLabel, 10.0);
        AnchorPane.setLeftAnchor(nameLabel, 10.0);
        anchorPane.getChildren().add(nameLabel);
        
        //Main chat textarea
        conversation = new TextArea();
        conversation.setFocusTraversable(false);
        conversation.setMinHeight(260);
        conversation.setWrapText(true);
        
        AnchorPane.setTopAnchor(conversation, 30.0);
        AnchorPane.setRightAnchor(conversation, 30.0);
        AnchorPane.setLeftAnchor(conversation, 30.0);
        anchorPane.getChildren().add(conversation);

        //Text input
        inputArea = new TextArea();
        inputArea.setWrapText(true);
        inputArea.setMaxHeight(15);

        AnchorPane.setBottomAnchor(inputArea, 10.0);
        AnchorPane.setLeftAnchor(inputArea, 10.0);
        AnchorPane.setRightAnchor(inputArea, 10.0);

        inputArea.addEventHandler(KeyEvent.KEY_PRESSED, onKeyEvent);
        anchorPane.getChildren().add(inputArea);
    }
    
    
}
