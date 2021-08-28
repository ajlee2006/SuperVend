package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.AdminController;
import sample.Controller.LoginController;
import sample.Model.Security;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Security.loadSecurityDB();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.initialise();
        primaryStage.setTitle("SuperVend - Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
