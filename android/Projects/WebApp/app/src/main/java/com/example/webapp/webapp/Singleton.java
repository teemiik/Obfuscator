package com.example.webapp.webapp;

/**
 * Created by bolgov.artem on 12.09.17.
 */

public class Singleton {
    private static Singleton instance;
    private String hw;

    private Singleton (){
        hw = "Hau Huy";
    }

    public static Singleton getInstance() {
        if (null == instance) {
            instance = new Singleton();
        }
        return instance;
    }

    public String getHw() {
        return hw;
    }
}
