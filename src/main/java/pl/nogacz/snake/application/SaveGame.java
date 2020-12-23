package pl.nogacz.snake.application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.BoardInfo;

public class SaveGame {

    BoardInfo BI;
    String defaultSaveFileName;
    static String saveFileName;
    static boolean inputGiven;
    
    private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

    public SaveGame(BoardInfo BI, String defaultSaveFileName) {

        this.BI = BI;
        this.defaultSaveFileName = defaultSaveFileName;

        File file = new File(Paths.get(".", "SavedGames").toUri());
        if(!file.isDirectory()){

            file.mkdir();
        }
    }

    public void startSave(Board board) {

        board.setManualSaveCount(board.getManualSaveCount()-1);        
        JFrame frame = new JFrame("Save Game");
        JPanel panel = new JPanel();
        JTextField input = new JTextField(defaultSaveFileName);
        JButton confirm = new JButton("Confirm");
        JLabel label = new JLabel();
        label.setText("Please enter the desired save file name:");
        confirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(input.getText().equals(""))                
                    input.setText("Please enter a valid save file name.");
                else{
                    saveFileName = input.getText();
                    String path = Paths.get("SavedGames").toAbsolutePath().toString()+"/"+saveFileName;
                                
                    FileOutputStream fo;
                    ObjectOutputStream out;                   

                    try{
                        fo = new FileOutputStream(path);
                        out = new ObjectOutputStream(fo);
                        out.writeObject(BI);
                            
                        fo.close();
                        out.close();
                        board.setManualSaveCount(board.getManualSaveCount()+1);
                        LOGGER.log(Level.INFO, "Game Saved");
                    } catch (IOException exception){
                        LOGGER.log(Level.WARNING, exception.getMessage());
                        frame.dispose();
                        frame.setVisible(false);
                    }
                                        
                    board.setIsPaused(false);
                    board.resume();
                    frame.setVisible(false);
                    frame.dispose();
                }               
            }
        });

        input.setPreferredSize(new Dimension(250,25));
        panel.add(label);
        panel.add(input);
        panel.add(confirm);
        panel.setPreferredSize(new Dimension(300,150));
        frame.add(panel);
        frame.setSize(new Dimension(300,150));
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);        
    }
    
    public void autoSave(){

        FileOutputStream fo;
        ObjectOutputStream out;
        
        String path = Paths.get("SavedGames").toAbsolutePath().toString()+"/"+defaultSaveFileName;

        try{
            fo = new FileOutputStream(path);
            out = new ObjectOutputStream(fo);
            out.writeObject(BI);
                
            fo.close();
            out.close();
            LOGGER.log(Level.INFO, "Game Auto-Saved");            
        } catch (IOException exception){
            LOGGER.log(Level.WARNING, exception.getMessage());
        }
    }
}
