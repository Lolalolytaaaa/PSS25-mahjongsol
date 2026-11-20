package it.unibo.mahjongsol;

import it.unibo.mahjongsol.controller.GameController;
import it.unibo.mahjongsol.controller.GameControllerImpl;
import it.unibo.mahjongsol.view.MahjongView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point dell'applicazione JavaFX Mahjong Solitaire.
 */
public class App extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final GameController controller = new GameControllerImpl();
        final MahjongView view = new MahjongView(controller);

        final Scene scene = new Scene(view.getRoot(), 1024, 740);
        primaryStage.setTitle("Mahjong Solitaire");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}