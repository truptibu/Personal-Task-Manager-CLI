package com.taskmanager.service;

import com.taskmanager.model.*;
import com.taskmanager.repository.TaskRepository;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class ReportServiceTest {

    private TaskRepository taskRepository;
    private ReportService reportService;
    private Category testCategory = new Category("cat", "Dummy", "desc");

    @BeforeMethod
    public void setUp() {
        taskRepository = new TaskRepository();
        reportService = new ReportService(taskRepository);

        // Add sample tasks
        taskRepository.addTask(new Task("t1", "Task 1", "Desc1", LocalDate.now().plusDays(1), Priority.HIGH, testCategory, Status.PENDING, LocalDateTime.now(), LocalDateTime.now()));
        taskRepository.addTask(new Task("t2", "Task 2", "Desc2", LocalDate.now().plusDays(1), Priority.LOW, testCategory, Status.COMPLETED, LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    public void testGetTotalTaskCount() {
        Assert.assertEquals(reportService.getTotalTaskCount(), 2);
    }

    @Test
    public void testGetCompletedTaskCount() {
        Assert.assertEquals(reportService.getCompletedTaskCount(), 1);
    }

    @Test
    public void testGetTaskCountByCategory() {
        Map<String, Long> byCat = reportService.getTaskCountByCategory();
        Assert.assertEquals((long) byCat.get("Dummy"), 2L);
    }

    @Test
    public void testGetTaskCountByStatus() {
        Map<Status, Long> byStatus = reportService.getTaskCountByStatus();
        Assert.assertEquals(byStatus.get(Status.PENDING), Long.valueOf(1));
        Assert.assertEquals(byStatus.get(Status.COMPLETED), Long.valueOf(1));
    }
}
