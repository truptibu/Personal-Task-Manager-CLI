package com.taskmanager.service;

import com.taskmanager.exception.*;
import com.taskmanager.model.*;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.util.DateValidator;
import com.taskmanager.util.TaskValidator;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;
    private Category testCategory;

    @BeforeMethod
    public void setUp() {
        taskRepository = new TaskRepository();
        taskService = new TaskService(taskRepository, new TaskValidator(), new DateValidator());
        testCategory = new Category("cat-1", "Test", "Test Category");
    }

    @Test
    public void testCreateTaskSuccess() throws Exception {
        Task task = new Task("task-1", "Valid Title", "Valid Desc", LocalDate.now().plusDays(2),
                Priority.HIGH, testCategory, Status.PENDING, LocalDateTime.now(), LocalDateTime.now());
        taskService.createTask(task);
        Assert.assertEquals(taskService.getTaskById("task-1").getTitle(), "Valid Title");
    }

    @Test(expectedExceptions = DuplicateTaskException.class)
    public void testDuplicateTaskDetection() throws Exception {
        Task t1 = new Task("t1", "Task 1", "Desc", LocalDate.now().plusDays(1),
                Priority.HIGH, testCategory, Status.PENDING, LocalDateTime.now(), LocalDateTime.now());
        taskService.createTask(t1);
        taskService.createTask(t1); // should throw
    }

    @DataProvider(name = "priorityTasks")
    public Object[][] providePriorityTasks() {
        return new Object[][] {
                { "High", Priority.HIGH }, { "Medium", Priority.MEDIUM }, { "Low", Priority.LOW }
        };
    }

    @Test(dataProvider = "priorityTasks")
    public void testTaskCreationWithDifferentPriorities(String name, Priority priority) throws Exception {
        Task task = new Task(name, name, "Desc", LocalDate.now().plusDays(1),
                priority, testCategory, Status.PENDING, LocalDateTime.now(), LocalDateTime.now());
        taskService.createTask(task);
        Assert.assertEquals(task.getPriority(), priority);
    }

    @Test
    public void testGetTasksByStatus() throws Exception {
        Task t1 = new Task("s1", "One", "d", LocalDate.now().plusDays(1), Priority.HIGH, testCategory, Status.PENDING, LocalDateTime.now(), LocalDateTime.now());
        Task t2 = new Task("s2", "Two", "dd", LocalDate.now().plusDays(1), Priority.LOW, testCategory, Status.COMPLETED, LocalDateTime.now(), LocalDateTime.now());
        taskService.createTask(t1);
        taskService.createTask(t2);

        List<Task> pending = taskService.getTasksByStatus(Status.PENDING);
        Assert.assertEquals(pending.size(), 1);
        Assert.assertEquals(pending.get(0).getTaskId(), "s1");
    }

    @Test(expectedExceptions = TaskNotFoundException.class)
    public void testDeleteTask() throws Exception {
        Task t1 = new Task("d1", "Del", "del", LocalDate.now().plusDays(2),
                Priority.HIGH, testCategory, Status.PENDING, LocalDateTime.now(), LocalDateTime.now());
        taskService.createTask(t1);
        taskService.deleteTask("d1");
        taskService.getTaskById("d1"); // should throw
    }
}
