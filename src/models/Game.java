package models;

import Exceptions.BotCountMoreThanOneException;
import Exceptions.PlayerCountException;
import Exceptions.SymbolUniquenessException;
import Strategies.WinningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private List<WinningStrategy> winningStrategies;
    private Player winner;
    private int nextPlayerIndex;
    private GameState gameState;
    private int dimensions;

    public Game(int dimensions, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.board = new Board(dimensions);
        this.moves = new ArrayList<>();
        this.winningStrategies = winningStrategies;
        this.nextPlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.dimensions = dimensions;
    }

    public static  Builder builder() {
        return new Builder();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int size) {
        this.dimensions = size;
    }

    public static class Builder {
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimensions;

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        private void validateBotCount() throws BotCountMoreThanOneException {
            int botSize = 0;
            for(Player player: players) {
                if(player.getPlayerType().equals(PlayerType.BOT)) {
                    botSize += 1;
                }
            }

            if(botSize > 1) {
                throw new BotCountMoreThanOneException();
            }
        }

        private void validateDimensionAndPlayerCount() throws PlayerCountException {
            if(players.size() > this.dimensions-1) {
                throw new PlayerCountException();
            }
        }

        private void validateSymbolUniqueness() throws SymbolUniquenessException {
            HashSet<Character> uniqueness = new HashSet<>();
            for(Player player: players) {
                if(uniqueness.contains(player.getSymbol().getaChar())) {
                    throw new SymbolUniquenessException();
                }
                uniqueness.add(player.getSymbol().getaChar());
            }
        }

        private void validate() throws BotCountMoreThanOneException, PlayerCountException, SymbolUniquenessException {
            validateBotCount();
            validateSymbolUniqueness();
            validateDimensionAndPlayerCount();
        }

        public Game build() throws BotCountMoreThanOneException, PlayerCountException, SymbolUniquenessException {
            validate();
            return new Game(dimensions, players, winningStrategies);
        }
    }

    private boolean validateMove(Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if(row >= board.getSize() || col >= board.getSize()) {
            return false;
        }

        if(!board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)) {
            return false;
        }

        return true;
    }

    public boolean checkWinner(Board board, Move move) {
        for (WinningStrategy winningStrategy: winningStrategies) {
            if(winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }
        return false;
    }

    public void  makeMove() {
        Player currentMovePlayer = players.get(nextPlayerIndex);
        System.out.println("It is "+ currentMovePlayer.getName()+" .");
        Move currentPlayerMove = currentMovePlayer.makeMove(board);
        if(!validateMove(currentPlayerMove)) {
            System.out.println("Invalid move, Please try again");
            return;
        }
        int row = currentPlayerMove.getCell().getRow();
        int col = currentPlayerMove.getCell().getCol();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentMovePlayer);

        Move finalMoveObject = new Move(cellToChange, currentMovePlayer);
        moves.add(finalMoveObject);
        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();

        if(checkWinner(board, finalMoveObject)) {
            gameState = GameState.WIN;
            winner = currentMovePlayer;
        } else if (moves.size() == board.getSize() * board.getSize()) {
            gameState = GameState.DRAW;
        }

    }

    public void printBoard() {
        board.printBoard();
    }

    public void undo() {
        if(moves.isEmpty()) {
            System.out.println("No moves to undo");
            return;
        }

        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);
        Cell cell = lastMove.getCell();
        cell.setPlayer(null);
        cell.setCellState(CellState.EMPTY);
        for(WinningStrategy winningStrategy: winningStrategies) {
            winningStrategy.handleUndo(board, lastMove);
        }

        nextPlayerIndex = (nextPlayerIndex - 1 + board.getSize())% board.getSize();
    }
}
