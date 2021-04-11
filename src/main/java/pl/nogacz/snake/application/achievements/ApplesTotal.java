package pl.nogacz.snake.application.achievements;

public class ApplesTotal extends Achievement {
    public ApplesTotal(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Eat <x> apples in total";
    }
}
