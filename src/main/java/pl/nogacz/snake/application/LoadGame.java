package pl.nogacz.snake.application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.BoardInfo;

public class LoadGame {

    Board board;

    private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

    public LoadGame(Board board){

        this.board = board;
    }

    public void getErrorWindow(){

        JFrame info = new JFrame("Info");
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JButton button = new JButton("OK");

        label.setText("No saved games found.");
        button.addActionListener(new ActionListener() { 

            @Override
            public void actionPerformed(ActionEvent e){

                info.setVisible(false);
                info.dispose();
            }
        });

        info.setSize(300,100);
        panel.add(label);
        panel.add(button);
        info.add(panel);
        info.setLocationRelativeTo(null);
        info.setAlwaysOnTop(true);

        info.setVisible(true);
    }

    public void startLoad(){

        String[] fileNames;
        String[] fileNamesRaw;
        String path = getClass().getResource("").getPath() + "SavedGames";
        File f = new File(path);

        if(!f.isDirectory()){

            getErrorWindow();
        }

        else{

            JFrame frame = new JFrame("Load Game");
            JPanel panel = new JPanel();
            JLabel label = new JLabel();
            JButton confirm = new JButton("Confirm");
            JScrollPane scrollPane = new JScrollPane();
            JList list;
            File dateFile;
            BasicFileAttributes attr;
            label.setText("Please select the desired save file name to load:");

            fileNames = f.list();
            fileNamesRaw = f.list();

            for(int i = 0;i<fileNames.length;i++){

                dateFile = new File(path+"/"+fileNames[i]);
                try {

                    attr = Files.readAttributes(dateFile.toPath(), BasicFileAttributes.class);
                    String dateAndTime = attr.creationTime().toString();
                    fileNames[i]=fileNames[i] + "   Save Date: " + dateAndTime.substring(0, dateAndTime.indexOf("T")) + "   Save Time: " + dateAndTime.substring(dateAndTime.indexOf("T")+1,dateAndTime.lastIndexOf(":")+3);               
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, e.getMessage());
                }
            }

            list = new JList(fileNames);

            confirm.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e){

                    int selectedIndex;

                    if((selectedIndex = list.getSelectedIndex())!=-1){
                        
                        FileInputStream fi;
                        ObjectInputStream in;

                        try{

                        fi = new FileInputStream(getClass().getResource("").getPath() + "SavedGames/"+fileNamesRaw[selectedIndex]);
                        in = new ObjectInputStream(fi);

                        BoardInfo BI = (BoardInfo)in.readObject();

                        in.close();
                        fi.close();            

                        LOGGER.log(Level.INFO, "Saved game loaded.");
                        board.setParameters(BI.getBoard(), BI.getDirection(), BI.getTailLength(), BI.getHeadCoordinates(), BI.getHeadClass(), BI.getBodyClass(), BI.getFoodClass(), BI.getSnakeTail());
                        } catch (Exception exception){
                            
                            LOGGER.log(Level.WARNING, exception.getMessage());
                        }                        
                        frame.setVisible(false);
                        frame.dispose();
                    }
                }
            });
            
            scrollPane.setViewportView(list);
            panel.add(label);
            panel.add(scrollPane);
            panel.add(confirm);
            frame.add(panel);            
            frame.setSize(500,300);
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);           
        }        
    }    
}
