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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpController {
    @FXML
    TextField userNameTextField, passwordTextField;
    @FXML
    Label statusLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void submit(ActionEvent event) {
        String usernameDB = "";

        try {
            Statement statement = Application.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                usernameDB = resultSet.getString("username");
            }

            if (!usernameDB.equals(userNameTextField.getText()) && !userNameTextField.getText().trim().isEmpty()
                    && !passwordTextField.getText().trim().isEmpty() && !usernameDB.equals(userNameTextField.getText())) {

                PreparedStatement postData = Application.connection.prepareStatement("insert into users values( '" + userNameTextField.getText() + "','" + passwordTextField.getText() + "')");
                postData.executeUpdate();
                statusLabel.setText("User added!");
            } else if (userNameTextField.getText().trim().isEmpty() || passwordTextField.getText().trim().isEmpty()) {
                statusLabel.setText("Username or password field can't be empty!");
            } else if (usernameDB.equals(userNameTextField.getText())) {
                statusLabel.setText("Username is already taken!");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void switchBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("gui1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
