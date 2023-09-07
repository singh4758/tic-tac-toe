import Strategies.WinningStrategies.ColWinningStrategy;
import Strategies.WinningStrategies.DiagonalWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import controllers.GameController;
import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);
        int dimensionOfGame = 3;
        List<Player> players = new ArrayList<>();
        players.add(new Player(1L, "Rishab", new Symbol('X'), PlayerType.HUMAN));
        players.add(new Bot(2L, "GPT", new Symbol('Y'), BotDifficultyLevel.EASY));

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());
        Game game = null;
        try {
            game = gameController.startGame(dimensionOfGame, players, winningStrategies);
            while (gameController.checkGameState(game).equals(GameState.IN_PROGRESS)) {
                gameController.printBoard(game);
                System.out.println("Does anyone want to do undo (y/n)");
                String undoAnswer = scanner.next();
                if(undoAnswer.equalsIgnoreCase("y")) {
                    gameController.undo(game);
                    continue;
                }
                gameController.makeMove(game);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        gameController.printBoard(game);
        System.out.println("Game finished");
        GameState gameState = gameController.checkGameState(game);
        if(gameState.equals(GameState.WIN)) {
            System.out.println("Winner is: "+ GameController.getWinner(game));
        } else {
            System.out.println("Game Draw");
        }
    }
}