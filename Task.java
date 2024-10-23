import java.time.LocalDate;

// patient class
public class Task
{
   // intialize fields
   private String name;
   private String description;
   private LocalDate deadline;
   private float estimatedTime;
   private int priority; // will be 1-10 (1 is highest priority)
   private String category;
   
   // constructor
   public Task (String name, String description, LocalDate deadline, float estimatedTime, int priority, String category)
   {
      this.name = name;
      this.description = description;
      this.deadline = deadline;
      this.estimatedTime = estimatedTime;
      this.priority = priority;
      this.category = category;
   }
   
   // getter methods
   public String getName ()
   {
      return name;
   }
   public String getDescription ()
   {
      return description;
   }
   public LocalDate getDeadline () { return deadline;}
   public float getEstimatedTime () { return estimatedTime; }
   public int getPriority ()
   {
      return priority;
   }
   public String getCategory () { return category; }

   // setter methods - to be used when editing a task
   // Setter for name
   public void setName(String name) {
      this.name = name;
   }
   // Setter for description
   public void setDescription(String description) {
      this.description = description;
   }
   // Setter for deadline
   public void setDeadline(LocalDate deadline) {
      this.deadline = deadline;
   }
   // Setter for estimatedTime
   public void setEstimatedTime(float estimatedTime) {
      if (estimatedTime > 0) { // Optionally validate that estimatedTime is positive
         this.estimatedTime = estimatedTime;
      } else {
         System.out.println("Estimated time must be positive.");
      }
   }
   // Setter for priority
   public void setPriority(int priority) {
      this.priority = priority;
   }
   // Setter for category
   public void setCategory(String category) {
      this.category = category;
   }

   public void displayTask () {
      System.out.println("1. Name: " + name + "\n2. Description: " + description + "\n3. Deadline: " + deadline.toString() + "\n4. Estimated Hours to Complete: " + String.valueOf(estimatedTime) + "\n5. Priority: " + String.valueOf(priority) + "\n6. Category: " + category);
   }
}