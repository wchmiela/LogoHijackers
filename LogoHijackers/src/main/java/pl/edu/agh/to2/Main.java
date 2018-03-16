package pl.edu.agh.to2;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.to2.controllers.AppController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Starting app");
        primaryStage.setTitle("Logo Hijackers");

        AppController appController = new AppController(primaryStage);
        appController.initRootLayout();
    }
}
