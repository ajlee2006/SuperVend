package sample.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Product {
    private static ArrayList<Product> products;
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

    public Product(String productID, String name, String description, String brand, double price, int temperature, char size, String country, String date, int weight, String image) {
        // just in case
        this(productID,name,description,brand,price,temperature,size,country,date,weight,new ArrayList<String>(Arrays.asList(image)));
    }
}
