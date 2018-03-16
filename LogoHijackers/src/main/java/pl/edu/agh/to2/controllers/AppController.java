package pl.edu.agh.to2.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.Main;

import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayDeque;
import java.util.Scanner;

public class AppController {
    private static final Logger logger = LogManager.getLogger(AppController.class);
    private Stage primaryStage;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Logo Hijackers");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/OverviewPane.fxml"));
            BorderPane rootLayout = loader.load();

            OverviewController controller = loader.getController();
            controller.setAppController(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }
    }

    public File showFileChooser(File file) {
        FileChooser fileChooser = new FileChooser();
        if (file != null)
            fileChooser.setInitialDirectory(new File(file.getParent()));
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Logo Files", "*.logo"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser.showSaveDialog(primaryStage);
    }

    public File handleFileSaving(File file, String data, boolean savingDefault) {
        if (!savingDefault || file == null) {
            file = showFileChooser(file);
        }
        if (file == null) {
            logger.debug("User resigned from saving the file");
        } else {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(data);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            logger.debug("File saved");
        }

        return file;
    }

    public File handleFileChoosing(File file) {
        FileChooser fileChooser = new FileChooser();
        if (file != null)
            fileChooser.setInitialDirectory(new File(file.getParent()));
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Logo Files", "*.logo"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser.showOpenDialog(primaryStage);
    }

    public ArrayDeque<String> handleFileReading(File file) {
        ArrayDeque<String> lines = new ArrayDeque<>();
        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    lines.addLast(scanner.nextLine());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return lines;
    }
}
