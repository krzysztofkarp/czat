package app.view;

import java.util.logging.Logger;

import app.ChatApp;
import javafx.scene.Parent;

public abstract class BaseView {
	
	
    final private static Logger LOG = Logger.getLogger(BaseView.class.getName());

    private static ChatApp app = null;

    public abstract Parent getView();


    public static ChatApp getMainApp() {
        if (app == null) {
            try {
                throw new Exception("No app reference");
            } catch (Exception e) {
               LOG.info(e.getMessage());
            }
        }
        return app;
    }


    public static void setMainApp(ChatApp mainApp) {
        BaseView.app = mainApp;
    }

}
