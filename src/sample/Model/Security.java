package sample.Model;

import java.util.*;
import java.io.*;

public class Security {
    private static ArrayList<Security> securityList;
    private static final String FILENAME = "Login.csv";
    private static Security currentUser;
    private String userID, password;

    public Security(String userID, String password) {
        if (!validUserID(userID)) throw new IllegalArgumentException("Invalid user ID: " + userID);
        if (!validPassword(password)) throw new IllegalArgumentException("Invalid password: " + password);
        this.userID = userID;
        this.password = password;
    }

    public static ArrayList<Security> getSecurityList() {
        return securityList;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public static void loadSecurityDB() {
        try {
            securityList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String s;
            do {
                s = br.readLine();
                if (s != null) {
                    String[] tokens = s.split(",");
                    securityList.add(new Security(tokens[0],tokens[1]));
                }
            } while (s != null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Loading security database failed");
        }
    }

    public static boolean authenticate(String userID, String password) {
        loadSecurityDB();
        if (!validUserID(userID)) throw new IllegalArgumentException("Invalid user ID: " + userID);
        if (!validPassword(password)) throw new IllegalArgumentException("Invalid password: " + password);
        Security user = find(userID);
        if (user == null) return false;
        if (user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public static void logOut() {
        currentUser = null;
    }

    public static boolean validUserID(String userID) {
        return userID.matches("^(AD|BA)\\d{5}$");
    }

    public static boolean validPassword(String password) {
        return password.matches("^[A-Za-z0-9]{6,}$");
    }

    public static Security find(String userID) {
        for (Security i: securityList) if (i.getUserID().equals(userID)) return i;
        throw new IllegalArgumentException("User does not exist: " + userID);
    }

    public static Security getCurrentUser() {
        return currentUser;
    }
}
