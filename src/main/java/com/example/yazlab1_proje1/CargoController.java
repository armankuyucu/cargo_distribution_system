package com.example.yazlab1_proje1;

import com.teamdev.jxbrowser.js.JsAccessible;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;

public class CargoController {

    @FXML
    TextField locationLatTF, locationLngTF, customerNameTF;


    public CargoController(){

    }

    public CargoController(Double lat,Double lng) {
        Application.locationLatToJS = lat;
        Application.locationLngToJS = lng;
    }

    int customerID = 1;


    //To access this information from JavaScript
    @JsAccessible
    public void addCargo(ActionEvent event) {

        try {
            PreparedStatement statement = Application.connection.prepareStatement("insert into cargos values('" + customerID + "', '" + customerNameTF.getText() + "' ,  '" + locationLatTF.getText() + "'  , '" + locationLngTF.getText() + "') ");
            statement.executeUpdate();
            customerID++;
            Application.locationLatToJS = Double.parseDouble(locationLatTF.getText());
            Application.locationLngToJS = Double.parseDouble(locationLngTF.getText());
        } catch (Exception SQlException) {
            SQlException.printStackTrace();
        }
    }

    @JsAccessible
    public String sayHelloTo(String firstName) {
        return "Hello " + firstName + "!";
    }

    @JsAccessible
    public Double getLocationLatToJS() {
        return Application.locationLatToJS;
    }

    @JsAccessible
    public Double getLocationLngToJS() {
        return Application.locationLngToJS;
    }
}
