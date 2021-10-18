package com.example.yazlab1_proje1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void submit() {
        username = UsernameTextField.getText();
        password = PasswordTextField.getText();
        String usernameDB = null;
        String passwordDB = null;
        try {
            Statement myStatement = Application.connection.createStatement();
            ResultSet myRs = myStatement.executeQuery("select * from users");
            while (myRs.next()) {
                usernameDB = myRs.getString("username");
                passwordDB = myRs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ((username.equals(usernameDB)) && (password.equals(passwordDB))) {
            MyLabel.setText("Logged in!");
        } else {
            System.out.println(username + " " + password);
        }
    }

    public void switchTochangePassword(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("change-password.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGui1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("gui1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
