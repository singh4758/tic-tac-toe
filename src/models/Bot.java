package models;

import Strategies.BotPlayingStartegy;

public class Bot extends Player {
    private BotDifficultyLevel botDifficultyLevel;
    private BotPlayingStartegy botPlayingStartegy;

    public Bot(Long id, String name, Symbol symbol, BotDifficultyLevel botDifficultyLevel) {
        super(id, name, symbol, PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
    }

    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }

    public void setBotDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        this.botDifficultyLevel = botDifficultyLevel;
    }

    public BotPlayingStartegy getBotPlayingStartegy() {
        return botPlayingStartegy;
    }

    public void setBotPlayingStartegy(BotPlayingStartegy botPlayingStartegy) {
        this.botPlayingStartegy = botPlayingStartegy;
    }
}
