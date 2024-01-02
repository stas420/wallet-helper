package propertiesManager;

import java.lang.*;
import java.util.*;
import java.io.*;

public class PropertiesManager {

    // === Public ===

    // Constructors:
    public PropertiesManager() {

        this.appPropsPath = this.rootPath + "app.properties";
        loadProperties();

    }

    // Methods:
    public void loadProperties() {

        appProps = new Properties();

        try {
            appProps.load(new FileInputStream(appPropsPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello!");
    }

    // === Private ===

    // Fields:
    private static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private String appPropsPath;
    private Properties appProps;
}
