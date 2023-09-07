package Strategies.WinningStrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;

public class DiagonalWinningStrategy implements WinningStrategy {
    HashMap<Symbol, Integer> leftDiagCount = new HashMap<>();
    HashMap<Symbol, Integer> rightDiagCount = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Symbol symbol = move.getPlayer().getSymbol();
        if(row == col) {
            if(!leftDiagCount.containsKey(symbol)) {
                leftDiagCount.put(symbol, 0);
            }
            leftDiagCount.put(symbol, leftDiagCount.get(symbol) + 1);
        }

        if(row + col == board.getSize()) {
            if(!rightDiagCount.containsKey(symbol)) {
                rightDiagCount.put(symbol, 0);
            }
            rightDiagCount.put(symbol, rightDiagCount.get(symbol) + 1);
        }

        if(row == col && leftDiagCount.get(symbol) == board.getSize()) {
            return true;
        }
        if(row + col == board.getSize() && rightDiagCount.get(symbol) == board.getSize()) {
            return true;
        }
        return false;
    }
}
