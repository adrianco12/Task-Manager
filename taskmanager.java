// main class
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;



// class for main
public class taskmanager
{
   public static void main(String args[]) 
   {
      // initialize variables
      Task task;
      int input;
      
      // creating a new PatientQueue object
      TaskQueue queue = new TaskQueue();
      // creating a new categories list
      ArrayList<String> categories = new ArrayList<String>();

      // create a scanner object for input from user
      Scanner in = new Scanner(System.in);
      
      // Display menu to the user
      while (true) {
         // prompt user to choose enqueue or dequeue
         System.out.println("\nMenu: \n 1. Create a Category \n 2. Add a new task  \n 3. Retrieve tasks in order of priority \n 4. Complete a Task \n 5. Edit a Task \n 6. Exit");
         input = in.nextInt();

         // data validation
         while (input < 1 || input > 6) {
            System.out.println("\nMenu: \n 1. Create a Category \n 2. Add a new task  \n 3. Retrieve tasks in order of priority \n 4. Complete a Task \n 5. Edit a Task \n 6. Exit");
            input = in.nextInt();
         }
         
         // decision structure to determine user's decision
         if (input == 1) {
            addCategory(categories);
         }
         else if (input == 2) {
            addTask(queue, categories);
         }
         else if (input == 3) {
            System.out.println ("Retrieving tasks in order of priority... \n");
            queue.displayQueue();
         }
         else if (input == 4) {
            System.out.println ("Removing the first task based on priority... \n");
            if (queue.getSize() != 0) {
               Task removedTask = queue.popPriority();
               System.out.println ("Removed " + removedTask.getName());
            }
            else {
               System.out.println ("Queue is empty!\n");
            }

         }
         else if (input == 5) {
            editTask(queue, categories);
         }
         else {
            System.out.println ("Bye!");
            // exit while loop and end program
            categories.clear();
            break;
         }      
      } // end while loop
   } // end main




