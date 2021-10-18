package com.example.yazlab1_proje1;

import com.teamdev.jxbrowser.engine.RenderingMode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;


//JwBrowser imports
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
//JwBrowser imports

public class Application extends javafx.application.Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());
    public static Connection connection;

    @Override
    public void start(Stage stage2) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("gui1.fxml"));
            //FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
            //JxBrowser
            //Licence Key
            System.setProperty("jxbrowser.license.key", "1BNDHFSC1G0LG30NVYOT187A00OAET5MVTA8M6T6ORHI81U1OR6GRTDPFC5E3KUSPZ97OB");

            // Creating and running Chromium engine
            Engine engine = Engine.newInstance(RenderingMode.HARDWARE_ACCELERATED);

            Browser browser = engine.newBrowser();
            BrowserView view = BrowserView.newInstance(browser);

            //TextField addressBar = new TextField("https://html5test.com");
            // addressBar.setOnAction(event -> browser.navigation().loadUrl(addressBar.getText()));
            // Loading the required web page
            //browser.navigation().loadUrl("https://html5test.com");
            browser.navigation().loadUrl("C:\\Users\\User\\Documents\\yazlab1_proje1\\src\\main\\resources\\com\\example\\yazlab1_proje1\\map.html");

            // Creating UI component for rendering web content
            // loaded in the given Browser instance
            BorderPane root = new BorderPane();
            //root.setTop(addressBar);
            root.setCenter(view);
            Scene scene2 = new Scene(root, 1280, 800);


            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("GUI 1");
            stage2.setTitle("GUI 2");
            stage2.setScene(scene2);
            stage.setScene(scene);
            stage.show();
            stage2.show();

            stage2.setOnCloseRequest(event -> engine.close());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connect();
        launch(args);
    }

    public static Connection connect() {
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
        } catch (Exception e) {
            log.info("No connection established. Error message: " + e);
        }

        return null;
    }

}