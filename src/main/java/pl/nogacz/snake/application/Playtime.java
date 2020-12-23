package pl.nogacz.snake.application;

public class Playtime extends Achievement{

    public Playtime() {
        super();
        this.milestone = 1;
        this.addition = 0.0023;
        this.notificationMessage1 = "ACHIEVEMENT UNLOCKED: PLAY";
        this.notificationMessage2 = "MINUTES";
        this.statusMessage1 = "PLAY";
        this.statusMessage2 = "MINUTES";
    }
    
}
