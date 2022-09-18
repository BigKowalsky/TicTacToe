package com.kodillamyproject.tictactoe;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class TicTacToe extends Application {
    private String playerMark = "X";
    private String computerMark = "O";
    private Cell[][] cell = new Cell[3][3];
    private Button[][] mainBoard;
    private int counter = 0;
    private Alert alert;

    private Image image = new Image("file:src/main/resources/static/images/tictactoe_background.jpg");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BackgroundSize backgroundSize = new BackgroundSize(120, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5.5);
        grid.setVgap(5.5);
        grid.setBackground(background);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cell[i][j] = new Cell();
                grid.add(cell[i][j], j, i);
            }
        }

        mainBoard = new Button[3][3];
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
                mainBoard[column][row] = new Button("");
                mainBoard[column][row].setPrefSize(100, 100);
                mainBoard[column][row].setStyle("fx-background-color: silver");
                mainBoard[column][row].setFont(new Font("Arial", 30));
                mainBoard[column][row].setOnAction(this::markX);
                grid.add(mainBoard[column][row], column + 15, row + 3);
            }
        }

        Button startGame = new Button(" ");
        startGame.setText("   New Game   ");
        startGame.setOnAction(this::clear);
        grid.add(startGame, 15, 7);

        Button endGame = new Button(" ");
        endGame.setText("   End Game   ");
        endGame.setOnAction(this::close);
        grid.add(endGame, 16, 7);

        Scene scene = new Scene(grid, 600, 600, Color.BLACK);

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void markX(ActionEvent actionEvent) {
        Button click = (Button) actionEvent.getTarget();
        click.setText(playerMark);
        click.setDisable(true);
        counter++;
       if (checkWon()) {
                return;
            }
        computerTurn();
    }

    public void clear(ActionEvent actionEvent1) {
        Button clear1 = (Button) actionEvent1.getTarget(); {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    mainBoard[i][j].setText("");
                    mainBoard[i][j].setDisable(false);
                    counter = 0;
                }
            }
        }

    }

    private void close(ActionEvent actionEvent2) {
        Button close1 = (Button) actionEvent2.getTarget(); {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    mainBoard[i][j].setText("");
                    mainBoard[i][j].setDisable(true);
                }
            }
            alertFinish();
        }
    }

    private void computerTurn() {
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        if (!mainBoard[x][y].isDisabled()) {
            mainBoard[x][y].setText(computerMark);
            mainBoard[x][y].setDisable(true);
            counter++;
            checkWon();
        } else {
            computerTurn();
        }
    }

    private List<String> getWiningLine() {
        List<String> winingLines = new ArrayList<>();

        winingLines.add(mainBoard[0][0].getText() + mainBoard[1][1].getText() + mainBoard[2][2].getText());
        winingLines.add(mainBoard[0][0].getText() + mainBoard[0][1].getText() + mainBoard[0][2].getText());
        winingLines.add(mainBoard[1][0].getText() + mainBoard[1][1].getText() + mainBoard[1][2].getText());
        winingLines.add(mainBoard[2][0].getText() + mainBoard[2][1].getText() + mainBoard[2][2].getText());
        winingLines.add(mainBoard[0][0].getText() + mainBoard[1][0].getText() + mainBoard[2][0].getText());
        winingLines.add(mainBoard[0][1].getText() + mainBoard[1][1].getText() + mainBoard[2][1].getText());
        winingLines.add(mainBoard[0][2].getText() + mainBoard[1][2].getText() + mainBoard[2][2].getText());
        winingLines.add(mainBoard[2][0].getText() + mainBoard[1][1].getText() + mainBoard[0][2].getText());

        return winingLines;
    }
    public boolean checkWon() {
        if (counter < 5) {
            return false;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (String lines : getWiningLine()) {
            if (lines.equals(playerMark + playerMark + playerMark)) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        mainBoard[i][j].setDisable(true);
                    }
                }
                alert1();
                return true;
            } else if (lines.equals(computerMark + computerMark + computerMark)) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        mainBoard[i][j].setDisable(true);
                    }
                }
                alert2();
                return true;
            } else if (counter == 9 && !lines.equals(playerMark + playerMark + playerMark) && !lines.equals(computerMark + computerMark + computerMark)) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        mainBoard[i][j].setDisable(true);
                    }
                }
                alert3();
                return true;
            }
        }
        return false;
    }

    private void alert1() {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulation");
            alert.setHeaderText(null);
            alert.setContentText("You won with computer");
            alert.show();
        }
    private void alert2() {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Unfortunately computer won. Try again");
            alert.show();
        }
    private void alert3() {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Draw");
            alert.setHeaderText(null);
            alert.setContentText("Draw! Nobody won!");
            alert.show();
    }

    private void alertFinish() {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Finish Game");
            alert.setHeaderText(null);
            alert.setContentText("Thank You for a game, please close the window and back soon");
            alert.show();
    }
}





