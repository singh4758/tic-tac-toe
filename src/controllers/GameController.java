package controllers;

import Exceptions.BotCountMoreThanOneException;
import Exceptions.PlayerCountException;
import Exceptions.SymbolUniquenessException;
import Strategies.WinningStrategies.WinningStrategy;
import models.Game;
import models.Player;

import java.util.List;

public class GameController {
    public Game startGame(int dimensions, List<Player> players, List<WinningStrategy> winningStrategies)
            throws BotCountMoreThanOneException, SymbolUniquenessException, PlayerCountException {
        return Game.builder()
                .setDimensions(dimensions)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void makeMove(Game game) {
        Player currentMovePlayer = game.getPlayers();
    }

    public void checkGameState(Game game) {

    }
}
