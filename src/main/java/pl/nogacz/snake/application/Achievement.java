package pl.nogacz.snake.application;

import java.awt.SystemTray;
import java.awt.TrayIcon.MessageType;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon;

public abstract class Achievement {

    protected int milestone;
    public int id;

    protected static final int max=10;
    protected double progress;
    protected int achieved;
    protected double addition;

    protected String notificationMessage1="";
    protected String notificationMessage2="";
    protected String statusMessage1="";
    protected String statusMessage2="";

    public void setAll(int achieved , double progress){
        this.achieved=achieved;
        this.progress=progress;
    }

    public double getProgress(){
        return progress;
    }

    public int getAchieved(){
        return achieved;
    }

    public boolean check(){
        if(progress >= (achieved + 1) * milestone){
            achieved++;
            showNotification();
            return true;
        }
        return false;
    }

    public String toString(){
        String toStr = "";
        for(int i = 1 ; i <= max ; i++){
            toStr += statusMessage1 + " "+ (i * milestone) + " " + statusMessage2;

            if(achieved < i){
                toStr+=": NOT ACHIEVED\n";

            }else{
                toStr+=": ACHIEVED\n";
            }

        }
        return toStr;
    }

    public void reset(){
        progress=0;
        achieved=0;
    }

    public void add(){
        progress += addition;
    }

    public void showNotification() {                           //Creates a tray notification while in game for newly unlocked achievements.

        String message= notificationMessage1 + " "+( achieved * milestone ) + " " + notificationMessage2;
        
        try{
         //Obtain only one instance of the SystemTray object
         SystemTray tray = SystemTray.getSystemTray();

         //If the icon is a file
         Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
         //Alternative (if the icon is on the classpath):
         //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
 
         TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
         //Let the system resize the image if needed
         trayIcon.setImageAutoSize(true);
         //Set tooltip text for the tray icon
         trayIcon.setToolTip("System tray icon demo");
         tray.add(trayIcon);
 
         trayIcon.displayMessage("Congratulations!", message, MessageType.INFO);

        }catch(Exception e){e.printStackTrace();}
        
    }
    
}
