package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//   apikey -      5a573853aef22227deedf74e69ecbe3a
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("WeatherApp");
        primaryStage.setScene(new Scene(root, 350, 550));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
