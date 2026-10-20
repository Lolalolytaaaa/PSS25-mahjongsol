package it.unibo.mahjongsol;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage stage) {
    Label label = new Label("Mahjong Solitaire - Test Finestra");
    Scene scene = new Scene(new StackPane(label), 600, 400);
    stage.setTitle("mahjongsol");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}