package sample.Model;

import java.util.ArrayList;

public class Food extends Product {
    public Food(String productID, String name, String description, String brand, double price, int temperature, char size, String country, String date, int weight, ArrayList<String> images) {
        super(productID, name, description, brand, price, temperature, size, country, date, weight, images);
    }
}
