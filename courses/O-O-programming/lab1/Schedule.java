/**
*Schedule.java
*
*Verson:
*       $Id$
*
*Revision:
*       $Log$
*/



/**
*@author: Kristin Parker (kkp5232)
*
*Class that holds a number of courses that do not conflict and can print out a simple day-by-day schedule.
*/

public class Schedule {
        //The collection of courses
        private java.util.ArrayList<Course> courses;

        /**
        *Constructor: Schedule - creates/initializes any necessary data structures
        */
        public Schedule(  ) {

                courses = new java.util.ArrayList<Course>();

        }


        /**
        *Method: contains - Tests whether a given Course is currently on the schedule
        *
        */
        public boolean contains( Course c ) {

                for ( int i = 0; i < courses.size(); i++) {

                        if ( c.equals(courses.get(i)) ) {
                                return true;

                        }
                }
                return false;
        }


        /**
        *Method: Adds the given course to the schedule only if it is not in conflict with courses currently on the schedule
        *
        *@param c - Course to attempt to add
        *@return whether the course was successfully added
        */
        public boolean add( Course c ) {

                if (courses.size() == 0 ) {
                        courses.add(c);
                        return true;
                }
                for( int i = 0; i < courses.size(); i++ ){

                        if (courses.get(i).inConflict(c) == false) {

                                courses.add(c);
                                return true;

                        }
                }
                return false;

        }


        /**
        *Method: Simple string representation: "Schedule with n courses" where n is the number of courses on the schedule
        *
        *@override: toString in class object
        *@return String as above
        */
        public String toString() {

                int num = 0;

                for( int i = 0; i < courses.size(); i++ ) {

                        num ++;

                }

                return "Schedule with " + num + " courses";

        }


        /**
        *Method: Prints a day-by-day schedule( using relevant functions from the Course class).
        */
        public void prettyPrint() {
                System.out.println("-------Monday--------");

                for ( int i = 0; i < courses.size(); i++ ) {

                        System.out.println(courses.get(i).inDay(0));

                }

                System.out.println("-------Tuesday-------");

                for ( int i = 0; i < courses.size(); i++ ) {

                        System.out.println(courses.get(i).inDay(1));

                }

                System.out.println("-------Wednesday-------");

                for ( int i = 0; i < courses.size(); i++ ) {

                        System.out.println(courses.get(i).inDay(2));

                }

                System.out.println("-------Thursday-------");

                for ( int i = 0; i < courses.size(); i++ ) {

                        System.out.println(courses.get(i).inDay(3));

                }

                System.out.println("-------Friday-------");

                for ( int i = 0; i < courses.size(); i++ ) {

                        System.out.println(courses.get(i).inDay(4));

                }

        }

}q//end of the Schedule class
