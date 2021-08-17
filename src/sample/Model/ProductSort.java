package sample.Model;

import java.util.Comparator;

public class ProductSort implements Comparator<Product> {

    public int compare(Product o1, Product o2) {
        int c = o1.getProductID().substring(0,2).compareTo(o2.getProductID().substring(0,2));
        if (c != 0) return c;
        c = o1.getIntSize() - o2.getIntSize();
        if (c != 0) return c;
        return o1.getName().compareTo(o2.getName());
    }

}
