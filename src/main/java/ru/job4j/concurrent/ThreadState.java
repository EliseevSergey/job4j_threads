package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(
                () -> {
                }
        );
        System.out.println("Initial status:");
        System.out.printf("Thread name: %s, thread status: %s %n", first.getName(), first.getState());
        System.out.printf("Thread name: %s, thread status: %s %n %n", second.getName(), second.getState());

        first.start();
        second.start();

        while ((first.getState() != Thread.State.TERMINATED) || (second.getState() != Thread.State.TERMINATED)) {
            System.out.printf("Thread name: %s, thread status: %s %n", first.getName(), first.getState());
            System.out.printf("Thread name: %s, thread status: %s %n", second.getName(), second.getState());
        }

        System.out.println("Both status before close:");
        System.out.printf("Thread name: %s, thread status: %s %n", first.getName(), first.getState());
        System.out.printf("Thread name: %s, thread status: %s %n", second.getName(), second.getState());
        System.out.println("Main is allowed to say Bye Bye");
    }
}
