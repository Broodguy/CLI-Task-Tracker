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
        List<Task> tasks = loadTasks();
        Task newTask = new Task(
                getID(),
                description,
                "todo",
                now(),
                now());
        tasks.add(newTask);
        saveTasks(tasks);
    }

    public static void delete(int id) {
        List<Task> tasks = loadTasks();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (task.getId().equals(id)) {
                tasks.remove(i);
                saveTasks(tasks);
                return;
            }
        }
    }

    public static void updateDescription(int id, String newDescription) {
        List<Task> tasks = loadTasks();

        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setDescription(newDescription);
                task.setUpdatedAt(LocalDateTime.now().toString());
                saveTasks(tasks);
                System.out.println("Description updated.");
                return;
            }
        }

        System.out.println("Task not found.");
    }

    public static void updateStatus(int id, String newStatus){
        List<Task> tasks = loadTasks();

        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setStatus(newStatus);
                task.setUpdatedAt(LocalDateTime.now().toString());
                saveTasks(tasks);
                System.out.println("Status updated.");
                return;
            }
        }

        System.out.println("Task not found.");
    }

    public static void list() {
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
        String formatted = now.format(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"));
        return formatted;
    }

    private static String getID() {

        return "0";

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

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
