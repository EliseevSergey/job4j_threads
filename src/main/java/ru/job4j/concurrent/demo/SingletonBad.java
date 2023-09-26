package ru.job4j.concurrent.demo;

public class SingletonBad {
    private static SingletonBad instance;

    /*private SingletonBad() {
    }*/

    public static SingletonBad getInstance() {
        if (instance == null) {
            instance = new SingletonBad();
        }
        return instance;
    }
}
