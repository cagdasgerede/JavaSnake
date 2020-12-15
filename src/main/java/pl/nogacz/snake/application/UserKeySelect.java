package pl.nogacz.snake.application;

import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import pl.nogacz.snake.Snake;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class UserKeySelect {

    private String message;

    public char[] getUser1Conrols() {
        return User1Conrols;
    }

    public char[] getUser2Conrols() {
        return User2Conrols;
    }

    char[] User1Conrols = new char[4];
    char[] User2Conrols = new char[4];

    public UserKeySelect(String message) {
        this.message = message;
    }

    public boolean printDialog()
    {
        Dialog dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setHeaderText("Please define keys for players\nLeft Snake is Player 1\nRight Snake is Player2" +
                "\n\nNote: Only first character is used that you typed");
        dialog.setGraphic(null);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setStyle("-fx-background-color: #ffffff; -fx-border-color: #8a7878 #8a7878 #8a7878 #e7eaec; -fx-border-width: 6;");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

        // VBox
        VBox alertVBox = new VBox();
        VBox.setVgrow(alertVBox, Priority.ALWAYS);
        alertVBox.setPrefWidth(400.0);
        alertVBox.setSpacing(10);
        Label label = new Label("Enter Player1 Up");
        TextField textField = new TextField();
        Label label2 = new Label("Enter Player1 Down");
        TextField textField2 = new TextField();
        Label label3 = new Label("Enter Player1 Left");
        TextField textField3 = new TextField();
        Label label4 = new Label("Enter Player1 Right");
        TextField textField4 = new TextField();

        Label label5 = new Label("Enter Player2 Up");
        TextField textField5 = new TextField();
        Label label6 = new Label("Enter Player2 Down");
        TextField textField6 = new TextField();
        Label label7 = new Label("Enter Player2 Left");
        TextField textField7 = new TextField();
        Label label8 = new Label("Enter Player2 Right");
        TextField textField8 = new TextField();

        alertVBox.getChildren().addAll(label, textField,label2,textField2,label3,textField3,label4,textField4,label5,textField5,label6,textField6,label7,textField7,label8,textField8);
        // END VBox

        dialog.getDialogPane().setContent(alertVBox);
        // Request focus on the username field by default.
        Platform.runLater(() -> textField.requestFocus());

        // result
        Optional<List<String>> result = dialog.showAndWait();
        String keys = "";

        //checks for insufficient input
        try {
            User1Conrols[0] = textField.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User1Conrols[0];
            User1Conrols[1] = textField2.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User1Conrols[1];
            User1Conrols[2] = textField3.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User1Conrols[2];
            User1Conrols[3] = textField4.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User1Conrols[3];
            User2Conrols[0] = textField5.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User2Conrols[0];
            User2Conrols[1] = textField6.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User2Conrols[1];
            User2Conrols[2] = textField7.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User2Conrols[2];
            User2Conrols[3] = textField8.getCharacters().toString().toUpperCase().charAt(0);
            keys = keys + User2Conrols[3];
        }catch (Exception e){
            return false;
        }


        // check for any 2 of the key is same
        boolean duplicateCheck = true;
        for (int i = 0; i < keys.length(); i++) {
            char a = keys.charAt(i);
            for (int j = i+1; j < keys.length(); j++) {
                if (a == keys.charAt(j)) {
                    duplicateCheck = false;
                    break;
                }
            }
            if(!duplicateCheck)
                break;
        }
        return  duplicateCheck;
    }



}
