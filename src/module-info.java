module SuperVend {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    opens sample.Controller to javafx.fxml;
    opens sample.View;
    opens sample;
}