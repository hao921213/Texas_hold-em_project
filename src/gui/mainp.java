package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import program.*;
import java.util.Optional;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class mainp extends Application {
    private GameLogic game;
    private VBox playerHandsDisplay;

    @Override
    public void start(Stage primaryStage) {
        game = new GameLogic(); // Initialize game logic

        primaryStage.setTitle("Texas Hold'em Poker");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        playerHandsDisplay = new VBox(10);
        playerHandsDisplay.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");
        root.setBottom(playerHandsDisplay);

        HBox communityCardsArea = new HBox(10);
        communityCardsArea.setStyle("-fx-background-color: green; -fx-padding: 10px;");
        root.setCenter(communityCardsArea);

        VBox gameControls = new VBox(10);
        Button btnFold = new Button("Fold");
        Button btnCheck = new Button("Check");
        Button btnCall = new Button("Call");
        Button btnRaise = new Button("Raise");
        Button btnDeal = new Button("Deal Cards");
        Button btnReset = new Button("Reset"); // Add reset button
        gameControls.getChildren().addAll(btnFold, btnCheck, btnCall, btnRaise, btnDeal, btnReset);
        root.setRight(gameControls);

        // Set actions for buttons
        btnFold.setOnAction(event -> {
            game.fold();
            displayPlayerHands();  // Update the display to show it's the next player's turn
        });
        btnCheck.setOnAction(event -> {
            game.check();
            displayPlayerHands();
        });
        btnCall.setOnAction(event -> {
            game.call();
            displayPlayerHands();
        });
        btnRaise.setOnAction(event -> {
            promptForRaiseAmount();  // Assume this method updates the game and calls displayPlayerHands internally
        });
        btnDeal.setOnAction(event -> {
            game.dealCards();
            displayPlayerHands();
        });
        btnReset.setOnAction(event -> {
            game.resetGame();
            displayPlayerHands();
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void promptForRaiseAmount() {
        TextInputDialog dialog = new TextInputDialog("100");
        dialog.setTitle("Raise");
        dialog.setHeaderText("Enter your raise amount:");
        dialog.setContentText("Amount:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            try {
                int raiseAmount = Integer.parseInt(amount);
                if (raiseAmount > 0) {
                    game.raise(raiseAmount);
                    displayPlayerHands(); // Ensure GUI updates after raising
                } else {
                    showErrorMessage("Raise amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter a valid number.");
            }
        });
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayPlayerHands() {
        playerHandsDisplay.getChildren().clear();  // Clear previous hands
        for (int i = 0; i < game.getPlayers().length; i++) {
            Player player = game.getPlayers()[i];
            BorderPane playerPane = new BorderPane();
            Label playerInfo = new Label();
            playerInfo.textProperty().bind(player.chipsProperty().asString("Player: " + player.getName() + " - Chips: %d"));
            
            // Check if it's the current player's turn
            if (i == game.getCurrentPlayerIndex()) {
                playerPane.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");
                Label turnLabel = new Label("Your Turn");
                turnLabel.setFont(new Font("Arial", 16));
                turnLabel.setTextFill(Color.RED);
                playerPane.setTop(new VBox(playerInfo, turnLabel));
            } else {
                playerPane.setStyle("-fx-padding: 10px;");
                playerPane.setTop(playerInfo);
            }

            HBox handDisplay = new HBox(5);
            for (Card card : player.getHand()) {
                Button cardButton = new Button(card.toString());
                handDisplay.getChildren().add(cardButton);
            }
            playerPane.setCenter(handDisplay);
            playerHandsDisplay.getChildren().add(playerPane);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
