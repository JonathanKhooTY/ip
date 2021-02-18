import myexceptions.InvalidCommandException;
import myexceptions.InvalidDateException;
import myexceptions.TodoException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {



    static ArrayList<Task> tasks = new ArrayList<Task>();
    static int taskPosition = 0;
    static String description;
    static String by;
    static int indexOfBy;


    private static void writeToFile(String filePath, ArrayList<Task> tasks) throws IOException{
        FileWriter fw = new FileWriter(filePath);

        for(Task t:tasks) {
            fw.write( t.getStatusIcon() + " " + t.getDescription() + "\n");

        }
        fw.close();
    }

    private static void restoreFileContents(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            System.out.println(s.nextLine());
        }
    }

    public static void storeTask(Task t) throws TodoException {
        if (!t.description.isEmpty()){
            tasks.add(t);
            System.out.println("Got it! I've added this task!");
            //System.out.println(t.description);
            System.out.println(t.getStatusIcon() + " " + t.getDescription());
            System.out.println("Now you have " + tasks.size() + " tasks!");

        }
        else {
            throw new TodoException();
        }

    }

    public static void markAsDone(int taskIndex){
        System.out.println("Nice! I've marked this task as done: ");
        tasks.get(taskIndex - 1).isDone = true;
        listArray(tasks);
    }

    public static void listArray(ArrayList<Task> tasks){
        int textNumber = 1;
        for(Task t:tasks){
            System.out.println(textNumber + "." + t.getStatusIcon() + " " + t.getDescription());
            textNumber++;
        }

    }

    public static void main(String[] args) throws InvalidCommandException, InvalidDateException, FileNotFoundException {
        System.out.println("Hello! I'm 343 Guilty Spark! You may call me Spark!");
        System.out.println("What can I do for you?");
        String text = "hi";

        try{
            restoreFileContents("Duke.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        while(!text.equalsIgnoreCase("Bye")){
            Scanner in = new Scanner(System.in);
            text = in.nextLine();
            //Task t = new Task(text);
            String[] arr = text.split(" "); //split command input into words
            String command = arr[0];

            switch (command){
            case "deadline":
            case "Deadline":
            case "DEADLINE":
                try {
                int indexOfBy = text.indexOf('/');
                description = text.substring(9,indexOfBy-1);
                by = text.substring(indexOfBy + 1);
                Deadline deadlineTask = new Deadline(description,by);

                    storeTask(deadlineTask);
                } catch (TodoException e) {
                    e.printStackTrace();
                } catch (StringIndexOutOfBoundsException e){
                    System.out.println("Date required!");
                }
                break;
            case "todo":
            case "Todo":
            case "TODO":

                try{
                    description = text.substring(5);
                    Todo todoTask = new Todo(description);
                    storeTask(todoTask);
                    break;
                } catch (TodoException e){
                    System.out.println("Todo cannot be empty");
                    break;
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println("Todo list cannot be empty! Try again!");
                    break;
                }


            case "event":
            case "Event":
            case "EVENT":

                try {
                    if (!text.contains("/")){
                        throw new InvalidDateException();
                    }
                    indexOfBy = text.indexOf('/');

                    String description = text.substring(6,indexOfBy-1);
                    String by = text.substring(indexOfBy + 1);
                    Event eventTask = new Event(description,by);
                    storeTask(eventTask);
                } catch (TodoException e) {
                    System.out.println("Event description cannot be empty! Try again!");
                } catch (InvalidDateException e){
                    System.out.println("Event must have a date! Try again!");
                }
                break;
            case "List":
            case "list":
            case "LIST":
                listArray(tasks);
                break;
            case"Done":
            case "done":
            case "DONE":
                try {
                    Integer taskIndex = Integer.parseInt(arr[1]);
                    markAsDone(taskIndex);
                    break;
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("What do you want to mark as done?");
                    break;
                }
            case "Delete":
            case "delete":
            case "DELETE":
                try {
                    Integer taskIndex = Integer.parseInt(arr[1]);
                    markAsDeleted(taskIndex);
                    break;
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("What do you want to mark as done?");
                    break;
                }



            default:
                System.out.println("Invalid command. Only accepts Todo,List,Event or Deadline!");
            }
            String file2 = "Duke.txt";
            try {
                writeToFile(file2, tasks);
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
        System.out.println("Bye! Hope to see you again soon!");
    }

    private static void markAsDeleted(Integer taskIndex) {

        Task t = tasks.get(taskIndex - 1);
        System.out.println("Noted! I have deleted this task for you: ");
        System.out.println(taskIndex + "." + t.getStatusIcon() + " " + t.getDescription());
        tasks.remove(taskIndex - 1);
        System.out.println("Now you have " + tasks.size() + " tasks in the list!");
    }
}
