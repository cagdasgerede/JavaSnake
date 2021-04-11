package pl.nogacz.snake.application.achievements;

public class Spiral extends Achievement{
    public Spiral(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Perform <x> consecutive turns in same direction";
    }
}
