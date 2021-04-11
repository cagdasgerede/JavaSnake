package pl.nogacz.snake.application.achievements;

public class Moves extends Achievement{
    public Moves(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        super(achievementName, achieved, tier, tierRequirements, progress);
        description = "Move <x> block without dying";
    }

}
