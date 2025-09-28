package com.taskmanager.repository;

import com.taskmanager.model.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskRepositoryTest {

    private TaskRepository taskRepository;
    private Task task;

    @BeforeMethod
    public void setUp() {
        taskRepository = new TaskRepository();
        task = new Task("id", "Title", "Desc",
                LocalDate.now().plusDays(1),
                Priority.LOW,
                new Category("cat", "Cat", "Category"),
                Status.PENDING,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void testAddAndGetTask() {
        taskRepository.addTask(task);
        Assert.assertEquals(taskRepository.getTaskById("id").getTitle(), "Title");
    }

    @Test
    public void testUpdateTask() {
        taskRepository.addTask(task);
        task.setTitle("Changed");
        taskRepository.updateTask(task);
        Assert.assertEquals(taskRepository.getTaskById("id").getTitle(), "Changed");
    }

    @Test
    public void testDeleteTask() {
        taskRepository.addTask(task);
        taskRepository.deleteTask("id");
        Assert.assertNull(taskRepository.getTaskById("id"));
    }

    @Test
    public void testExistsAndClear() {
        taskRepository.addTask(task);
        Assert.assertTrue(taskRepository.exists("id"));
        taskRepository.clear();
        Assert.assertFalse(taskRepository.exists("id"));
    }
}
