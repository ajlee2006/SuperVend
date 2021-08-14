package sample.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Product {
    private static ArrayList<Product> products;
    public static final String FILENAME = "Product.csv";
    private String productID, name, description, brand, country;
    private double price;
    private int temperature, weight;
    private char size;
    private Date date;
    private ArrayList<String> images;

    public Product(String productID, String name, String description, String brand, double price, int temperature, char size, String country, String date, int weight, ArrayList<String> images) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            this.date = sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date (dd-MM-yyyy)");
        }
        if (!productID.matches("^(FD|BV|PM)\\d{5}$")) throw new IllegalArgumentException("Invalid product ID");
        char us = Character.toUpperCase(size); // just in case
        if (us != 'S' && us != 'M' && us != 'L') throw new IllegalArgumentException("Invalid size (S|M|L)");
        for (String i: images) if (!i.matches("^"+productID+"-\\d+\\.jpg$")) throw new IllegalArgumentException("Invalid images");
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.temperature = temperature;
        this.size = us;
        this.country = country;
        this.weight = weight;
        this.images = images;
    }

    public static void loadProductDB() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String s;
            do {
                s = br.readLine();
                if (s != null) {
                    String[] tokens = s.split(",");
                    ArrayList<String> images = new ArrayList<String>();
                    for (int i=10;i<tokens.length;i++) images.add(tokens[i]);
                    if (tokens[0].startsWith("FD")) products.add(new Food(tokens[0],tokens[1],tokens[2],tokens[3],Double.parseDouble(tokens[4]),Integer.parseInt(tokens[5]),tokens[6].charAt(0),tokens[7],tokens[8],Integer.parseInt(tokens[9]),images));
                    if (tokens[0].startsWith("BV")) products.add(new Beverage(tokens[0],tokens[1],tokens[2],tokens[3],Double.parseDouble(tokens[4]),Integer.parseInt(tokens[5]),tokens[6].charAt(0),tokens[7],tokens[8],Integer.parseInt(tokens[9]),images));
                    if (tokens[0].startsWith("PM")) products.add(new Pharmacy(tokens[0],tokens[1],tokens[2],tokens[3],Double.parseDouble(tokens[4]),Integer.parseInt(tokens[5]),tokens[6].charAt(0),tokens[7],tokens[8],Integer.parseInt(tokens[9]),images));
                }
            } while (s != null);
        } catch (Exception e) {}
    }
}
