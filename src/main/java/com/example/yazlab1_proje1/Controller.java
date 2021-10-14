package com.example.yazlab1_proje1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.PushbackReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    @FXML
    TextField UsernameTextField, PasswordTextField;
    @FXML
    Label MyLabel;
    String username;
    String password;

    public void submit(){
        username = UsernameTextField.getText();
        password = PasswordTextField.getText();
        String usernameDB = null;
        String passwordDB = null;
        try {
            Statement myStatement = Application.connection.createStatement();
            ResultSet myRs = myStatement.executeQuery("select * from users");
            while(myRs.next()){
                usernameDB = myRs.getString("username");
                passwordDB = myRs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if((username.equals(usernameDB)) && (password.equals(passwordDB))){
            MyLabel.setText("Logged in!");
        }else{
            System.out.println(username + " " +password);
        }
    }

}
