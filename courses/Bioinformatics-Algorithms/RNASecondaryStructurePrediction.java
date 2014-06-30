/*
  Date: November 30th, 2013
  
  RNASecondaryStructurePrediction:
      Predict the RNA's secondary structure by implement RNA folding algorithms.
      
  This program will requests for the sequence from a FASTA file.
  
  Created by: Kristin Parker

*/

import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;


public class RNASecondaryStructurePrediction{

    ArrayList seq = new ArrayList();//store the sequence
    int[][] score;
    
    int sco = 0; //scoring the most common: C-G: 3  A-U: 2  A: 0 U: 0 C: 0 G:0


   //intization the new object
   public RNASecondaryStructurePrediction( String fa )throws IOException{
      FASTAReader( fa );
      for(int i = 0; i < seq.size(); i++){
         fillStage( (String) seq.get(i) );
         
         System.out.println("");
         
         trackback( (String) seq.get(i) );
      }
   }

   public void trackback( String s ){
      int size = s.length();
            
      String lineBeg = "";
      String lineEnd = "";
      String notationLineBeg = "";
      String notationLineEnd = "";
      
      for( int row = 0; row < size; ){
         for( int col = size-1; col > 0; ){
         
            int cur = score[row][col];
            int west = score[row][col-1];
            int dia = score[row+1][col-1];
            int south = score[row+1][col];
            
            if( west == 0 && south == 0 && dia == 0 ){
               lineBeg = lineBeg + s.charAt(row);
               lineEnd = s.charAt(col) + lineEnd;
               notationLineEnd = ")" + notationLineEnd;
               notationLineBeg = notationLineBeg + "(";
               row = size;
               col = 0;
            }else if( west == south ){
               lineBeg = lineBeg + s.charAt(row);
               lineEnd = s.charAt(col) + lineEnd;
               notationLineEnd = ")" + notationLineEnd;
               notationLineBeg = notationLineBeg + "(";
               row++;
               col--;
               
               if( s.charAt(row) == 'C' || s.charAt(row) == 'G' ){
                  sco += 3;
               }else if ( s.charAt(row) == 'A' || s.charAt(row) == 'U' ){
                  sco += 2;
               }
               
            }else if( south > west && south > dia ){
               lineBeg = lineBeg + s.charAt(row);
               notationLineBeg = notationLineBeg + ".";
               row++;
            }else if( dia > west && dia > south ){
               lineBeg = lineBeg + s.charAt(row);
               lineEnd = s.charAt(col) + lineEnd;
               notationLineEnd = ")" + notationLineEnd;
               notationLineBeg = notationLineBeg + "(";
               row++;
               col--;
               
               if( s.charAt(row) == 'C' || s.charAt(row) == 'G' ){
                  sco += 3;
               }else if ( s.charAt(row) == 'A' || s.charAt(row) == 'U' ){
                  sco += 2;
               }
               
            }else if( west > south && west > dia ){
               lineEnd = s.charAt(col) + lineEnd;
               notationLineEnd =  "." + notationLineEnd;
               col--;
            }
         }
      }
      System.out.println(lineBeg + lineEnd);
      System.out.println(notationLineBeg + notationLineEnd);
      System.out.println("");
      System.out.println(sco);
   }

   public void fillStage( String s ){
      int size = s.length();
      //System.out.println(size);
      
      score = new int[size][size];
      
      //initialisation
      for( int r = 0; r < size; r++ ){
         for( int c = 0; c < size; c++ ){
            if( r == 0 && c == 0){
               score[0][0] = 0;
            }else if( r == c ){
               score[r][c-1] = 0;
               score[r][c] = 0;
            }
         }
      }
      
      //int r = 0;
      //int c = 1;
      
      int pos = 1;
      
      for( int r = 0; r < size; r ++ ){
         int c = r+pos;
         for( int i = 0; c < size && i == 0; i++ ){
                  
         int west = score[r][c-1];
         int dia = score[r+1][c-1];
         int south = score[r+1][c];
         
         int highScore = 0;
         
         int finalScore = 0;
         
         if( west > 0 && west > highScore ){
            highScore = west;
         }
         if( dia != 0 && dia > highScore ){
            highScore = dia;
         }
         if( south != 0 && south > highScore ){
            highScore = south;
         }
         
         if(  ( (s.charAt(r))== 'A' && (s.charAt(c)) == 'U' ) || ((s.charAt(r))== 'U' && (s.charAt(c)) == 'A') || ((s.charAt(r))== 'G' && (s.charAt(c)) == 'C') || ((s.charAt(r))== 'C' && (s.charAt(c)) == 'G') ){            
            score[r][c] =  highScore + 1;            
         }else{
            score[r][c] = highScore;         
         }

         }
         
         if( c == size ){
            r = -1;
            pos++;
            c = pos;
         }
      
      }
      
      /*
      System.out.println(" " + s);
      String line = "";
      for(int i = 0; i <size; i++){
         line = "" + s.charAt(i);
         for(int j = 0; j < size; j++){
            line += score[i][j];
         }
         System.out.println(line);
         line= "";
      }
      */
   }

   //Read the sequence in the FASTA file
   public void FASTAReader( String fa ) throws IOException, FileNotFoundException{
      File file = new File(fa);
      
      if(!file.isFile()){
         System.out.println(file.toString() + " is not a valid file.");
         System.exit(0);
      }
      if(!file.canRead()){
         System.out.println(file.toString() + " is not readable.");
         System.exit(0);
      }
      
      FileReader in;
      try{
         in = new FileReader(file);
      }catch ( FileNotFoundException fe ){
         throw fe;
      }
      BufferedReader bIn = new BufferedReader(in);
      
      String cur = bIn.readLine();
      StringBuffer buf;
      
      int key = 1;
      String name = "";
      String s = "";

      while( cur != null ){
         if(cur == null || cur.length() == 0 ){
            cur = bIn.readLine();
            break;
         }else if( cur.startsWith(">")){
            cur = bIn.readLine();         
            s = cur;
            check(s);
            seq.add(s);
         }else {
            System.out.println("Input of a FASTA file is not the right file");
            System.exit(0);
         }
         cur = bIn.readLine();       
      }     
   }
   
   //Check the sequence to make sure there is nothing else than A,U,C, and G or else it will output error and end the program
   public void check( String s1 ){
      String[] s = s1.split("");
      for(int i = 1; i < s.length; i++){
         if( !(s[i].equals("A") || s[i].equals("U") || s[i].equals("C") || s[i].equals("G")) ){
            System.out.println("Error: You have " + s[i] + " in your sequence.. it have to be A, U, C, or G in your sequence");
            System.exit(0);
         }
      }
   }



   public static void main(String[] args) throws IOException, FileNotFoundException{
      Scanner input = new Scanner( System.in );
      
      System.out.println("Input a FASTA file: ");
      String fa = input.next();   
      
      RNASecondaryStructurePrediction RSSP = new RNASecondaryStructurePrediction( fa );
   }
}
