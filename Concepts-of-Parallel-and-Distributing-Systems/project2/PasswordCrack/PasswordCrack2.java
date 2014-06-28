/*
 *  PasswordCrack2.java
 *
 *  Program will change the hash from database to password by using the brute-force attack. Then print the user and password,
 *  total of users, and total of passwords found.
 *
 *  Sometime there will have any match or not.
 *
 *  @author: Kristin K. Parker
 *  @version 07-April-2014
 *  Course: Concepts of Parallel and Distributed System
 *  Assignment: Project 2
 *
 *
 */

import java.lang.Exception;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;


public class PasswordCrack2 extends Task {

        private int pwdFound = 0;

        /*
         * checkFile method will check to make sure the file have something inside.
         *
         * @param file Scanner
         *
         * If there is none, it will print the errors
         *
         */
        public void checkFile( Scanner file ) {
                if(!file.hasNext()){

                        System.err.println("Usage: There is no line in the file");
                        System.exit(0);

                }
        }


        /*
         * generateSHA method will change the bytes to SHA
         *
         * @param data byte[]
         *
         * @return the data (SHA)
         *
         * @exception NoSuchAlgorithmException
         *       Thrown when an algorithm is necessary
         * @exception UnsupportedEncodingException
         *       Thrown if an encoding is not supported
         */
         public byte[] generateSHA256(byte [] data)throws NoSuchAlgorithmException, UnsupportedEncodingException{

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(data);
                data = md.digest();
                return data;
         }

        /*
        * toHash method will change the password to hash
        *
        * @param pwd String of password
        * @return sb.toString() -> string of hash of password
        *
        * @exception NoSuchAlgorithmException
        *       Thrown when an algorithm is necessary
        * @exception UnsupportedEncodingException
        *       Thrown if an encoding is not supported
        */
        public String toHash(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
                byte[] pwdHash;
                 pwdHash = pwd.getBytes("UTF-8");
                 pwdHash = generateSHA256(pwdHash);
                 StringBuilder sb = new StringBuilder();
                 for( byte b: pwdHash ){
                       sb.append(String.format("%02x",b));
                 }
                 return sb.toString();
        }

   /*
    * main method will recieved the one files and open them with call the checkFile method for check if there is any line in the flie.
    * Then each line will be check for user and hash then each hash will be check for 64-character hexadecimal string.
    *
    * Each line will be split then storage into userHash class (user,hash)
    *
    * The parallel run will run through each object (userHash) in the userHashList which
    * will becomes the threads. They have the bytes (data) and call generateSHA256_100000 which will return the
    * data (SHA) for each thread. The data will be return as password and will be stored in the userHash.
    *
    * Print: <user> <password> -> only if the password is found.
    *
    * Print: U users -> where U is the number of users in the password hash database
    *
    *
    * Run through the userHash list to count how much the passwords were found.
    * Print: N passwords found  -> where N is the number of users whose passwords were found.
    *
    * @exception Exception
    *           Thrown if an error occurred
    */
    public void main(String[] args) throws Exception {

                final ArrayList<userHash> userHashList = new ArrayList<userHash>(); //storage the user and hash in the object in the list
                final String[] nl = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
                final HashMap<String, String> pwdHashList = new HashMap<String, String>();

                if( args.length == 1 ){
                        // Open db.N
                        Scanner db = new Scanner(System.in);
                        try {
                                File newDBFile = new File( args[0] );
                                db = new Scanner( newDBFile );
                                checkFile(db);
                        }catch ( IOException e ) {

                                System.out.println( e.getMessage() );
                                System.exit(0);
                        }

                        while( db.hasNext() ){

                                String line = db.nextLine();
                                String[] spl = line.split("\\s+");

                                if( spl.length == 2){
                                        String[] spHash = spl[1].split("");
                                        if ( spHash.length-1 == 64 ){
                                                userHash uh = new userHash(spl[0],spl[1]);
                                                userHashList.add(uh);
                                        }else {
                                                System.out.println("Each line have to have 64-character hexadecimal string for a password hash");
                                                System.exit(0);
                                        }
                                }else {
                                        System.out.println("Each line have to have a user name and password hash");
                                        System.exit(0);
                                }

                        }
                                for(int k = 0; k < nl.length; k++ ){
                                        String pwd1 = nl[k];
                                        String sb1 = toHash(pwd1);
                                        pwdHashList.put(sb1,pwd1);
                                        for( int l = 0; l < nl.length; l++ ){
                                                String pwd2 = pwd1 + nl[l];
                                                String sb2 = toHash(pwd2);
                                                pwdHashList.put(sb2,pwd2);
                                                for(int n = 0; n < nl.length; n++ ){
                                                        String pwd3 = pwd2 + nl[n];
                                                        String sb3 = toHash(pwd3);
                                                        pwdHashList.put(sb3,pwd3);
                                                        for (int j = 0; j < nl.length; j++){
                                                            String pwd4 = pwd3 + nl[j];
                                                            String sb4 = toHash(pwd4);
                                                            pwdHashList.put(sb4,pwd4);
                                                        }
                                                }
                                        }
                                }

                        final Count c = new Count();
                        parallelFor(0,  userHashList.size() - 1) .exec (new Loop()
                        {
                                public void run( int i ){
                                        userHash uh = userHashList.get(i);
                                        String user = uh.getUser();
                                        String hash = uh.getHash();

                                        if( pwdHashList.containsKey(hash) ){
                                                String pwd = pwdHashList.get(hash);
                                                System.out.println(user + " " + pwd);
                                                c.addCount();
                                        }
                                }
                        });

                        System.out.println(userHashList.size() + " users");
                        System.out.println(c.getNum() + " passwords found" );

                }else {

                        System.err.println("Usage: Input errors, please type like this: java PasswordCrack <db>");
                        System.exit(0);

                }
        }
}

