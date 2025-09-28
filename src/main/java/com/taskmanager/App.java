package com.taskmanager;

import com.taskmanager.exception.*;
import com.taskmanager.model.Category;
import com.taskmanager.model.Priority;
import com.taskmanager.model.Status;
import com.taskmanager.model.Task;
import com.taskmanager.repository.CategoryRepository;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.service.CategoryService;
import com.taskmanager.service.TaskService;
import com.taskmanager.util.DateValidator;
import com.taskmanager.util.TaskValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Setup repositories and services
        TaskRepository taskRepository = new TaskRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        TaskService taskService = new TaskService(taskRepository, new TaskValidator(), new DateValidator());
        CategoryService categoryService = new CategoryService(categoryRepository);

        // Add an initial work category for demonstration
        Category workCategory = new Category("1", "Work", "Work-related");
        categoryService.addCategory(workCategory);

        System.out.println("Welcome to Personal Task Manager CLI!");
        printHelp();

        while (true) {
            System.out.print("\nEnter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            try {
                switch (command) {
                    case "help":
                        printHelp();
                        break;
                    case "add":
                        addTask(scanner, taskService, categoryService);
                        break;
                    case "list":
                        listTasks(taskRepository);
                        break;
                    case "listbycategory":
                        listByCategory(scanner, taskService, categoryService);
                        break;
                    case "listbystatus":
                        listByStatus(scanner, taskService);
                        break;
                    case "delete":
                        deleteTask(scanner, taskService);
                        break;
                    case "update":
                        updateTask(scanner, taskService, categoryService);
                        break;
                    default:
                        System.out.println("Unknown command! Type 'help' for commands.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printHelp() {
        System.out.println("\nAvailable commands:");
        System.out.println("  add - Add a new task");
        System.out.println("  update - Update an existing task");
        System.out.println("  list - List all tasks");
        System.out.println("  listbycategory - List tasks for a category");
        System.out.println("  listbystatus - List tasks by status");
        System.out.println("  delete - Delete a task by ID");
        System.out.println("  help - Show this help");
        System.out.println("  exit - Exit application");
    }


    private static void addTask(Scanner scanner, TaskService taskService, CategoryService categoryService) {
        try {
            System.out.print("Enter Task ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter Title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter Description: ");
            String description = scanner.nextLine().trim();

            System.out.print("Enter Due Date (yyyy-MM-dd): ");
            String dueDateStr = scanner.nextLine().trim();
            LocalDate dueDate = DateValidator.parseDate(dueDateStr);

            System.out.print("Enter Priority (HIGH, MEDIUM, LOW): ");
            String priorityStr = scanner.nextLine().trim().toUpperCase();
            Priority priority = Priority.valueOf(priorityStr);

            // Show current categories
            List<Category> allCategories = categoryService.getAllCategories();
            System.out.println("Available categories:");
            for (Category c : allCategories) {
                System.out.println("  " + c.getCategoryId() + ": " + c.getName());
            }
            System.out.print("Enter Category ID (or type new to create): ");
            String catId = scanner.nextLine().trim();
            Category cat = categoryService.getCategoryById(catId);
            if (cat == null) {
                System.out.print("Category not found! Enter name for new category: ");
                String catName = scanner.nextLine().trim();
                System.out.print("Description for new category: ");
                String catDesc = scanner.nextLine().trim();
                cat = new Category(catId, catName, catDesc);
                categoryService.addCategory(cat);
                System.out.println("New category created.");
            }

            // Prompt for status with a loop until valid.
            Status status = null;
            while (status == null) {
                System.out.print("Enter Status (PENDING, IN_PROGRESS, COMPLETED): ");
                String statusStr = scanner.nextLine().trim().toUpperCase();
                try {
                    status = Status.valueOf(statusStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status! Must be PENDING, IN_PROGRESS, or COMPLETED. Try again.");
                }
            }

            Task task = new Task(
                    id,
                    title,
                    description,
                    dueDate,
                    priority,
                    cat,
                    status,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            taskService.createTask(task);
            System.out.println("Task created.");

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (DuplicateTaskException | TaskValidationException | InvalidDateException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private static void listTasks(TaskRepository taskRepository) {
        Collection<Task> all = taskRepository.getAllTasks();
        if (all.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("-- All Tasks --");
            for (Task t : all) {
                printTask(t);
            }
        }
    }

    private static void printTask(Task t) {
        System.out.println("------------------------------");
        System.out.println("Task ID     : " + t.getTaskId());
        System.out.println("Title       : " + t.getTitle());
        System.out.println("Description : " + t.getDescription());
        System.out.println("Due Date    : " + t.getDueDate());
        System.out.println("Priority    : " + t.getPriority());
        System.out.println("Category    : " + (t.getCategory() != null ? t.getCategory().getName() : "None"));
        System.out.println("Status      : " + t.getStatus());
        System.out.println("Created     : " + t.getCreatedDate());
        System.out.println("Modified    : " + t.getLastModifiedDate());
    }

    private static void listByCategory(Scanner scanner, TaskService taskService, CategoryService categoryService) {
        // Show all categories first
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }
        System.out.println("-- Available Categories --");
        for (Category c : categories) {
            System.out.println("  " + c.getCategoryId() + ": " + c.getName());
        }
        System.out.print("Enter Category ID: ");
        String catId = scanner.nextLine().trim();
        List<Task> tasks = taskService.getTasksByCategory(catId);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found in this category.");
        } else {
            System.out.println("-- Tasks in Category --");
            for (Task t : tasks) {
                printTask(t); // Pretty-print format
            }
        }
    }

    private static void listByStatus(Scanner scanner, TaskService taskService) {
        // Prompt for status with a loop until valid
        Status status = null;
        while (status == null) {
            System.out.print("Enter Status (PENDING, IN_PROGRESS, COMPLETED): ");
            String statusStr = scanner.nextLine().trim().toUpperCase();
            try {
                status = Status.valueOf(statusStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status! Must be PENDING, IN_PROGRESS, or COMPLETED. Try again.");
            }
        }
        List<Task> tasks = taskService.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            System.out.println("No tasks with status " + status);
        } else {
            System.out.println("-- Tasks with status " + status + " --");
            for (Task t : tasks) {
                printTask(t);
            }
        }
    }

    private static void deleteTask(Scanner scanner, TaskService taskService) {
        System.out.print("Enter Task ID to delete: ");
        String id = scanner.nextLine().trim();
        try {
            taskService.deleteTask(id);
            System.out.println("Deleted task " + id);
        } catch (TaskNotFoundException e) {
            System.out.println("Task not found!");
        }
    }

    private static void updateTask(Scanner scanner, TaskService taskService, CategoryService categoryService) {
        System.out.print("Enter Task ID to update: ");
        String id = scanner.nextLine().trim();
        try {
            Task original = taskService.getTaskById(id);

            System.out.println("Leave field empty to keep current value.");

            System.out.print("Current Title: " + original.getTitle() + "\nNew Title: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) title = original.getTitle();

            System.out.print("Current Description: " + original.getDescription() + "\nNew Description: ");
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) description = original.getDescription();

            System.out.print("Current Due Date: " + original.getDueDate() + "\nNew Due Date (yyyy-MM-dd): ");
            String dueDateStr = scanner.nextLine().trim();
            LocalDate dueDate = dueDateStr.isEmpty() ? original.getDueDate() : DateValidator.parseDate(dueDateStr);

            System.out.print("Current Priority: " + original.getPriority() + "\nNew Priority (HIGH, MEDIUM, LOW): ");
            String priorityStr = scanner.nextLine().trim().toUpperCase();
            Priority priority = priorityStr.isEmpty() ? original.getPriority() : Priority.valueOf(priorityStr);

            // Show all available categories
            List<Category> allCats = categoryService.getAllCategories();
            System.out.println("Available categories:");
            for (Category c : allCats) {
                System.out.println("  " + c.getCategoryId() + ": " + c.getName());
            }
            System.out.print("Current Category: " + (original.getCategory() != null ? original.getCategory().getName() : "None")
                    + "\nNew Category ID (or leave empty): ");
            String catId = scanner.nextLine().trim();
            Category cat = catId.isEmpty() ? original.getCategory() : categoryService.getCategoryById(catId);
            if (cat == null) cat = original.getCategory();

            // Status prompt with loop
            Status status = original.getStatus();
            while (true) {
                System.out.print("Current Status: " + original.getStatus() + "\nNew Status (PENDING, IN_PROGRESS, COMPLETED): ");
                String statusStr = scanner.nextLine().trim().toUpperCase();
                if (statusStr.isEmpty()) {
                    break;
                }
                try {
                    status = Status.valueOf(statusStr);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status! Must be PENDING, IN_PROGRESS, or COMPLETED. Try again or leave blank.");
                }
            }

            // Set lastModifiedDate to now
            Task updatedTask = new Task(
                    id, title, description, dueDate, priority, cat, status,
                    original.getCreatedDate(), LocalDateTime.now()
            );
            taskService.updateTask(id, updatedTask);
            System.out.println("Task updated.");

        } catch (TaskNotFoundException e) {
            System.out.println("Task not found!");
        } catch (Exception e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

}
