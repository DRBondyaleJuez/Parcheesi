package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class ParcheesiLaunch extends Application {

    private Stage mainStage;

    @Override public void start(Stage stage) throws Exception {
        mainStage = stage;
        loadingMainScene();

        mainStage.setTitle("Parcheesi");
        mainStage.centerOnScreen();
        mainStage.show();
    }

    private void loadingMainScene() {

        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource("/view/GameView.fxml"));
        Parent root = loadPaneLoader(paneLoader);
        if(root == null) {
            // This is the extreme case if loaded fxml file is null
            gracefulShutdown();
        } else {
            Scene newScene = new Scene(root);
            mainStage.setScene(newScene);
        }
    }
    private Parent loadPaneLoader(FXMLLoader paneLoader) {
        try {
            return paneLoader.load();
        } catch (IOException e) {
            //Todo: log!!
            System.out.println("The FXML file could not be loaded.");
            System.out.println(e);
            return null;
        }
    }

    private void gracefulShutdown(){
        // Show something to the user if apply
        // save a new log if a apply
        System.out.println("HERE");
        System.exit(-1);
    }

}
