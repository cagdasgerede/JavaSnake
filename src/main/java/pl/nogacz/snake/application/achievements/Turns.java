package pl.nogacz.snake.application.achievements;

public class Turns extends Achievement{
    public Turns(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Turn any direction <x> times without dying";
    }
}
