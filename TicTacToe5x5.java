package tictactoe.game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TicTacToe5x5 extends Application {
    private static final int SIZE = 5;
    private Button[][] board = new Button[SIZE][SIZE];
    private boolean xTurn = true;
    private Label winnerLabel = new Label("");

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button(" ");
                button.setMinSize(60, 60);
                final int r = row, c = col;
                button.setOnAction(e -> makeMove(button, r, c));
                board[row][col] = button;
                grid.add(button, col, row);
            }
        }

        winnerLabel.setVisible(false);

        StackPane root = new StackPane(grid, winnerLabel);
        StackPane.setAlignment(winnerLabel, Pos.CENTER);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Tic-Tac-Toe 5x5");
        primaryStage.show();
    }

    private void makeMove(Button button, int row, int col) {
        if (!button.getText().equals(" ")) return;
        if (xTurn){
            button.setText("X");
        }
        else {
            button.setText("O");
        }
        if (checkWin(row, col)) {
            if (xTurn) {
                winnerLabel.setText("X Wins!");
            }
            else {
                winnerLabel.setText("O Wins!");
            }
            winnerLabel.setVisible(true);
            disableBoard();
        }
        xTurn = !xTurn;
    }

    private boolean checkWin(int row, int col) {
        String symbol = board[row][col].getText();
        return checkDirection(row, col, 1, 0, symbol) || // Horizontal
               checkDirection(row, col, 0, 1, symbol) || // Vertical
               checkDirection(row, col, 1, 1, symbol) || // Diagonal (\)
               checkDirection(row, col, 1, -1, symbol);       // Diagonal (/)
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol, String symbol) {
        int count = 1;
        count += countConsecutive(row, col, dRow, dCol, symbol);
        count += countConsecutive(row, col, -dRow, -dCol, symbol);
        return count >= 5;
    }

    private int countConsecutive(int row, int col, int dRow, int dCol, String symbol) {
        int count = 0;
        for (int i = 1; i < 5; i++) {
            int r = row + i * dRow, c = col + i * dCol;
            if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].getText().equals(symbol)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private void disableBoard() {
        for (Button[] row : board) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
