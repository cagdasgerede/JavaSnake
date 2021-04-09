package pl.nogacz.snake.application;
import javax.swing.JFrame;
import java.util.Optional;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class UserKeyDefiner extends JFrame{

    JLabel message1;
    JLabel message2;
    JLabel message3;
    JLabel message4;
    JLabel message5;
    JLabel message6;
    JLabel message7;
    JLabel message8;

    JTextField input1;
    JTextField input2;
    JTextField input3;
    JTextField input4;
    JTextField input5;
    JTextField input6;
    JTextField input7;
    JTextField input8;

    JPanel panel;

    private String[] inputsAsString;
    private boolean inputsAreValid = false;
    private char[] inputsAsChar;

    public UserKeyDefiner(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300,300);

        message1 = new JLabel("First Player Up Button");
        message2 = new JLabel("First Player Down Button");
        message3 = new JLabel("First Player Left Button");
        message4 = new JLabel("First Player Right Button");

        message5 = new JLabel("Second Player Up Button");
        message6 = new JLabel("Second Player Down Button");
        message7 = new JLabel("Second Player Left Button");
        message8 = new JLabel("Second Player Right Button");

        input1 = new JTextField(10);
        input2 = new JTextField(10);
        input3 = new JTextField(10);
        input4 = new JTextField(10);
        input5 = new JTextField(10);
        input6 = new JTextField(10);
        input7 = new JTextField(10);
        input8 = new JTextField(10);

        inputsAsString = new String[8];

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(message1);
        panel.add(input1);
        panel.add(message2);
        panel.add(input2);
        panel.add(message3);
        panel.add(input3);
        panel.add(message4);
        panel.add(input4);
        panel.add(message5);
        panel.add(input5);
        panel.add(message6);
        panel.add(input6);
        panel.add(message7);
        panel.add(input7);
        panel.add(message8);
        panel.add(input8);

        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    public void approver(){
        Alert alert;
        ButtonType approve;
        Optional<ButtonType> result;

        alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Approve");
        alert.setContentText("Please approve");

        approve = new ButtonType("Approve");

        alert.getButtonTypes().setAll(approve);

        result = alert.showAndWait();

       if(result.get() == approve){
        inputsAsString[0] = input1.getText();
        inputsAsString[1] = input2.getText();
        inputsAsString[2] = input3.getText();
        inputsAsString[3] = input4.getText();
        inputsAsString[4] = input5.getText();
        inputsAsString[5] = input6.getText();
        inputsAsString[6] = input7.getText();
        inputsAsString[7] = input8.getText();
        upperCaseAllArray(inputsAsString);
        if(areInputsValid(inputsAsString)){
            prepareNewKeys();
            inputsAreValid = true;
            this.dispose();
        }else{
            System.out.println("Keys Entered are same or empty");
            System.exit(0);
        }
       }
    }

    public char[] getNewKeys(){
        return inputsAsChar;
    }

    public void prepareNewKeys(){
        inputsAsChar = new char[8];
        for(int i = 0 ; i < inputsAsString.length ; i++){
            inputsAsChar[i] = inputsAsString[i].charAt(0);
        }
    }

    public boolean areInputsReady(){
        return inputsAreValid;
    }

    public boolean areThereEmptyInputs(String[] inputArray){
        for(int i = 0 ; i < inputArray.length ; i++){
            if(inputArray[i].length() == 0){
                return false;
            }
        }
        return true;
    }

    public boolean areInputsValid(String[] inputArray){
        if(!areThereEmptyInputs(inputArray)){
            return false;
        }
        else{
            for(int i = 0 ; i < inputArray.length ; i++){
                for(int j = i+1 ; j < inputArray.length ; j++){
                    if(inputArray[i].charAt(0) == inputArray[j].charAt(0)){
                        return false;
                    }
                }
            }
            return true;
        }    
    }

    public void upperCaseAllArray(String[] inputArray){
        for(int i = 0 ; i < inputArray.length ; i++){
            inputArray[i] = inputArray[i].toUpperCase();
        }
    }

}
