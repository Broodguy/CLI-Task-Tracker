package net.broodguy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Task-Tracker-CLI ");
            input = sc.nextLine();
            List<String> parts = new ArrayList<>();
            Matcher m = Pattern.compile("(\"[^\"]+\"|\\S+)").matcher(input);
            while (m.find()) {
                parts.add(m.group().replace("\"", ""));
            }
            String command = parts.get(0);
            String target = parts.get(1);


            switch (command) {
                case "add":
                    Tasks.add(target);
                    break;

                case "update":
                    Tasks.updateDescription(Integer.parseInt(target), parts.get(2));
                    break;

                case "delete":
                    Tasks.delete(Integer.parseInt(target));
                    break;

                case "list":
                    Tasks.list(target);
                    break;

                case "mark":
                    Tasks.updateStatus(Integer.parseInt(target), parts.get(2));
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