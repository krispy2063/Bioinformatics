/**
 * ScheduleMaker.java
 *
 * $Id$
 *
 * $Log$
 *
 */
import java.util.Scanner;

/**
 * Interactive schedule maker.  Loads a list of courses and gives
 * the user the option to add them to their schedule if they do not
 * create a conflict, and to print their schedule.
 *
 * @author zjb
 */
public class ScheduleMaker {

    /**
     * Main method.
     *
     * @param args Expects one command-line argument: the name of the file containing the courses.
     */
    public static void main(String[] args) {

        CourseList cl = new CourseList(args[0]);
        boolean done = false;
        Schedule sched = new Schedule();

        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to CS3 Schedule Maker!");
        while (!done) {
            System.out.println("\nChoices are");
            System.out.println("1. Add a course to your schedule");
            System.out.println("2. Print your schedule");
            System.out.println("3. Quit");
            System.out.print("What would you like to do? ");
            int choice = in.nextInt();
            System.out.println();
            if (choice == 1) {
                System.out.println("Here are the courses:");
                System.out.println(cl);
                System.out.print("What course would you like to add? ");
                int course = in.nextInt();
                if (sched.contains(cl.getCourse(course)))
                    System.out.println("That course is already on your schedule.");
                else if (sched.add(cl.getCourse(course)))
                    System.out.println("Course successfully added.");
                else
                    System.out.println("Sorry, course could not be added to your schedule.");
            } else if (choice == 2) {
                sched.prettyPrint();
            } else if (choice == 3) {
                done = true;
            } else {
                System.out.println("Not a valid option, please try again.");
            }
        }
        System.out.println("Thanks for using Schedule Maker.");
    }
}
