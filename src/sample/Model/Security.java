package sample.Model;

import java.util.*;
import java.io.*;

public class Security {
    private static ArrayList<Security> users;
    private static final String FILENAME = "Login.csv";
    private String userID, password;

    public Security(String userID, String password) {
        if (!validUserID(userID)) throw new IllegalArgumentException("Invalid user ID");
        if (!validPassword(password)) throw new IllegalArgumentException("Invalid password");
        this.userID = userID;
        this.password = password;
    }

    public static ArrayList<Security> getUsers() {
        return users;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public static void loadLoginDB() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String s;
            do {
                s = br.readLine();
                if (s != null) {
                    String[] tokens = s.split(",");
                    users.add(new Security(tokens[0],tokens[1]));
                }
            } while (s != null);
        } catch (Exception e) {}
    }

    public static boolean authenticate(String userID, String password) {
        loadLoginDB();
        if (!validUserID(userID)) throw new IllegalArgumentException("Invalid user ID");
        if (!validPassword(password)) throw new IllegalArgumentException("Invalid password");
        for (int i=0;i<users.size();i++) {
            if (users.get(i).getUserID().equals(userID) && users.get(i).getPassword().equals(password)) return true;
        }
        return false;
    }

    public static boolean validUserID(String userID) {
        return userID.matches("^(AD|BA)\\d{5}$");
    }

    public static boolean validPassword(String password) {
        return password.matches("^[A-Za-z0-9]{6,}$");
    }
}
