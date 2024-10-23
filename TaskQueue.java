public class TaskQueue
{
   // initialize fields
   private Task[] queue;
   private int arraySize;
   private int dataSize;
   private int front;
   private int rear;

   // constructor
   public TaskQueue()
   {
      // set initial size of array
      this.arraySize = 20;

      // instantiate our array of elements of type Patient
      queue = new Task[this.arraySize];

      // set our initial data size
      this.dataSize = 0;

      // set our front and rear both to zero because there is nothing in our queue
      this.front = 0;
      this.rear = 0;
   }


   // method to enqueue
   public void enqueue (Task task)
   {
      if(this.dataSize >= this.arraySize) // make sure we have room in the line
      {
         System.out.println("The queue is full. No more tasks can be added.");
      }
      else
      {
         queue[rear] = task; // putting the passed parameter in the rear of our queue
         rear = (rear + 1)%arraySize; // cause us to add 1 and wrap around to the beginning of our array

         // add one to our data
         dataSize++;

         // calling sort priority here
         sortHeap();

         // let user know that a Patient was added to the queue
         System.out.println("Added " + task.getName() + " to the queue. It is at: " + rear);
      }
   }

   // Method to sort the heap
   public void sortHeap() {
      buildMaxHeap();

      for (int i = dataSize - 1; i > 0; i--) {
         // Move current root to the end
         Task temp = queue[0];
         queue[0] = queue[i];
         queue[i] = temp;

         // Call max heapify on the reduced heap
         heapifyDown(0, i);
      }
   }

   // Method to build the max heap
   public void buildMaxHeap() {
      int heapSize = dataSize;
      // Start from the last non-leaf node and heapify downwards
      for (int i = (heapSize / 2) - 1; i >= 0; i--) {
         heapifyDown(i, heapSize);
      }
   }

   // Method to turn the array into a max heap (down-heap process)
   public void heapifyDown(int index, int heapSize) {
      int largest = index;
      int leftChild = 2 * index + 1;
      int rightChild = 2 * index + 2;

      // Check if left child is larger than root
      if (leftChild < heapSize && queue[leftChild].getPriority() > queue[largest].getPriority()) {
         largest = leftChild;
      }

      // Check if right child is larger than the largest so far
      if (rightChild < heapSize && queue[rightChild].getPriority() > queue[largest].getPriority()) {
         largest = rightChild;
      }

      // If largest is not root
      if (largest != index) {
         Task temp = queue[index];
         queue[index] = queue[largest];
         queue[largest] = temp;

         // Recursively heapify the affected sub-tree
         heapifyDown(largest, heapSize);
      }
   }


   // method to dequeue
   public Task popPriority()
   {
      // declare a variable for our current parent location
      int parent = 0;

      // grab our top priority which should be at element 0
      Task topPriority = queue[0];

      // taking our youngest child and setting that as root node
      queue[0] = queue[dataSize - 1];
      dataSize--;

      // heapify the new array
      sortHeap();

      return topPriority;
   } // end method



   // method to display the patients in the queue
   public void displayQueue()
   {
      for (int i = 0; i < dataSize; i++)
      {
         System.out.println(i + 1 + ". Name: " + queue[i].getName() + ", Description: " + queue[i].getDescription() + ", Deadline: " + queue[i].getDeadline().toString() + ", Priority: " + queue[i].getPriority());
      }
   }

   public int getSize() {
      return dataSize;
   }

   public Task getTask(int index) {
      return queue[index];
   }

   public void displayTask(int i) {
      System.out.println("1. Name: " + queue[i].getName() + "\n2. Description: " + queue[i].getDescription() + "\n3. Deadline: " + queue[i].getDeadline().toString() + "\nEstimated Hours to Complete: " + queue[i].getEstimatedTime() + "\n4. Priority: " + queue[i].getPriority());
   }


}