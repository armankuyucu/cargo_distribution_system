module com.example.yazlab1_proje1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jxbrowser;
    requires jxbrowser.javafx;
    requires ortools.java;
    requires com.google.common;
    requires json.simple;

    opens com.example.yazlab1_proje1 to javafx.fxml;
    exports com.example.yazlab1_proje1;
}