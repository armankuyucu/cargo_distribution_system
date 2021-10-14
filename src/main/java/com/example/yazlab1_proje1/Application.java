package com.example.yazlab1_proje1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class Application extends javafx.application.Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());
    public static Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("gui1.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Stage stage2 = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        Scene scene2 = new Scene(fxmlLoader2.load(), 320, 240);
        stage.setTitle("Hello!");
        stage2.setScene(scene2);
        stage.setScene(scene);
        stage.show();
        stage2.show();
    }

    public static void main(String[] args) {
        connect();
        launch();
    }

    public static Connection connect(){
        String connectionUrl = "jdbc:sqlserver://yazlab.database.windows.net:1433;database=yazlab1_proje1;user=arman@yazlab;password=D9]E4gQveoyELcX^!Xgl;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        try {
            connection = DriverManager.getConnection(connectionUrl);
            log.info("Connection established");
            /*Statement myStatement = connection.createStatement();
            myStatement.executeQuery("select * from users");
            while(myRs.next()){
                System.out.println(myRs.getString("username"));
                System.out.println(myRs.getString("password"));
            }*/
            return connection;
        }catch (Exception e){
            log.info("No connection established. Error message: " + e);
        }

        return null;
    }

}