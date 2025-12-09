package net.broodguy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Tasks {
    private static final String FILE = "tasks.json";
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type TASK_LIST_TYPE = new TypeToken<List<Task>>() {}.getType();

    //Commands
    public static void add(String description) {
        String lowestIDCache = getLowestUnusedID();

        List<Task> tasks = loadTasks();
        Task newTask = new Task(
                getLowestUnusedID(),
                description,
                "todo",
                now(),
                now());
        tasks.add(newTask);
        tasks.sort(Comparator.comparingInt(t -> Integer.parseInt(t.getId())));
        saveTasks(tasks);
        System.out.println("Task \"" + description + "\" added with ID of " + lowestIDCache);
    }

    public static void delete(int id) {
        List<Task> tasks = loadTasks();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (task.getId().equals(String.valueOf(id))) {
                String idCache = task.getId();
                tasks.remove(i);
                saveTasks(tasks);
                System.out.println("Task ID " + idCache + " deleted");
                return;
            }
        }
        System.out.println("Task with ID " + id + " not found");
    }

    public static void updateDescription(int id, String newDescription) {
        List<Task> tasks = loadTasks();

        for (Task task : tasks) {
            if (task.getId().equals(String.valueOf(id))) {
                String descCache = task.getDescription();
                task.setDescription(newDescription);
                task.setUpdatedAt(LocalDateTime.now().toString());
                saveTasks(tasks);
                System.out.println("Task ID " + id + " description updated from \"" + descCache + "\" to \"" + newDescription + "\"");
                return;
            }
        }

        System.out.println("Task with ID " + id + " not found");
    }

    public static void updateStatus(int id, String newStatus){
        List<Task> tasks = loadTasks();

        for (Task task : tasks) {
            if (task.getId().equals(String.valueOf(id))) {
                String statusCache = task.getStatus();
                task.setStatus(newStatus);
                task.setUpdatedAt(LocalDateTime.now().toString());
                saveTasks(tasks);
                System.out.println("Task ID " + id + " description updated from \"" + statusCache + "\" to \"" + newStatus + "\"");
                return;
            }
        }

        System.out.println("Task with ID " + id + " not found");
    }

    public static void list(String status) {
        List<Task> tasks = loadTasks();

        System.out.println();
        for (Task task : tasks){
            if (status == null){
                System.out.println(task.getId() + " " + task.getDescription());
                System.out.println("\t" + task.getStatus());
                System.out.println("\tCREATED: " + task.getCreatedAt());
                System.out.println("\nUPDATED: " + task.getUpdatedAt());
                System.out.println();
            }else if (task.getStatus().equals(status)){
                System.out.println(task.getId() + " " + task.getDescription());
                System.out.println("\t" + task.getStatus());
                System.out.println("\tCREATED: " + task.getCreatedAt());
                System.out.println("\nUPDATED: " + task.getUpdatedAt());
                System.out.println();
            }

        }
    }

    //GSON Utils
    private static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Task> loadTasks() {
        try (FileReader reader = new FileReader(FILE)) {
            List<Task> tasks = gson.fromJson(reader, TASK_LIST_TYPE);
            if (tasks == null) {
                return new ArrayList<>();
            }
            return tasks;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    //Base Utils
    private static String now() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"));
    }

    private static String getLowestUnusedID() {
        List<Task> tasks = loadTasks();
        int id = 1;

        for (Task task : tasks) {
            if (!task.getId().equals(String.valueOf(id))) {
                return String.valueOf(id);
            }
            id++;
        }
        return String.valueOf(id++);
    }
}

class Task {
    private String id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    public Task(String id, String description, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
