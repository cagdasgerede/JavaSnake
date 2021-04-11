package pl.nogacz.snake.application.achievements;

public class Apples extends Achievement{

    public Apples(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Collect <x> apples in one game";
    }
}
