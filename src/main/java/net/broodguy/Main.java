package net.broodguy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print("Task-Tracker-CLI ");
            input = sc.nextLine();
            String[] parts = input.split(" ");
            switch (parts[0]) {
                case "add":
                    Tasks.add(parts[1]);
                    break;

                case "update":
                    Tasks.updateDescription(Integer.parseInt(parts[1]), parts[2]);
                    break;

                case "delete":
                    Tasks.delete(Integer.parseInt(parts[1]));
                    break;

                case "list":
                    Tasks.list(parts[1]);
                    break;

                case "mark":
                    Tasks.updateStatus(Integer.parseInt(parts[1]), parts[2]);
                    break;

                case "exit":
                    return;

                default:
                    System.out.println("Not a valid command");
                    break;
            }
        }

    }
}