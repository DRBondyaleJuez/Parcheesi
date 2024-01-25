package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * This the entry point of the application launch called by the main
 * <p>
 *     Inheriting from application requiring an implementation of abstract class start that allows the display of the view
 * </p>
 * @author Daniel R Bondyale Juez
 * @version 1.0
 */
public class ParcheesiLaunch extends Application {

    private static Logger logger = LogManager.getLogger(ParcheesiLaunch.class);
    private Stage mainStage;

    /**
     * This is the implementation of the start abstract method of the extended class Application.
     *  * <p>
     *     This method is called during the execution of the Application class static method launch. It loads the FXMl files,
     *     therefore, it builds its controllers too and mainStage is built and displayed with the method show of the Stage class.
     *  * </p>
     * @param stage Stage object provided during the static launch method execution.
     */
    @Override public void start(Stage stage) {
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
        } catch (IOException exception) {
            System.out.println("The FXML file could not be loaded.");
            logger.error("The FXML file (" + paneLoader.toString() + ") could not be loaded. ERROR:\n ",  exception);
            return null;
        }
    }

    private void gracefulShutdown(){
        logger.info("There has been a fatal error. I am shutting down.");
        System.exit(-1);
    }

}
