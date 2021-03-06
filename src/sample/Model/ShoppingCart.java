package sample.Model;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShoppingCart {

    private ArrayList<Product> shoppingCart;
    private String id;
    private static final String FILENAME = "Transaction.csv";

    public ShoppingCart() {
        shoppingCart = new ArrayList<>();
        Random r = new Random();
        id = Character.toString(r.nextInt(26) + 65) + Character.toString(r.nextInt(26) + 65) + Character.toString(r.nextInt(26) + 65) + Character.toString(r.nextInt(26) + 65) + Character.toString(r.nextInt(26) + 65);
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void add(Product p) {
        if (shoppingCart.size() == 10) throw new IllegalArgumentException("Shopping cart cannot be increased due to max size (10)");
        shoppingCart.add(p);
    }

    public void delete(Product p) {
        shoppingCart.remove(p);
    }

    public void clearAll() {
        shoppingCart = new ArrayList<>();
    }

    public double sum() {
        double s = 0;
        for (Product i: shoppingCart) s += i.getPrice();
        return s;
    }

    public ArrayList<ArrayList<Product>> bagging() {
        shoppingCart.sort(new ProductSort());
        ArrayList<ArrayList<Product>> aa = new ArrayList<>();
        ArrayList<Product> a = new ArrayList<>();
        Product past = shoppingCart.get(0);
        for (Product i: shoppingCart) {
            int s = 0;
            for (Product j: a) s += j.getIntSize();
            if (s + i.getIntSize() > 8 ||
                    (i.getTemperature()<0 && past.getTemperature()>=0) || (i.getTemperature()<20 && past.getTemperature()>=20) ||
                    (past.getTemperature()<0 && i.getTemperature()>=0) || (past.getTemperature()<20 && i.getTemperature()>=20)) {
                aa.add(a);
                a = new ArrayList<>();
            }
            a.add(i);
            past = i;
        } aa.add(a);
        return aa;
    }

    public double returnCash(double payment) { // also writes transaction
        if (sum() > payment) throw new IllegalArgumentException(String.format("Not enough payment: minimum $%.2f", sum()));

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILENAME, true));
            for (Product i: shoppingCart) {
                pw.println(id+","+new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss").format(new Date())+","+i.getProductID()+","+i.getPrice()+",1");
            }
            pw.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Writing transaction database failed");
        }

        double sum = sum();
        clearAll();
        return payment - sum;
    }
}
