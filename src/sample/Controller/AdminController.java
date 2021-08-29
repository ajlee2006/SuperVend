package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Model.*;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AdminController {
    @FXML
    private TextField textfieldLogo;
    @FXML
    private Button buttonLogo;
    @FXML
    private TextField textfieldVideo;
    @FXML
    private Button buttonVideo;
    @FXML
    private TreeView treeView;
    @FXML
    private TextField textfieldItem;
    @FXML
    private Button buttonLoad;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextArea textareaCSV;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonLogout;
    @FXML
    private TextField textfieldStock;
    @FXML
    private Button buttonStock;
    @FXML
    private HBox hboxEdit;
    @FXML
    private HBox hboxUpdate;

    private TreeItem<String> treeItem;
    private static final String FILENAME = "Advertisement.csv";

    public void initialise() {
        Product.loadProductDB();
        Inventory.loadInventoryDB();
        ArrayList<Product> productList = Product.getProductList();
        textfieldItem.setText("");
        textareaCSV.setText("");
        textfieldStock.setText("");
        textfieldItem.setDisable(false);
        buttonLoad.setDisable(false);
        buttonDelete.setDisable(false);
        buttonCancel.setDisable(true);
        buttonEdit.setDisable(true);
        buttonAdd.setDisable(false);
        hboxUpdate.setDisable(true);

        try {
            treeItem = new TreeItem<>("Products (" + productList.size() + " items)");
            TreeItem<String> foodItem = new TreeItem<>("Food");
            TreeItem<String> beverageItem = new TreeItem<>("Beverage");
            TreeItem<String> pharmacyItem = new TreeItem<>("Pharmacy");
            treeItem.getChildren().addAll(foodItem, beverageItem, pharmacyItem);

            int j = 0;

            for (Product p : productList) {
                TreeItem<String> ti;
                ti = treeItem;
                if (p.isFood()) ti = foodItem;
                if (p.isBeverage()) ti = beverageItem;
                if (p.isPharmacy()) ti = pharmacyItem;
                TreeItem<String> tii = new TreeItem<>(j + ". " + p.getName());
                tii.getChildren().add(new TreeItem<>("Product ID: " + p.getProductID()));
                tii.getChildren().add(new TreeItem<>("Description: " + p.getDescription()));
                tii.getChildren().add(new TreeItem<>("Brand: " + p.getBrand()));
                tii.getChildren().add(new TreeItem<>(String.format("Price: $%.2f", p.getPrice())));
                tii.getChildren().add(new TreeItem<>("Storage temperature: " + p.getTemperature() + "degC"));
                tii.getChildren().add(new TreeItem<>("Size: " + p.getSize()));
                tii.getChildren().add(new TreeItem<>("Country of origin: " + p.getCountry()));
                tii.getChildren().add(new TreeItem<>("Expiry date: " + p.getDate()));
                tii.getChildren().add(new TreeItem<>("Net weight: " + p.getWeight()));
                ti.getChildren().add(tii);
                j++;
            }
            treeView.setRoot(treeItem);
        } catch (Exception e) {
            alertError(e);
        }
    }

    public void changeLogo(ActionEvent l) {
        LoginController.setFilename(textfieldLogo.getText());
        Controller.setFilename(textfieldLogo.getText());
        alertMessage("Logo changed to " + textfieldLogo.getText());
        textfieldLogo.setText("");
    }

    public void changeVideo(ActionEvent joe) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILENAME, false));
            pw.println(textfieldVideo.getText());
            pw.close();
            alertMessage("Advertisement media player video changed to " + textfieldVideo.getText());
            textfieldVideo.setText("");
        } catch (Exception e) {
            alertWarning("Writing file failed");
        }
    }

    public void load(ActionEvent pleaseHelpMe) {
        try {
            Product p = Product.getProductList().get(Integer.parseInt(textfieldItem.getText()));
            int stock = Inventory.find(p.getProductID());
            textfieldItem.setDisable(true);
            buttonLoad.setDisable(true);
            buttonDelete.setDisable(true);
            buttonCancel.setDisable(false);
            buttonEdit.setDisable(false);
            buttonAdd.setDisable(true);
            hboxUpdate.setDisable(false);
            textfieldStock.setText("" + stock);
            textareaCSV.setText(p.toString());
        } catch (Exception aaaa) {
            alertError(aaaa);
        }
    }

    public void delete(ActionEvent a) {
        Product p = Product.getProductList().get(Integer.parseInt(textfieldItem.getText()));
        Product.delete(p);
        Inventory.delete(p.getProductID(),Inventory.find(p.getProductID()));
        initialise();
    }

    public void cancel(ActionEvent eventAction) {
        initialise();
    }

    public void add(ActionEvent dont) {
        try {
            String[] tokens = textareaCSV.getText().split(",");
            ArrayList<String> images = new ArrayList<>();
            for (int i = 10; i < tokens.length; i++) images.add(tokens[i]);
            if (tokens[0].startsWith("FD"))
                Product.add(new Food(tokens[0], tokens[1], tokens[2], tokens[3], Double.parseDouble(tokens[4]), Integer.parseInt(tokens[5]), tokens[6].charAt(0), tokens[7], tokens[8], Integer.parseInt(tokens[9]), images));
            if (tokens[0].startsWith("BV"))
                Product.add(new Beverage(tokens[0], tokens[1], tokens[2], tokens[3], Double.parseDouble(tokens[4]), Integer.parseInt(tokens[5]), tokens[6].charAt(0), tokens[7], tokens[8], Integer.parseInt(tokens[9]), images));
            if (tokens[0].startsWith("PM"))
                Product.add(new Pharmacy(tokens[0], tokens[1], tokens[2], tokens[3], Double.parseDouble(tokens[4]), Integer.parseInt(tokens[5]), tokens[6].charAt(0), tokens[7], tokens[8], Integer.parseInt(tokens[9]), images));
            initialise();
        } catch (Exception e) {
            alertError(e);
        }
    }

    public void edit(ActionEvent no) {
        try {
            Product p = Product.getProductList().get(Integer.parseInt(textfieldItem.getText()));
            Product.delete(p);
            Inventory.delete(p.getProductID(),Inventory.find(p.getProductID()));
            add(no);
        } catch (Exception e) {
            alertError(e);
        }
    }

    public void updateStock(ActionEvent actionEvent) {
        try {
            Inventory.updateQuantity(Product.getProductList().get(Integer.parseInt(textfieldItem.getText())).getProductID(),Integer.parseInt(textfieldStock.getText()));
            initialise();
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
    public void logout(ActionEvent event) {
        try {
            Security.logOut();

            Stage thisStage = (Stage) buttonLogout.getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.initialise();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("SuperVend - Login");
            stage.show();
        } catch (Exception e) {
            alertError(e);
        }
    }
}
