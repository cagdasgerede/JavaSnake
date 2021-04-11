package pl.nogacz.snake.application.achievements;

public class TurnsTotal extends Achievement{
    public TurnsTotal(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Turn any direction <x> times in total";
    }
}
