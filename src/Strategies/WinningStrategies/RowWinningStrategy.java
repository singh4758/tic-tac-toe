package Strategies.WinningStrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;

public class RowWinningStrategy implements WinningStrategy {
    HashMap<Integer, HashMap<Symbol, Integer>> countMap = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!countMap.containsKey(row)) {
            countMap.put(row, new HashMap<>());
        }

        HashMap<Symbol, Integer> rowMap = countMap.get(row);
        if(!rowMap.containsKey(symbol)) {
            rowMap.put(symbol, 0);
        }

        rowMap.put(symbol, rowMap.get(symbol)+1);

        return rowMap.get(symbol) == board.getSize();
    }
}
