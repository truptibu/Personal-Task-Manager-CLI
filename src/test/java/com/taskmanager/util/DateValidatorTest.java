package com.taskmanager.util;

import com.taskmanager.exception.InvalidDateException;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;

public class DateValidatorTest {

    @Test
    public void testParseValidDate() throws InvalidDateException {
        LocalDate date = DateValidator.parseDate("2025-12-31");
        Assert.assertEquals(date.getYear(), 2025);
        Assert.assertEquals(date.getMonthValue(), 12);
        Assert.assertEquals(date.getDayOfMonth(), 31);
    }

    @Test(expectedExceptions = InvalidDateException.class)
    public void testParseInvalidDateFormat() throws InvalidDateException {
        DateValidator.parseDate("invalid-date");
    }

    @Test
    public void testIsPastDate() throws InvalidDateException {
        Assert.assertTrue(DateValidator.isPastDate(LocalDate.now().minusDays(1)));
        Assert.assertFalse(DateValidator.isPastDate(LocalDate.now().plusDays(2)));
    }
}
