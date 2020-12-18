package pl.nogacz.snake.application;

public class SnakeLength extends Achievement{


    public SnakeLength(){
        super();
        this.milestone=5;
        this.addition=1;
        this.notificationMessage1 = "ACHIEVEMENT UNLOCKED: REACH LENGTH OF";
        this.notificationMessage2 = "IN A GAME";
        this.statusMessage1="REACH LENGTH OF";
        this.statusMessage2="IN A GAME";

    }

    public double getProgress(){
        return 0;
    }
    
}