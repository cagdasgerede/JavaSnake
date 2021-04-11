package pl.nogacz.snake.application.achievements;

public class Deaths extends Achievement{
    public Deaths(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Die <x> times";
    }
}
