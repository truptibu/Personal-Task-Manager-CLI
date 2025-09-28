package com.taskmanager.util;

import com.taskmanager.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

  //Utility class for validating and parsing date strings.

public class DateValidator {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    // Returns a LocalDate after validating format; throws exception if invalid
    public static LocalDate parseDate(String dateString) throws InvalidDateException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException ex) {
            throw new InvalidDateException("Invalid date format. Expected: " + DEFAULT_FORMAT, ex);
        }
    }

    // Checks if a date is in the past (compared to today)
    public static boolean isPastDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    // Checks if a date is today or future
    public static boolean isTodayOrFuture(LocalDate date) {
        return !date.isBefore(LocalDate.now());
    }

    // Add more date business rules as needed
}
