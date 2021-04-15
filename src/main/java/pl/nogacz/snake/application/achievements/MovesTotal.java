package pl.nogacz.snake.application.achievements;

public class MovesTotal extends  Achievement{
    // tier 1 = 100
    // tier 2 = 250
    // tier 3 = 1000

    public MovesTotal(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Travel through <x> meters in total";
    }


}
