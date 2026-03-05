# Spike Chatbot User Guide

This is a chatbot that allows you to track your tasks.
Specifically we can track todos, deadlines and events!
// Product intro goes here

## Features

### 1. Adding a Todo: 'todo'
Adds a standard task with no specific time attached to it. 
* **Format:** 'todo <description>'
* **Example:** 'todo read book'

### 2. Adding a Deadline: 'deadline'
Adds a task that needs to be done before a specific date/time.
* **Format:** 'deadline <description> /by <time>'
* **Example:** 'deadline return book /by Sunday'

### 3. Adding an Event: 'event'
Adds a task that starts and ends at specific times/dates.
* **Format:** 'event <description> /from <start_time> /to <end_time>'
* **Example:** 'event project meeting /from 2pm /to 4pm'

### 4. Listing all tasks: 'list'
Lists all tasks currently saved.
* **Format:** 'list'

### 5. Marking a task as done: 'mark'
Marks a specific task as completed.
* **Format:** 'mark <task_number>'
* **Example:** 'mark 2' (Marks 2nd task in the list as done)

### 6. Unmarking a task: 'unmark'
Marks a specific task as not completed.
* **Format:** 'unmark <task_number>'
* **Example:** 'unmark 3' (Marks 3rd task in the list as done)

### 7. Deleting a task: 'delete'
Deletes a specific task.
* **Format:** 'delete <task_number>'
* **Example:** 'delete 2' (Deletes 2nd task in the list)

### 8. Finding a task: 'find'
Searches for a task with the specified keyword.
* **Format:** 'find <keyword>'
* **Example:** 'find book'

### 9. Printing help instructions: 'help'
Prints out available commands with their format and purpose.
* **Format:** 'help'

### 10. Exiting application: 'bye'
Saves task and exits application.
* **Format:** 'bye'
