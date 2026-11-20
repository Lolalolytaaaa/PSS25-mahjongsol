package it.unibo.mahjongsol.view;

import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;
import it.unibo.mahjongsol.controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * Vista principale del gioco Mahjong Solitaire in JavaFX.
 */
public class MahjongView {

    private static final double TILE_WIDTH = 54.0;
    private static final double TILE_HEIGHT = 72.0;
    private static final double STEP_X = TILE_WIDTH / 2.0;
    private static final double STEP_Y = TILE_HEIGHT / 2.0;
    private static final double LAYER_OFFSET_X = -6.0;
    private static final double LAYER_OFFSET_Y = -6.0;
    private static final double BOARD_PADDING_X = 60.0;
    private static final double BOARD_PADDING_Y = 40.0;

    private final GameController controller;
    private final BorderPane root;
    private final Pane boardPane;
    private final Label scoreLabel;
    private final Label remainingLabel;

    public MahjongView(final GameController controller) {
        this.controller = controller;
        this.root = new BorderPane();
        this.boardPane = new Pane();
        this.scoreLabel = new Label();
        this.remainingLabel = new Label();

        setupLayout();
        render();
    }

    public Parent getRoot() {
        return this.root;
    }

    private void setupLayout() {
        this.root.setStyle("-fx-background-color: #2E4A3E;");

        // Barra superiore (HUD)
        final HBox topBar = new HBox(24);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(14, 24, 14, 24));
        topBar.setStyle("-fx-background-color: #1F332B; -fx-border-color: #3D6152; -fx-border-width: 0 0 2 0;");

        final Label titleLabel = new Label("MAHJONG SOLITAIRE");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#F5EFE6"));

        this.scoreLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
        this.scoreLabel.setTextFill(Color.web("#E2C07D"));

        this.remainingLabel.setFont(Font.font("System", FontWeight.NORMAL, 15));
        this.remainingLabel.setTextFill(Color.web("#D5E2DC"));

        final Button newGameBtn = new Button("Nuova Partita");
        newGameBtn.setStyle(
                "-fx-background-color: #4A7C66; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-padding: 8 16 8 16; -fx-background-radius: 6; -fx-cursor: hand;"
        );
        newGameBtn.setOnAction(e -> {
            this.controller.newGame();
            render();
        });

        topBar.getChildren().addAll(titleLabel, this.scoreLabel, this.remainingLabel, newGameBtn);
        this.root.setTop(topBar);

