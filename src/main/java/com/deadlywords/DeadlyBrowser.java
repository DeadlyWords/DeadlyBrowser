package com.deadlywords;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**w
 * JavaFX App
 */
public class DeadlyBrowser extends Application {


    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {


        scene = new Scene(loadFXML("scene"), 1400, 1000);
        //stage.initStyle(StageStyle.UNDECORATED);
        setRoot("scene");
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DeadlyBrowser.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }
}