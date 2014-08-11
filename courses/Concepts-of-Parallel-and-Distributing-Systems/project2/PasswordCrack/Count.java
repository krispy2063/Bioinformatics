/*
*  Count.java
*
*  Program will store the number of user's password
*
*  @author: Kristin K. Parker
*  @version 07-April-2014
*  Course: Concepts of Parallel and Distributed System
*  Assignment: Project 2
*/



public class Count{

        private int numPwdFound = 0;

        public Count( ){}
        public void addCount(){
                numPwdFound++;
        }

        /*
        *   getNum - return numPwdFound
        *   @return numPwdFound int of numPwdFound
        */
        public int getNum(){
                return numPwdFound;
        }
}
