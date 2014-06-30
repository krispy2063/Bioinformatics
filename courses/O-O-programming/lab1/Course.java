/**
*Course.java
*
*Version:
*       $Id$
*
*Reversion:
*       $Log$
*/



/**
*@author: Kristin Parker (kkp5232)
*
*Represents a single course (name, days, time). Aussumes that all courses start and end on the hour.
*/

public class Course {

        private String n;
        private java.util.ArrayList<Boolean> days;
        private int start;
        private int end;

        /**
        *Field Summary: dayString - a String containing all possible days as characters, "MTWRF"
        */
        public static final String dayString = "MTWRF";


        /**
        *Constructor: Course - Constructor
        *
        *@param n - name of the course
        *@param days - list of days that the course is held on
        *@param start - Starting time (in hour, 24-hour clock)
        *@param end - Ending time (in hours, 24-hour clock)
        */
        public Course( String n, java.util.ArrayList<Boolean> days, int start, int end ) {
                this.n = n;
                this.days = days;
                this.start = start;
                this.end = end;
        }


        /**
        *Constructor: equals - test for equality
        *
        *@overrides: equals in class Object
        *@param other - Object to be tested against
        *@return True if the object passed in is a Course with the same name, days, start and end as this Course
        */
        public boolean equals( Object other ) {

                if ( other instanceof Course ) {

                        Course o = ( Course ) other;
                        return( this.n.equals(o.n) && this.days.equals(o.days) && this.start == o.start && this.end == o.end );

                }

                return false;

        }


        /**
        *Constructor: Test for scheduling conflict
        *
        *@param other - Course to test against
        *@return True if the passed-in Course overlaps in time (on any day) with this Course
        */
        public boolean inConflict( Course other ) {

                if ( other instanceof Course) {

                        Course o = ( Course ) other;
                        int num = this.start;
                        while ( num <= this.end) {

                                if ( num == o.start || num == o.end) {

                                        return true;
                                }
                                num++;
                        }
                }

                return false;

        }


        /**
        *Constructor: String representation of the course: Name: days at start-end
        *
        *@overrides: toString in class Object
        *@return String representation
        */
        public String toString() {

                String day = " ";

                for ( int i = 0; i < days.size(); i++ )

                        if ( days.get(i) == true ) {

                                if ( i == 0 ) {
                                        day = day + "M";
                                }else if ( i == 1 ) {
                                        day = day + "T";
                                }else if ( i == 2 ) {
                                        day = day + "W";
                                }else if ( i == 3 ) {
                                        day = day + "R";
                                }else {
                                        day = day + "F";
                                }
                        }

                return n + ": " + day + " at " + start + "-" + end;

        }


        /**
        *Constructor: Returns a string representing the time this course meets on the given day.
        *              if it does meet, String should be in the form: Start-end: Name.
        *
        *@param day - Day of the week, ( 0 = Monday... 4 = Friday etc..)
        *@return String as above or the empty String if the course does not meet on the given day.
        */
        public String inDay( int day ) {

                if ( days.get(day) == true ) {

                        return( start + "-" + end + ": " + n );

                }else {
                        return( " " );
                }

        }
}//end of the Course class

