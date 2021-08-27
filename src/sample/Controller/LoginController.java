package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.*;

public class LoginController {
    @FXML
    private TextField textfieldUsername;
    @FXML
    private PasswordField textfieldPassword;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonExit;

    @FXML
    public void login(ActionEvent event) {
        try {
            String username = textfieldUsername.getText();
            String password = textfieldPassword.getText();

            if (username.startsWith("AD") && Security.authenticate(username, password)) {
                Stage thisStage = (Stage) buttonLogin.getScene().getWindow();
                thisStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/admin.fxml"));
                Parent root = loader.load();
                AdminController adminController = loader.getController();
                adminController.initialise();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("SuperVend - Admin");
                stage.show();
            } else {
                if (Security.authenticate(username, password)) {
                    Stage thisStage = (Stage) buttonLogin.getScene().getWindow();
                    thisStage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/sample.fxml"));
                    Parent root = loader.load();
                    Controller sampleController = loader.getController();
                    sampleController.initialise();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("SuperVend - Main Page");
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("Invalid login credentials.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void closeApp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Close");
        alert.setHeaderText("Exiting app");
        alert.setContentText("Thank you for using SuperVend!");
        alert.showAndWait();
        Stage thisStage = (Stage) buttonExit.getScene().getWindow();
        thisStage.close();
    }
}
