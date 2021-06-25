package com.deadlywords;

import java.io.*;

public class Settings {

    private static final Settings OBJ = new Settings();


    //TODO single String property
    private String homepage;

    private Settings() {
        loadFromFile();
    }

    public static Settings getInstance() {
        return OBJ;
    }

    public void saveToFile(){
        System.out.println("save");
        try {
            FileWriter fstream = new FileWriter("settings.txt");
            BufferedWriter out = new BufferedWriter(fstream);

            out.write(homepage);
            out.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println(homepage);
    }

    public void loadFromFile(){
        System.out.println("load");
        System.out.println(homepage);
        try {
            BufferedReader in = new BufferedReader(new FileReader("settings.txt"));
            homepage = in.readLine();
            in.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println(homepage);
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
        saveToFile();
    }
}

