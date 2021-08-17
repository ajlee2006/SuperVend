package sample.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Inventory {
    private static ArrayList<String> productCategories;
    private static final String FILENAME = "Inventory.csv";
    private static ArrayList<ArrayList<Object>> inventoryList;

    public static void loadInventoryDB() {
        try {
            inventoryList = new ArrayList<>();
            productCategories = new ArrayList<>();
            productCategories.add("Food");
            productCategories.add("Beverage");
            productCategories.add("Pharmacy");
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String s;
            do {
                s = br.readLine();
                if (s != null) {
                    String[] tokens = s.split(",");
                    ArrayList<Object> thing = new ArrayList<>();
                    thing.add(Product.find(tokens[1]));
                    thing.add(tokens[2]);
                    inventoryList.add(thing);
                }
            } while (s != null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Loading inventory database failed");
        }
    }

    public static void writeInventoryDB() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILENAME, false));
            for (ArrayList<Object> i: inventoryList) {
                pw.println(((Product)i.get(0)).getProductID().substring(0,2) + "," + ((Product)i.get(0)).getProductID() + "," + i.get(1));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Writing product database failed");
        }
    }

    public static void add(String productID, int quantity) {
        boolean found = false;
        Product p = Product.find(productID);
        for (ArrayList<Object> i: inventoryList) if (!found && ((Product)i.get(0)).getProductID().equals(productID)) {
            Object o = i.remove(1);
            i.add((int)o+quantity);
            found = true;
        } if (!found) {
            ArrayList<Object> thing = new ArrayList<>();
            thing.add(p);
            thing.add(quantity);
            inventoryList.add(thing);
        }
    }

    public static void delete(String productID, int quantity) {
        boolean found = false;
        Product p = Product.find(productID);
        for (ArrayList<Object> i: inventoryList) if (!found && ((Product)i.get(0)).getProductID().equals(productID)) {
            Object o = i.remove(1);
            int io = (int)o;
            if (quantity > io) throw new IllegalArgumentException("Not enough product to remove: " + productID + " " + quantity);
            if (quantity == io) inventoryList.remove(i);
            else i.add(io-quantity);
            found = true;
        } if (!found) throw new IllegalArgumentException("No product to remove: " + productID + " " + quantity);
    }

    public static void updateQuantity(String productID, int quantity) {
        boolean found = false;
        Product p = Product.find(productID);
        for (ArrayList<Object> i: inventoryList) if (!found && ((Product)i.get(0)).getProductID().equals(productID)) {
            i.remove(1);
            i.add(quantity);
            found = true;
        } if (!found) {
            ArrayList<Object> thing = new ArrayList<>();
            thing.add(p);
            thing.add(quantity);
            inventoryList.add(thing);
        }
    }

    public static int find(String productID) {
        for (ArrayList<Object> i: inventoryList) if (((Product)i.get(0)).getProductID().equals(productID)) return (int)i.get(1);
        throw new IllegalArgumentException("Product not found: " + productID);
    }

    public static ArrayList<String> getProductCategories() {
        return productCategories;
    }

    public static ArrayList<ArrayList<Object>> getInventoryList() {
        return inventoryList;
    }
}
