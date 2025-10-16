Personal Task Manager (CLI) 📋
A robust, Java-based command-line interface (CLI) application for managing personal tasks. Designed with clean architecture, demonstrating strong OOP principles, and fully covered by automated tests.

💡 Key Highlights

* 100% Java-based implementation — zero reliance on external databases or complex UI.

* Clean Modular Architecture — high maintainability and scalability through strict separation of concerns (Service, Repository, Model).

* Robustness — features extensive input validation and custom exception handling.

* Quality Assured — fully automated TestNG test suite covering core logic (service, repository, utilities).

* Lightweight CLI design, perfect for learning and demonstrating intermediate Java skills.

🚀 Features

* The CLI provides comprehensive functionality for efficient task management:

* Task Creation: Add new tasks with detailed metadata:

* Category

* Priority

* Status

* Date

 ** Flexible Listing :-

* List all tasks.

* Filter tasks by Category ID.

* Filter tasks by Status (PENDING, IN_PROGRESS, COMPLETED).

** Task Management:-

* Delete tasks by their unique ID.

* User Experience: Interactive command-line prompt for smooth, text-based interaction.

⚙️ Technologies Used
Technology / Tool	Purpose / Description

🟠 Java (JDK 8+)	Core programming language.

🔵 Maven	Build automation, dependency management, and project execution.

🧪 TestNG	Testing framework for unit and integration testing.

🧩 Collections Framework	Java structures (List, Map) for efficient in-memory task storage.

💡 Clean Architecture	Layered structure for modularity and testability.

🧾 Exception Handling	Custom exceptions for robust error and validation handling.

📁 Project Structure

The project employs a modular and layered architecture to achieve a clear separation of concerns:

Folder / File	 Description
pom.xml	       Maven build file defining dependencies, plugins, and configurations.

App.java     	 The main entry point for the CLI. Handles user input and service invocation.

model/	       Contains core data classes (Task) and enumerations (Category, Priority, Status).

repository/   	 Data Access Layer. TaskRepository handles CRUD operations using an in-memory structure.

service/	       Business Logic Layer. Implements all core feature logic (e.g., Task operations, reporting).

util/         	 Utility Helpers. Contains classes like DateValidator for input validation.

exception/	    Custom exceptions for specific runtime errors (e.g., TaskNotFoundException).

test/	          Automated TestNG test cases for all key components.

▶️ Usage & Installation
Prerequisites
Java (JDK 8+)

Maven

Build and Run
Execute these commands from the root directory of the project:

Bash

# 1. Build project (clean, compile, package)
mvn clean install

# 2. Run the application's main class
mvn exec:java

# 3. Run the automated test suite
mvn test

CLI Commands
When running the application, you will be presented with an interactive prompt. The following commands are available:

add: Add a new task (prompts interactively for all metadata).

list: List all tasks.

listbycategory: List tasks filtered by a specific category ID.

listbystatus: List tasks by status (PENDING, IN_PROGRESS, COMPLETED).

delete: Delete a task by its ID.

help: Show the list of available commands.

exit: Quit the application.