   /* Function to create a category for each task */
   public static void addCategory(ArrayList<String> categories) {
      while (true) {
         Scanner in = new Scanner(System.in);

         System.out.println("Here are the categories you have made: ");
         for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
         }

         System.out.println("Create a name for the category or 'exit'");
         String category = in.next();
         if (category.equals("exit")) {
            break;
         }
         categories.add(category);
      }
   }






   /* Function to add a task */
   public static void addTask(TaskQueue queue, ArrayList<String> categories) {
      // declare variables to be passed to constructor
      String name = "";
      String description = "";
      LocalDate deadline = null;
      float estimatedTime = 0;
      int priority = 0;

      // create a scanner object for input from user
      Scanner in = new Scanner(System.in);

      while (true)
      {
         // get data to add to Task object
         System.out.println("Enter the name of the task or 'exit': ");
         name = in.nextLine();
         // break loop with sentinel variable
         if (name.equals("exit")) {
            break;
         }

         System.out.println("Enter the description of the task: ");
         description = in.nextLine();

         // data validation for deadline date
         deadline = deadline(deadline);

         // data validation for estimated time to complete
         estimatedTime = -1;  // Reset estimatedTime
         while (estimatedTime <= 0){ // will continue to run if user puts invalid data
            System.out.println("Enter the estimated time of the task in hours: ");
            String strEstimatedTime = in.nextLine();
            estimatedTime = validateAndParseFloat(strEstimatedTime);
         }

         // data validation for priority
         priority = -1; // reset priority
         while (priority <= 0) {
            System.out.println("Enter the priority (integer value): ");
            String strPriority = in.nextLine();
            priority = validateAndParseInt(strPriority);
         }

         // add categories for task object
         System.out.println("Here are the categories you have made: ");
         for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
         }
         int intCategory = -1;
         while (intCategory < 0) {
            System.out.println("Choose the category you would like to add (choose the number)");
            String strCategory = in.nextLine();
            intCategory = validateAndParseInt(strCategory) - 1;
         }
         String category = categories.get(intCategory);

         // pass data to an instance of Patient class
         Task newTask = new Task(name, description, deadline, estimatedTime, priority, category);

         // queue data using the enqueue method in the PatientQueue class
         queue.enqueue(newTask);
      }
   } // end add task function


   // modify deadline function
   public static LocalDate deadline (LocalDate deadline) {
      Scanner in = new Scanner(System.in);

      deadline = null;  // Reset deadline
      while (deadline == null){ // will continue to run if the user does not put the correct format
         System.out.println("Enter the deadline (in format of MM/dd/yyyy): ");
         String strDeadline = in.nextLine();
         deadline = validateAndParseDate(strDeadline);
      }
      return deadline;
   }



   /* Functions that will edit a task */
   // function that determines which task the user wants to edit and what field they would like to edit
   public static void editTask (TaskQueue queue, ArrayList<String> categories)
   {
      Scanner in = new Scanner(System.in);

      // display the list of tasks to the user
      System.out.println ("Here are a list of your tasks: ");
      queue.displayQueue();

      // validate data and get input from the user
      int intTask = -1;
      while (intTask < 0) { // Ensure intTask starts from a valid index
         System.out.println("\nChoose the task you would like to edit (choose the number): ");
         String indexTask = in.nextLine();

         intTask = validateAndParseInt(indexTask) - 1; // Convert user input to 0-based index

         // Check if intTask is out of bounds
         if (intTask < 0 || intTask >= queue.getSize()) {
            System.out.println("Invalid task number. Please choose a valid task.");
            intTask = -1; // Reset to prompt again
         }
      }

      // display the task to the user and determine which field they would like to edit
      System.out.println ("\nHere is the task you have chosen: ");
      Task editTask = queue.getTask(intTask);
      editTask.displayTask();

      // determine which field user would like to edit
      while (true) {
         int editField = -1;

         while (editField < 0 || editField > 5) {
            System.out.println ("\nChoose the part you would like to edit (choose the number) or 0 to exit: ");
            String strEditField = in.nextLine();
            editField = validateAndParseInt(strEditField);
         }
         if (editField == 0){
            break;
         }
         else {
            editTaskFields(editField, editTask, queue, categories);
         }
      }
   }


   // function to the edit task fields
   public static void editTaskFields(int editField, Task editTask, TaskQueue queue, ArrayList<String> categories) {

      Scanner in = new Scanner(System.in);

      if (editField == 1) {
         System.out.println("Enter the name of the task or 'exit': ");
         String name = in.nextLine();
         editTask.setName(name);
      }
      else if (editField == 2) {
         System.out.println("Enter the description of the task: ");
         String description = in.nextLine();
         editTask.setDescription(description);
      }
      else if (editField == 3) {
         // data validation for deadline date
         LocalDate deadline = null;  // Reset deadline
         while (deadline == null){ // will continue to run if the user does not put the correct format
            System.out.println("Enter the deadline (in format of MM/dd/yyyy): ");
            String strDeadline = in.nextLine();
            deadline = validateAndParseDate(strDeadline);
         }
         editTask.setDeadline(deadline);
      }
      else if (editField == 4) {
         // data validation for estimated time to complete
         float estimatedTime = -1;  // Reset estimatedTime
         while (estimatedTime <= 0){ // will continue to run if user puts invalid data
            System.out.println("Enter the estimated time of the task in hours: ");
            String strEstimatedTime = in.nextLine();
            estimatedTime = validateAndParseFloat(strEstimatedTime);
         }
         editTask.setEstimatedTime(estimatedTime);
      }
      else if (editField == 5){
         // data validation for priority
         int priority = -1; // reset priority
         while (priority <= 0) {
            System.out.println("Enter the priority (integer value): ");
            String strPriority = in.nextLine();
            priority = validateAndParseInt(strPriority);
         }
         editTask.setPriority(priority);
         // sort the heap again if priority is changed
         queue.sortHeap();
      }
      else {
         // add categories for task object
         System.out.println("Here are the categories you have made: ");
         for (int i = 0; i < categories.size(); i++) {
            System.out.println(i + ". " + categories.get(i));
         }
         int intCategory = -1;
         while (intCategory <= 0) {
            System.out.println("Choose the category you would like to add (choose the number)");
            String strCategory = in.nextLine();
            intCategory = validateAndParseInt(strCategory);
         }
         String category = categories.get(intCategory);
         editTask.setCategory(category);
      }
   }






   /* Data validation functions */

   // function that validates local date string
   public static LocalDate validateAndParseDate(String dateString) {
      try {
         // Create a formatter for dd/MM/yyyy with Locale to ensure it parses correctly
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
         // Parse the date string
         return LocalDate.parse(dateString.trim(), formatter);
      }
      catch (DateTimeParseException e) {
         // Handle the exception if the string is not a valid date
         System.out.println("Error parsing date: " + e.getMessage());
         return null;  // Return null if the string is invalid
      }
   }

   // function that validates variables of type float
   public static float validateAndParseFloat(String floatString) {
      try {
         return Float.parseFloat(floatString);
      }
      catch (NumberFormatException e)
      {
         System.out.println("Error parsing float: " + e.getMessage());
         return -1;
      }
   }

   // function that validates variables of type int
   public static int validateAndParseInt(String intString)
   {
      try {
         return Integer.parseInt(intString);
      }
      catch (NumberFormatException e) {
         System.out.println("Error parsing int: " + e.getMessage());
         return -1;
      }
   }


} // end class
