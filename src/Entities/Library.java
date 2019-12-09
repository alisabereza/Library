package Entities;

import Utils.ConsoleColours;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Library {
    private Semaphore maxAmount;
    private int peopleCount;
    private Door door;
    private ConsoleColours console;
    private String reset;


    public Library(Semaphore maxAmount, int peopleCount) {
        this.maxAmount = maxAmount;
        this.peopleCount = peopleCount;
        this.door = new Door();
        this.console = new ConsoleColours();
        reset = console.commands.get(0);
    }

    public class Visitor implements Runnable {
        private int id;

        public Visitor(int id) {
            this.id = id;
        }

        public void run() {
            String colour = console.commands.get(id % (console.commands.size() - 1) + 1);

            boolean isWaiting = true;
            boolean isInside = false;
            System.out.println(colour + "Visitor " + id + " came to the library" + reset);

            try {
                while (isWaiting) {
                    if (maxAmount.availablePermits() == 0) {
                        System.out.println(colour + "Visitor " + id + " is waiting near the door" + reset);
                        TimeUnit.SECONDS.sleep(3);
                    } else {
                        maxAmount.acquire();
                        System.out.println(colour + "Visitor " + id + " is right outside" + reset);
                        synchronized (door) {
                            door.goThroughTheDoor(id, isInside, colour);
                        }
                        System.out.println(colour + "Visitor " + id + " is inside" + reset);
                        isWaiting = false;
                    }
                }
                TimeUnit.SECONDS.sleep(2);
                isInside = true;
                System.out.println(colour + "Visitor " + id + " is reading" + reset);
                TimeUnit.SECONDS.sleep(10);

                System.out.println(colour + "Visitor " + id + " is leaving the library" + reset);
                System.out.println(colour + "Visitor " + id + " is in front of the door" + reset);
                synchronized (door) {
                    door.goThroughTheDoor(id, isInside, colour);
                }
                System.out.println(colour + "Visitor " + id + " is outside" + reset);
                isInside = false;
                maxAmount.release();
            } catch (InterruptedException e) {
                System.out.println(colour + "Visitor " + id + "is going to the Moon" + reset);
            }
        }
    }

    public void startWorking() {
        for (int i = 0; i < peopleCount; i++) {
            new Thread(new Visitor(i)).start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public class Door {
        private boolean isOpen;

        public Door() {
            this.isOpen = false;
        }

        private void goThroughTheDoor(int id, boolean isInside, String colour) {
            if (!isOpen) {
                isOpen = true;
                if (isInside) {

                    try {
                        System.out.println(colour + "Visitor " + id + " is passing through the Door from the library" + reset);
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println(colour + "Visitor " + id + " is leaving to the Moon" + reset);
                    }
                    isOpen = false;

                } else {

                    try {
                        System.out.println(colour + "Visitor " + id + " is passing through the Door to the library" + reset);
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println(colour + "Visitor " + id + " is going to the Moon" + reset);
                    }
                    isOpen = false;

                }
            }
        }
    }

    public static Library openNewLibrary() {
        Scanner scanner = new Scanner(System.in);

        boolean isDataCorrect = false;
        int maxPlaces = 0, numberOfVisitors = 0;
        while (!isDataCorrect) {
            System.out.println("Enter the number of places in Library: ");
            try {
                maxPlaces = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter the number of Visitors: ");
                numberOfVisitors = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input Mismatch");
            }
            scanner.nextLine();
            if (maxPlaces > 0 & numberOfVisitors > 0) {
                isDataCorrect = true;
            } else {
                System.out.println("Incorrect input");
            }
        }
        return new Library(new Semaphore(maxPlaces, true), numberOfVisitors);

    }
}