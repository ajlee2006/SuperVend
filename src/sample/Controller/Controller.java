package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Inventory;
import sample.Model.Product;
import sample.Model.Security;
import sample.Model.ShoppingCart;

import java.util.ArrayList;

public class Controller {

    @FXML
    private VBox vboxLeftSide;
    @FXML
    private VBox vboxLeftStuff;
    @FXML
    private VBox vboxChooseItem;
    @FXML
    private ImageView imageView;
    @FXML
    private Label labelLoggedInAs;
    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonStart;
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

    private TreeItem<String> allRootItem, shoppingCartRootItem;
    private ArrayList<Product> productIDs;
    private ShoppingCart cart;
    private static String filename = "Untitled - Paint.jpeg";

    public static void setFilename(String s) {
        filename = s;
    }

    public void initialise() {
        labelLoggedInAs.setText("Logged in as " + Security.getCurrentUser().getUserID() + "   ");
        vboxLeftStuff.setDisable(true);
        vboxChooseItem.setDisable(true);
        vboxPayMoney.setDisable(true);
        try {
            imageView.setImage(new Image(filename));
        } catch (Exception d) {
            filename = "Untitled - Paint.jpeg";
            try {
                imageView.setImage(new Image(filename));
            } catch (Exception e) {
                filename = "Untitled - Paint.jpeg";
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void startTransaction(ActionEvent e) {
        buttonStart.setDisable(true);
        vboxLeftStuff.setDisable(false);
        vboxChooseItem.setDisable(false);
        Product.loadProductDB();
        Inventory.loadInventoryDB();
        loadAllItems();
        cart = new ShoppingCart();
        shoppingCartRootItem = new TreeItem<>("Shopping cart (0 items)");
        shoppingCart.setRoot(shoppingCartRootItem);
    }

    public void loadAllItems() {
        try {
            productIDs = new ArrayList<>();
            allRootItem = new TreeItem<>("All products");
            TreeItem<String> foodItem = new TreeItem<>("Food");
            TreeItem<String> beverageItem = new TreeItem<>("Beverage");
            TreeItem<String> pharmacyItem = new TreeItem<>("Pharmacy");
            allRootItem.getChildren().addAll(foodItem, beverageItem, pharmacyItem);

            int j = 0;

            for (ArrayList<Object> i : Inventory.getInventoryList()) {
                Product p = (Product) i.get(0);
                TreeItem<String> ti = allRootItem;
                if (p.isFood()) ti = foodItem;
                if (p.isBeverage()) ti = beverageItem;
                if (p.isPharmacy()) ti = pharmacyItem;
                TreeItem<String> tii = new TreeItem<>(j + ". " + p.getName());
                tii.getChildren().add(new TreeItem<>("Product ID: " + p.getProductID()));
                tii.getChildren().add(new TreeItem<>("Quantity: " + i.get(1)));
                tii.getChildren().add(new TreeItem<>("Description: " + p.getDescription()));
                tii.getChildren().add(new TreeItem<>("Brand: " + p.getBrand()));
                tii.getChildren().add(new TreeItem<>(String.format("Price: $%.2f", p.getPrice())));
                tii.getChildren().add(new TreeItem<>("Storage temperature: " + p.getTemperature() + "°C"));
                tii.getChildren().add(new TreeItem<>("Size: " + p.getSize()));
                tii.getChildren().add(new TreeItem<>("Country of origin: " + p.getCountry()));
                tii.getChildren().add(new TreeItem<>("Expiry date: " + p.getDate()));
                tii.getChildren().add(new TreeItem<>("Net weight: " + p.getWeight()));
                ti.getChildren().add(tii);
                productIDs.add(p);
                j++;
            }
            allItems.setRoot(allRootItem);
        } catch (Exception e) {
            alertError(e);
        }
    }

    public void loadShoppingCart() {
        try {
            shoppingCartRootItem = new TreeItem<>("Shopping cart (" + cart.getShoppingCart().size() + " items)");
            TreeItem<String> foodItem = new TreeItem<>("Food");
            TreeItem<String> beverageItem = new TreeItem<>("Beverage");
            TreeItem<String> pharmacyItem = new TreeItem<>("Pharmacy");
            shoppingCartRootItem.getChildren().addAll(foodItem, beverageItem, pharmacyItem);

            int j = 0;

            for (Product p : cart.getShoppingCart()) {
                TreeItem<String> ti;
                ti = shoppingCartRootItem;
                if (p.isFood()) ti = foodItem;
                if (p.isBeverage()) ti = beverageItem;
                if (p.isPharmacy()) ti = pharmacyItem;
                TreeItem<String> tii = new TreeItem<>(j + ". " + p.getName());
                tii.getChildren().add(new TreeItem<>("Product ID: " + p.getProductID()));
                tii.getChildren().add(new TreeItem<>("Description: " + p.getDescription()));
                tii.getChildren().add(new TreeItem<>("Brand: " + p.getBrand()));
                tii.getChildren().add(new TreeItem<>(String.format("Price: $%.2f", p.getPrice())));
                tii.getChildren().add(new TreeItem<>("Storage temperature: " + p.getTemperature() + "°C"));
                tii.getChildren().add(new TreeItem<>("Size: " + p.getSize()));
                tii.getChildren().add(new TreeItem<>("Country of origin: " + p.getCountry()));
                tii.getChildren().add(new TreeItem<>("Expiry date: " + p.getDate()));
                tii.getChildren().add(new TreeItem<>("Net weight: " + p.getWeight()));
                ti.getChildren().add(tii);
                j++;
            }
            shoppingCart.setRoot(shoppingCartRootItem);
        } catch (Exception e) {
            alertError(e);
        }
    }
    @FXML
    public void addToCart(ActionEvent event) {
        try {
            Product product = productIDs.get(Integer.parseInt(textfieldItemNo.getText()));
            Inventory.delete(product.getProductID(), 1);
            cart.add(product);
            loadAllItems();
            loadShoppingCart();
            textfieldItemNo.setText("");
        } catch (Exception e) {
            alertError(e);
        }
    }

    @FXML
    public void removeFromCart(ActionEvent no) {
        try {
            Product product = cart.getShoppingCart().get(Integer.parseInt(textfieldRemoveFromCart.getText()));
            cart.delete(product);
            Inventory.add(product.getProductID(),1);
            loadAllItems();
            loadShoppingCart();
            textfieldRemoveFromCart.setText("");
        } catch (Exception e) {
            alertError(e);
        }
    }

    @FXML
    public void clearCart(ActionEvent z) {
        try {
            System.out.println(cart.getShoppingCart().size());
            while (cart.getShoppingCart().size() != 0) {
                Product p = cart.getShoppingCart().get(0);
                cart.delete(p);
                Inventory.add(p.getProductID(),1);
            }
            loadAllItems();
            loadShoppingCart();
        } catch (Exception e) {
            alertError(e);
        }
    }

    @FXML
    public void checkout(ActionEvent adsfhiauosdhfoaids) {
        try {
            if (cart.getShoppingCart().size() == 0) throw new IllegalArgumentException("Please put stuff in your cart first");
            vboxLeftStuff.setDisable(true);
            vboxChooseItem.setDisable(true);
            vboxPayMoney.setDisable(false);
        } catch (Exception e) {
            alertError(e);
        }
    }

    @FXML
    public void payMoney(ActionEvent money) {
        try {
            ArrayList<ArrayList<Product>> bag = cart.bagging();
            System.out.println(bag);
            StringBuilder s = new StringBuilder("Here are your bags\n");
            int j = 1;
            for (ArrayList<Product> i: bag) {
                s.append("Bag ").append(j).append(": ");
                for (Product m: i) {
                    s.append(m.getName()).append(", ");
                } s = new StringBuilder(s.substring(0, s.length() - 2) + "\n");
                j++;
            }
            alertMessage(String.format("Your change is $%.2f", cart.returnCash(Double.parseDouble(textfieldMoney.getText()))));
            alertMessage(s.toString());
            vboxPayMoney.setDisable(true);
            buttonStart.setDisable(false);
            loadShoppingCart();
            loadAllItems();
            textfieldMoney.setText("");
        } catch (Exception e) {
            alertError(e);
        }
    }

    @FXML
    public void viewImages(ActionEvent image) {
        try {
            Product product = productIDs.get(Integer.parseInt(textfieldItemNo.getText()));
            HBox hbox = new HBox();
            double width = 0, height = 0;
            for (String i: product.getImages()) {
                Image img = new Image(i);
                width += img.getWidth();
                if (img.getHeight() > height) height = img.getHeight();
                ImageView imageView = new ImageView(img);
                hbox.getChildren().add(imageView);
            }
            Scene scene = new Scene(hbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(product.getName());
            stage.showAndWait();
        } catch (Exception e) {
            alertError(e);
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            alertMessage("Your shopping cart will be cleared. Thank you for using SuperVend, " + Security.getCurrentUser().getUserID() + "!");
            if (cart != null && cart.getShoppingCart().size() != 0) clearCart(event);

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
            AboutController aboutController = loader.getController();
            aboutController.initialise();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("SuperVend - About the Programmer");
            stage.showAndWait();
            aboutController.stop();
        } catch (Exception e) {
            alertError(e);
        }
    }


}
