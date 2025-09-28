package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.model.Status;
import com.taskmanager.repository.TaskRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {
    private final TaskRepository taskRepository;

    public ReportService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public int getTotalTaskCount() {
        return taskRepository.getAllTasks().size();
    }

    public int getCompletedTaskCount() {
        return (int) taskRepository.getAllTasks().stream()
                .filter(task -> task.getStatus() == Status.COMPLETED)
                .count();
    }

    public int getOverdueTaskCount() {
        return (int) new TaskService(taskRepository, null, null).getOverdueTasks().size();
    }

    public Map<String, Long> getTaskCountByCategory() {
        return taskRepository.getAllTasks().stream()
                .collect(Collectors.groupingBy(
                        task -> task.getCategory() != null ? task.getCategory().getName() : "Uncategorized",
                        Collectors.counting()
                ));
    }

    public Map<Status, Long> getTaskCountByStatus() {
        return taskRepository.getAllTasks().stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }


}
