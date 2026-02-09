package com.mycompany.projectjava2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/mycompany/projectjava2/view/view.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Book Management");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}