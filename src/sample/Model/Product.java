package sample.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Product {
    private static ArrayList<Product> productList;
    private static final String FILENAME = "Product.csv";
    private String productID, name, description, brand, country;
    private double price;
    private int temperature, weight, size;
    private Date date;
    private ArrayList<String> images;

    public Product(String productID, String name, String description, String brand, double price, int temperature, char size, String country, String date, int weight, ArrayList<String> images) {
        try {
            this.date = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date (dd-MM-yyyy)");
        }
        if (!productID.matches("^(FD|BV|PM)\\d{5}$")) throw new IllegalArgumentException("Invalid product ID: " + productID);
        char us = Character.toUpperCase(size); // just in case
        if (us != 'S' && us != 'M' && us != 'L') throw new IllegalArgumentException("Invalid size (S|M|L): " + us);
        int usi;
        if (us == 'S') usi = 1; else if (us == 'M') usi = 2; else usi = 4;
        for (String i: images) if (!i.matches("^"+productID+"-\\d+\\.jpg$")) throw new IllegalArgumentException("Invalid image: " + i);
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.temperature = temperature;
        this.size = usi;
        this.country = country;
        this.weight = weight;
        this.images = images;
    }

    public static void loadProductDB() {
        try {
            productList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String s;
            ArrayList<String> temp = new ArrayList<>();
            do {
                s = br.readLine();
                if (s != null) {
                    String[] tokens = s.split(",");
                    if (temp.contains(tokens[0])) throw new IllegalArgumentException("Duplicate element: " + tokens[0]);
                    temp.add(tokens[0]);
                    ArrayList<String> images = new ArrayList<>();
                    for (int i=10;i<tokens.length;i++) images.add(tokens[i]);
                    if (tokens[0].startsWith("FD")) productList.add(new Food(tokens[0],tokens[1],tokens[2],tokens[3],Double.parseDouble(tokens[4]),Integer.parseInt(tokens[5]),tokens[6].charAt(0),tokens[7],tokens[8],Integer.parseInt(tokens[9]),images));
                    if (tokens[0].startsWith("BV")) productList.add(new Beverage(tokens[0],tokens[1],tokens[2],tokens[3],Double.parseDouble(tokens[4]),Integer.parseInt(tokens[5]),tokens[6].charAt(0),tokens[7],tokens[8],Integer.parseInt(tokens[9]),images));
                    if (tokens[0].startsWith("PM")) productList.add(new Pharmacy(tokens[0],tokens[1],tokens[2],tokens[3],Double.parseDouble(tokens[4]),Integer.parseInt(tokens[5]),tokens[6].charAt(0),tokens[7],tokens[8],Integer.parseInt(tokens[9]),images));
                }
            } while (s != null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Loading product database failed");
        }
    }

    @Override
    public String toString() {
        return  productID + ',' +
                name + ',' +
                description + ',' +
                brand + ',' +
                country + ',' +
                price + ',' +
                temperature + ',' +
                weight + ',' +
                size + ',' +
                getDate() + ',' +
                images;
    }

    public static void writeProductDB() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILENAME, false));
            for (Product i: productList) {
                pw.println(i.toString());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Writing product database failed");
        }
    }

    public static Product find(String productID) {
        for (Product i: productList) if (i.getProductID().equals(productID)) return i;
        throw new IllegalArgumentException("Product does not exist: " + productID);
    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getCountry() {
        return country;
    }

    public double getPrice() {
        return price;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWeight() {
        return weight;
    }

    public char getSize() {
        char[] c = {' ','S','M',' ','L'};
        return c[size];
    }

    public int getIntSize() {
        return size;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public ArrayList<String> getImages() {
        return images;
    }
}
