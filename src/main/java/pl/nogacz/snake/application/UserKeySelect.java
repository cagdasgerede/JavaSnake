package pl.nogacz.snake.application;

import javafx.scene.control.ToolBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class UserKeySelect {

    String[] user1Conrols = new String[4];
    String[] user2Conrols = new String[4];
    private String message;

    public UserKeySelect(String message) {
        this.message = message;
    }

    public String[] getUser1Conrols() {
        return user1Conrols;
    }

    public String[] getUser2Conrols() {
        return user2Conrols;
    }

    public boolean printDialog() {
        Dialog dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setHeaderText("Please define keys for players\nLeft Snake is Player 1\nRight Snake is Player2" +
                "\n\nNote: In order to continue, you should select one and unique per key." +
                "\nAlso, please do not forget to click to Apply for that key" +
                "\nOnce you finished selecting keys, then you may click OK to start the game");
        dialog.setGraphic(null);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);

        ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

        // VBox
        VBox alertVBox = new VBox();
        VBox.setVgrow(alertVBox, Priority.ALWAYS);
        alertVBox.setPrefWidth(400.0);
        alertVBox.setSpacing(10);
        Accordion accordion = new Accordion();

        Label label1 = new Label("Player1 Up");
        ChoiceBox<String> keyChoiceBox1 = new ChoiceBox<>();
        keyChoiceBox1.setValue("Please select...");
        fillChoiceBox(keyChoiceBox1);
        Button applyButton1 = new Button("Apply " + label1.getText());
        applyButton1.setOnAction(e -> user1Conrols[0] = getUserKey(keyChoiceBox1));
        ToolBar toolBar1 = new ToolBar();
        toolBar1.getItems().addAll(keyChoiceBox1, applyButton1);

        Label label2 = new Label("Player1 Down");
        ChoiceBox<String> keyChoiceBox2 = new ChoiceBox<>();
        keyChoiceBox2.setValue("Please select...");
        fillChoiceBox(keyChoiceBox2);
        Button applyButton2 = new Button("Apply " + label2.getText());
        applyButton2.setOnAction(e -> user1Conrols[1] = getUserKey(keyChoiceBox2));
        ToolBar toolBar2 = new ToolBar();
        toolBar2.getItems().addAll(keyChoiceBox2, applyButton2);

        Label label3 = new Label("Player1 Left");
        ChoiceBox<String> keyChoiceBox3 = new ChoiceBox<>();
        keyChoiceBox3.setValue("Please select...");
        fillChoiceBox(keyChoiceBox3);
        Button applyButton3 = new Button("Apply " + label3.getText());
        applyButton3.setOnAction(e -> user1Conrols[2] = getUserKey(keyChoiceBox3));
        ToolBar toolBar3 = new ToolBar();
        toolBar3.getItems().addAll(keyChoiceBox3, applyButton3);

        Label label4 = new Label("Player1 Right");
        ChoiceBox<String> keyChoiceBox4 = new ChoiceBox<>();
        keyChoiceBox4.setValue("Please select...");
        fillChoiceBox(keyChoiceBox4);
        Button applyButton4 = new Button("Apply " + label4.getText());
        applyButton4.setOnAction(e -> user1Conrols[3] = getUserKey(keyChoiceBox4));
        ToolBar toolBar4 = new ToolBar();
        toolBar4.getItems().addAll(keyChoiceBox4, applyButton4);

        VBox user1VBox = new VBox();
        user1VBox.getChildren().addAll(label1,toolBar1,label2,toolBar2,label3,toolBar3,label4,toolBar4);
        TitledPane user1TitledPane = new TitledPane("Player 1 Controls", user1VBox);

        Label label5 = new Label("Player2 Up");
        ChoiceBox<String> keyChoiceBox5 = new ChoiceBox<>();
        keyChoiceBox5.setValue("Please select...");
        fillChoiceBox(keyChoiceBox5);
        Button applyButton5 = new Button("Apply " + label5.getText());
        applyButton5.setOnAction(e -> user2Conrols[0] = getUserKey(keyChoiceBox5));
        ToolBar toolBar5 = new ToolBar();
        toolBar5.getItems().addAll(keyChoiceBox5, applyButton5);

        Label label6 = new Label("Player2 Down");
        ChoiceBox<String> keyChoiceBox6 = new ChoiceBox<>();
        keyChoiceBox6.setValue("Please select...");
        fillChoiceBox(keyChoiceBox6);
        Button applyButton6 = new Button("Apply " + label6.getText());
        applyButton6.setOnAction(e -> user2Conrols[1] = getUserKey(keyChoiceBox6));
        ToolBar toolBar6 = new ToolBar();
        toolBar6.getItems().addAll(keyChoiceBox6, applyButton6);

        Label label7 = new Label("Player2 Left");
        ChoiceBox<String> keyChoiceBox7 = new ChoiceBox<>();
        keyChoiceBox7.setValue("Please select...");
        fillChoiceBox(keyChoiceBox7);
        Button applyButton7 = new Button("Apply " + label7.getText());
        applyButton7.setOnAction(e -> user2Conrols[2] = getUserKey(keyChoiceBox7));
        ToolBar toolBar7 = new ToolBar();
        toolBar7.getItems().addAll(keyChoiceBox7, applyButton7);

        Label label8 = new Label("Player2 Right");
        ChoiceBox<String> keyChoiceBox8 = new ChoiceBox<>();
        keyChoiceBox8.setValue("Please select...");
        fillChoiceBox(keyChoiceBox8);
        Button applyButton8 = new Button("Apply " + label8.getText());
        applyButton8.setOnAction(e -> user2Conrols[3] = getUserKey(keyChoiceBox8));
        ToolBar toolBar8 = new ToolBar();
        toolBar8.getItems().addAll(keyChoiceBox8, applyButton8);

        VBox user2VBox = new VBox();
        user2VBox.getChildren().addAll(label5,toolBar5,label6,toolBar6,label7,toolBar7,label8,toolBar8);
        TitledPane user2TitledPane = new TitledPane("Player 2 Controls", user2VBox);

        alertVBox.getChildren().addAll(user1TitledPane, user2TitledPane);
        // END VBox

        dialog.getDialogPane().setContent(alertVBox);
        dialog.showAndWait();

        //checks for insufficient input
        final int userArrayLength = 4;
        try {
            for (int i = 0; i < userArrayLength; i++) {
                String checkUserOne = user1Conrols[i];
                String checkUserTwo = user2Conrols[i];
                if(checkUserOne == null)
                    throw new Exception("User key cannot be null. "+ user1Conrols[i] + " is null");
                if(checkUserTwo == null)
                    throw new Exception("User key cannot be null. "+ user2Conrols[i] + " is null");
            }
        } catch (Exception e) {
            e.getMessage();
            return false;
        }

        // check for any 2 of the key is same
        boolean duplicateCheck = true;
        for (int i = 0; i < userArrayLength; i++) {
            for (int j = i+1; j < userArrayLength; j++) {
                if(user1Conrols[i].equals(user1Conrols[j]) || user2Conrols[i].equals(user2Conrols[j])){
                    duplicateCheck = false;
                    break;
                }
            }
        }

        for (int i = 0; i < userArrayLength; i++) {
            for (int j = 0; j < userArrayLength; j++) {
                if(user1Conrols[i].equals(user2Conrols[j])){
                    duplicateCheck = false;
                    break;
                }
            }
        }

        return duplicateCheck;
    }

    private String getUserKey(ChoiceBox choiceBox) {
        return choiceBox.getValue().toString();
    }

    private void fillChoiceBox(ChoiceBox choiceBox) {
        choiceBox.getItems().add("UP");
        choiceBox.getItems().add("DOWN");
        choiceBox.getItems().add("LEFT");
        choiceBox.getItems().add("RIGHT");

        char start = 'A';
        char end = 'Z';
        while (start <= end)
            choiceBox.getItems().add("" + start++);
    }
}
