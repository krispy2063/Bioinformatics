/*
*  userHash.java
*
*  Program will storage the user and hash. Also it can return the data if the data is need.
*
*  @author: Kristin K. Parker
*  @version 07-April-2014
*  Course: Concepts of Parallel and Distributed System
*  Assignment: Project 2
*
*/

public class userHash{

        private String user;
        private String hash;

        /*
        * userHash - as other program created a object. It will add the user and hash
        *
        * @param u String of user
        * @param h String of hash
        */
        public userHash( String u, String h ){

                user = u;
                hash = h;

        }

        /*
        * getUser - return user
        * @return user String
        */
        public String getUser( ){

                return user;

        }

        /*
        * getHash - return hash
        * return hash String
        */
        public String getHash( ){

                return hash;

        }

}