        // Area centrale della plancia
        final StackPane centerWrapper = new StackPane(this.boardPane);
        centerWrapper.setPadding(new Insets(10));
        this.boardPane.setMaxSize(960, 660);
        this.boardPane.setPrefSize(960, 660);
        this.root.setCenter(centerWrapper);
    }

    private void render() {
        this.boardPane.getChildren().clear();

        final Map<Position, Tile> tiles = this.controller.getVisibleTiles();
        final Optional<Position> selectedPos = this.controller.getSelectedTile();

        this.scoreLabel.setText("Punteggio: " + this.controller.getScore());
        this.remainingLabel.setText("Tessere rimaste: " + tiles.size());

        // Ordina per z crescente (dal basso verso l'alto), poi per y e x per la corretta sovrapposizione 3D
        tiles.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<Position, Tile> e) -> e.getKey().z())
                        .thenComparingInt(e -> e.getKey().y())
                        .thenComparingInt(e -> e.getKey().x()))
                .forEach(entry -> {
                    final Position pos = entry.getKey();
                    final Tile tile = entry.getValue();
                    final boolean isSelected = selectedPos.isPresent() && selectedPos.get().equals(pos);
                    final boolean isFree = this.controller.isTileFree(pos);

                    final StackPane tileNode = createTileNode(pos, tile, isSelected, isFree);
                    this.boardPane.getChildren().add(tileNode);
                });
    }

    private StackPane createTileNode(final Position pos, final Tile tile, final boolean isSelected, final boolean isFree) {
        final double screenX = BOARD_PADDING_X + pos.x() * STEP_X + pos.z() * LAYER_OFFSET_X;
        final double screenY = BOARD_PADDING_Y + pos.y() * STEP_Y + pos.z() * LAYER_OFFSET_Y;

        final Rectangle base = new Rectangle(TILE_WIDTH, TILE_HEIGHT);
        base.setArcWidth(10);
        base.setArcHeight(10);

        if (isSelected) {
            base.setFill(Color.web("#FFF3B0"));
            base.setStroke(Color.web("#E09F3E"));
            base.setStrokeWidth(3.0);
        } else if (isFree) {
            base.setFill(Color.web("#FAF6EE"));
            base.setStroke(Color.web("#8C7A6B"));
            base.setStrokeWidth(1.5);
        } else {
            base.setFill(Color.web("#DDD6C8"));
            base.setStroke(Color.web("#7A6F64"));
            base.setStrokeWidth(1.2);
        }

        final DropShadow shadow = new DropShadow();
        shadow.setOffsetX(3);
        shadow.setOffsetY(4);
        shadow.setRadius(4);
        shadow.setColor(Color.rgb(0, 0, 0, 0.45));
        base.setEffect(shadow);

        final VBox labels = new VBox(3);
        labels.setAlignment(Pos.CENTER);

        final Text valueText = new Text(formatValue(tile));
        valueText.setFont(Font.font("System", FontWeight.BOLD, 16));
        valueText.setFill(getSuitColor(tile.getSuit()));

        final Text suitText = new Text(formatSuit(tile.getSuit()));
        suitText.setFont(Font.font("System", FontWeight.BOLD, 9));
        suitText.setFill(Color.web("#4A4A4A"));

        labels.getChildren().addAll(valueText, suitText);

        final StackPane tilePane = new StackPane(base, labels);
        tilePane.setLayoutX(screenX);
        tilePane.setLayoutY(screenY);

        if (isFree) {
            tilePane.setStyle("-fx-cursor: hand;");
            tilePane.setOnMouseClicked(event -> {
                this.controller.onTileClicked(pos);
                render();
                checkEndGame();
            });
        }

        return tilePane;
    }

    private void checkEndGame() {
        if (this.controller.isVictory()) {
            showEndAlert("Vittoria!", "Complimenti, hai rimosso tutte le tessere! Punteggio finale: " + this.controller.getScore());
        } else if (this.controller.isGameOver()) {
            showEndAlert("Nessuna mossa disponibile", "Game Over! Non ci sono più coppie libere. Punteggio: " + this.controller.getScore());
        }
    }

    private void showEndAlert(final String title, final String message) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String formatSuit(final String suit) {
        return switch (suit) {
            case "BAMBOO" -> "BAMBU";
            case "CHARACTERS" -> "CARATT.";
            case "DOTS" -> "CERCHI";
            case "WINDS" -> "VENTO";
            case "DRAGONS" -> "DRAGO";
            case "FLOWERS" -> "FIORE";
            case "SEASONS" -> "STAGIONE";
            default -> suit;
        };
    }

    private String formatValue(final Tile tile) {
        return switch (tile.getValue()) {
            case "NORTH" -> "N";
            case "SOUTH" -> "S";
            case "EAST" -> "E";
            case "WEST" -> "O";
            case "RED" -> "ROSSO";
            case "GREEN" -> "VERDE";
            case "WHITE" -> "BIANCO";
            default -> tile.getValue().replace("FLOWER_", "F").replace("SEASON_", "S");
        };
    }

    private Color getSuitColor(final String suit) {
        return switch (suit) {
            case "BAMBOO" -> Color.web("#2B7A4B");
            case "CHARACTERS" -> Color.web("#B23A48");
            case "DOTS" -> Color.web("#2A5C8F");
            case "DRAGONS" -> Color.web("#8B2635");
            case "WINDS" -> Color.web("#2B2D42");
            case "FLOWERS" -> Color.web("#9B5DE5");
            case "SEASONS" -> Color.web("#D97706");
            default -> Color.BLACK;
        };
    }
}