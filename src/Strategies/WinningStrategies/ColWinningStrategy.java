package Strategies.WinningStrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy{
    HashMap<Integer, HashMap<Symbol, Integer>> countMap = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!countMap.containsKey(col)) {
            countMap.put(col, new HashMap<>());
        }

        HashMap<Symbol, Integer> colMap = countMap.get(col);
        if(!colMap.containsKey(symbol)) {
            colMap.put(symbol, 0);
        }

        colMap.put(symbol, colMap.get(symbol)+1);

        return colMap.get(symbol) == board.getSize();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        Map<Symbol, Integer> colMap = countMap.get(col);
        colMap.put(symbol, colMap.get(symbol)-1);
    }
}
