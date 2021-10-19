package com.example.yazlab1_proje1;

import com.teamdev.jxbrowser.deps.com.google.protobuf.NullValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangePasswordController {
    @FXML
    TextField oldPasswordTextField, newPasswordTextField, newPasswordAgainTextField;
    @FXML
    Label StatusLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Submit(ActionEvent event) {
        String passwordDB = null;
        try {
            Statement statement = Application.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                passwordDB = resultSet.getString("password");
            }

            if (passwordDB.equals(oldPasswordTextField.getText()) && newPasswordTextField.getText().equals(newPasswordAgainTextField.getText())
                    && !newPasswordTextField.getText().trim().isEmpty()) {
                Statement statement2 = Application.connection.createStatement();

                String updatePassword = "Update users set password = '" + newPasswordTextField.getText() +"' ";
                statement2.executeUpdate(updatePassword);
                StatusLabel.setText("Password change is successful!");
            } else if (newPasswordTextField.getText().trim().isEmpty()) {
                StatusLabel.setText("Password field can't be empty!");
            } else if (!newPasswordTextField.getText().equals(newPasswordAgainTextField.getText())) {
                StatusLabel.setText("Passwords don't match!");
            } else if (!passwordDB.equals(oldPasswordTextField.getText())) {
                StatusLabel.setText("Incorrect Password");
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
