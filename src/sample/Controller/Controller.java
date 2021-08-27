package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Inventory;
import sample.Model.Product;
import sample.Model.Security;

import java.util.ArrayList;

public class Controller {

    @FXML
    private VBox vboxLeftSide;
    @FXML
    private VBox vboxLeftStuff;
    @FXML
    private VBox vboxChooseItem;
    @FXML
    private ImageView logo;
    @FXML
    private Label labelLoggedInAs;
    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonStart;
    @FXML
    private Label labelRentalCart;
    @FXML
    private TreeView shoppingCart;
    @FXML
    private TextField textfieldRemoveFromCart;
    @FXML
    private Button buttonRemoveFromCart;
    @FXML
    private Button buttonClear;
    @FXML
    private Button buttonCheckout;
    @FXML
    private TreeView allItems;
    @FXML
    private Label labelChoose;
    @FXML
    private TextField textfieldItemNo;
    @FXML
    private Button buttonChoose;
    @FXML
    private VBox vboxPayMoney;
    @FXML
    private TextField textfieldMoney;
    @FXML
    private Button buttonPay;
    @FXML
    private Label labelChange;
    @FXML
    private Button buttonAboutProgrammer;

    private TreeItem<String> allRootItem;

    public void initialise() {
        labelLoggedInAs.setText("Logged in as " + Security.getCurrentUser().getUserID() + "   ");
        vboxLeftStuff.setDisable(true);
        vboxChooseItem.setDisable(true);
        vboxPayMoney.setDisable(true);
    }

    @FXML
    public void startTransaction(ActionEvent e) {
        buttonStart.setDisable(true);
        vboxLeftStuff.setDisable(false);
        vboxChooseItem.setDisable(false);
        vboxPayMoney.setDisable(false);
        loadAllItems();
    }

    public void loadAllItems() {
        try {
            allRootItem = new TreeItem<>("Products");
            TreeItem<String> foodItem = new TreeItem<>("Food");
            TreeItem<String> beverageItem = new TreeItem<>("Beverage");
            TreeItem<String> pharmacyItem = new TreeItem<>("Pharmacy");
            allRootItem.getChildren().addAll(foodItem, beverageItem, pharmacyItem);

            int j = 1;

            for (ArrayList<Object> i : Inventory.getInventoryList()) {
                Product p = (Product) i.get(0);
                TreeItem<String> ti;
                if (p.isFood()) ti = foodItem;
                if (p.isBeverage()) ti = beverageItem;
                if (p.isPharmacy()) ti = pharmacyItem;
                ti = allRootItem;
                TreeItem<String> tii = new TreeItem<String>(p.getName());
                tii.getChildren().add(new TreeItem<String>("Product ID: " + p.getProductID()));
                tii.getChildren().add(new TreeItem<String>("Quantity: " + (int) i.get(1)));
                tii.getChildren().add(new TreeItem<String>("Description: " + p.getDescription()));
                tii.getChildren().add(new TreeItem<String>("Brand: " + p.getBrand()));
                tii.getChildren().add(new TreeItem<String>(String.format("Price: $%.2f", p.getPrice())));
                tii.getChildren().add(new TreeItem<String>("Storage temperature: " + p.getTemperature() + "degC"));
                tii.getChildren().add(new TreeItem<String>("Size: " + p.getSize()));
                tii.getChildren().add(new TreeItem<String>("Country of origin: " + p.getCountry()));
                tii.getChildren().add(new TreeItem<String>("Expiry date: " + p.getDate()));
                tii.getChildren().add(new TreeItem<String>("Net weight: " + p.getWeight()));
                ti.getChildren().add(tii);
            }

            allItems.setRoot(allRootItem);
        } catch (Exception e) {
            alertError(e);
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            alertMessage("Logging out " + Security.getCurrentUser().getUserID());

            Security.logOut();

            Stage thisStage = (Stage) buttonLogout.getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("AirBooks - Login");
            stage.show();
        } catch (Exception e) {
            alertError(e);
        }
    }

    public void alertError(Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public void alertWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void aboutProgrammer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/about.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("SuperVend - About the Programmer");
            stage.showAndWait();
        } catch (Exception e) {
            alertError(e);
        }
    }
}
