package pl.nogacz.snake.application;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class LogWriter {
    private boolean append = true;
    private Logger logger = Logger.getLogger("pl.nogacz.snake.application");
    private String pathToLogFile = "logs/Exceptions.log"; 
    private SimpleFormatter formatter = new SimpleFormatter();

    public void write(String message) {
        try{
            FileHandler handler = new FileHandler(pathToLogFile, append);
            handler.setFormatter(formatter);
            logger.addHandler(handler);
            logger.warning(message);
            handler.close();

        }catch(Exception e){e.printStackTrace();}

    }
}
