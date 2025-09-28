package com.taskmanager.service;

import com.taskmanager.model.Task;

public class NotificationService {
    // This could be expanded to use timers, scheduled tasks, or integration with an actual notification system

    public void sendReminder(Task task) {
        System.out.println("Reminder: Task '" + task.getTitle() + "' is due on " + task.getDueDate());

    }
}
