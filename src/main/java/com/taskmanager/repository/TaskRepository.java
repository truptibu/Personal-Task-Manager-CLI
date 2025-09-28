package com.taskmanager.repository;

import com.taskmanager.model.Task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepository {
    private final Map<String, Task> taskMap = new ConcurrentHashMap<>();

    public void addTask(Task task) {
        taskMap.put(task.getTaskId(), task);
    }

    public Task getTaskById(String taskId) {
        return taskMap.get(taskId);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getTaskId(), task);
    }

    public void deleteTask(String taskId) {
        taskMap.remove(taskId);
    }

    public Collection<Task> getAllTasks() {
        return taskMap.values();
    }

    public boolean exists(String taskId) {
        return taskMap.containsKey(taskId);
    }

    public void clear() {
        taskMap.clear();
    }
}
