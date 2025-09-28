Personal Task Manager (CLI)
====================================

A full-featured backend + CLI for personal task management.
This project demonstrates clean Java, OOP, validation, exception handling, Maven, and a comprehensive TestNG suite.

-------------------------------------------------------------------------------

Project Structure
-----------------
personal-task-manager/
├── pom.xml
├── README.txt
├── src/
│   ├── main/
│   │   └── java/
│   │        └── com/taskmanager/
│   │            ├── App.java               
│   │            ├── model/
│   │            │    ├── Category.java
│   │            │    ├── Task.java
│   │            │    ├── Priority.java
│   │            │    └── Status.java
│   │            ├── service/
│   │            ├── repository/
│   │            ├── exception/
│   │            └── util/
│   └── test/
│        └── java/
│             └── com/taskmanager/
│                  ├── service/
│                  ├── repository/
│                  └── util/

-------------------------------------------------------------------------------

Building and Running
--------------------

**Requirements:**
- Java 8+ (any recent JDK is fine)
- Maven 3.6+ (add 'mvn' to your PATH)

**To Build:**
1. Open a terminal in the project root.
2. Run:
   mvn clean install

**To Run the CLI:**
- In Eclipse/IntelliJ: Right-click App.java → Run As → Java Application
- Or in terminal:
  mvn exec:java

-------------------------------------------------------------------------------

Using the CLI
-------------

When you run App.java, you'll get an interactive prompt:

Commands available:

- add             : Add a new task (will prompt for all info)
- list            : List all tasks
- listbycategory  : List tasks for a specific category ID
- listbystatus    : List tasks by status (PENDING, IN_PROGRESS, COMPLETED)
- delete          : Delete a task by ID
- help            : Show list of commands
- exit            : Quit application

-------------------------------------------------------------------------------

Testing
-------

This project includes a fully automated TestNG test suite (see /src/test/java).

**To test everything:**

In terminal:
mvn test

Or in Eclipse/IDEA:
- Right-click a test class or package → Run As → TestNG Test

All service logic, validation, exceptions, and edge cases are covered in tests:
- TaskServiceTest
- CategoryServiceTest
- ReportServiceTest
- TaskRepositoryTest
- DateValidatorTest

-------------------------------------------------------------------------------

Design and Implementation Details
---------------------------------

- **Models:** Task, Category, Priority, Status enums.
- **Services:** Business logic, validation, filtering, reporting, category management.
- **Repositories:** In-memory data storage with thread-safe operations.
- **Custom Exceptions:** Robust error handling for all failures and business rule violations.
- **Util:** Date validation and business rule validation utilities.
- **Test Suite:** High coverage for both positive and negative logic; exception, validation, and data-driven tests.

-------------------------------------------------------------------------------

Extending The Project
---------------------

You can easily add:
- Task updating (edit fields)
- Marking tasks complete
- Sorting or advanced filters
- Richer category/priority management
- File/database persistence
- Advanced reporting/notification features

-------------------------------------------------------------------------------

Assignment/Presentation Tips
----------------------------

- Demonstrate CLI for task creation, listing, and deletion.
- Show test suite green (all-pass) in IDE or terminal for verification.
- Be ready to discuss: validation, exception classes, test design, and edge case coverage.
- Point reviewers to this README for setup, build, and run info.

-------------------------------------------------------------------------------

Questions or Troubleshooting
---------------------------
- If "mvn" not found: add Maven bin folder to your system PATH.
- If Java errors: reinstall JDK and set JAVA_HOME/PATH.
- For package/import errors: verify folder/package structure matches this README.

-------------------------------------------------------------------------------

