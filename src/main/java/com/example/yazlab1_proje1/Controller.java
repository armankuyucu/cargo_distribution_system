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
import java.util.ArrayList;

public class Controller {
    @FXML
    TextField UsernameTextField, PasswordTextField;
    @FXML
    Label MyLabel;
    String username;
    String password;

    private Stage stage, stage2, stage3;
    private Scene scene, scene2, scene3;
    private Parent root, root2, root3;


    public void submit(ActionEvent event) throws IOException {
        username = UsernameTextField.getText();
        password = PasswordTextField.getText();
        //String usernameDB = null;
        ArrayList<String> usernameDB = new ArrayList<String>();
        ArrayList<String> passwordDB = new ArrayList<String>();
        try {
            Statement myStatement = Application.connection.createStatement();
            ResultSet myRs = myStatement.executeQuery("select * from users");
            for (int i = 0; myRs.next(); i++) {
                usernameDB.add(i, myRs.getString("username"));
                passwordDB.add(i, myRs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int j = 0; j < usernameDB.size(); j++) {
            if ((username.equals(usernameDB.get(j))) && (password.equals(passwordDB.get(j))) && !UsernameTextField.getText().isEmpty()) {
                MyLabel.setText("Logged in");
                switchToNextScene(event);
                break;
            } else if (UsernameTextField.getText().isEmpty() || PasswordTextField.getText().isEmpty()) {
                MyLabel.setText("Username or password field can't be empty!");
                break;

            } else {
                MyLabel.setText("Please check your credentials and try again.");
            }
        }
    }

    public void switchToNextScene(ActionEvent event) throws IOException{
        root3 = FXMLLoader.load(getClass().getResource("cargo-add.fxml"));
        stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene3 = new Scene(root3);
        stage3.setScene(scene3);
        stage3.show();
    }

    public void switchToChangePassword(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("change-password.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUp(ActionEvent event) throws IOException {
        root2 = FXMLLoader.load(getClass().getResource("sign-up.fxml"));
        stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene2 = new Scene(root2);
        stage2.setScene(scene2);
        stage2.show();
    }
}
