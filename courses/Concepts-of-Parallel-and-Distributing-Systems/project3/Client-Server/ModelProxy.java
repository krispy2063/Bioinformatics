import java.io.*;
import java.util.Scanner;
import java.net.Socket;

/*
*  Class ModelProxy provides the network proxy for the model object in the
*  game of Fifteen. The model proxy resides in the client program and
*  communicates with the server program
*
* @author  Kristin Parker
* @version 14-May-2014
*/
public class ModelProxy implements ViewListener {

  //Hidden data members

   private Socket socket;
   private PrintStream out;
   private Scanner in;
   private ModelListener modelListener;

   /*
   *  Construct a new model proxy.
   *
   *  @param socket Socket
   *
   *  @exception IOException
   *       Thrown if an I/O error occurred.
   */
   public ModelProxy( Socket socket) throws IOException{
      this.socket = socket;
      this.out = new PrintStream (this.socket.getOutputStream());
      this.in = new Scanner (this.socket.getInputStream());
   }

   /*
   *  Set the model listener object for this model proxy.
   *  @param modelListener ModelListener
   */
   public synchronized void setModelListener( ModelListener modelListener ){
      this.modelListener = modelListener;
      new ReaderThread().start();
   }
   /*
   *  quit the game
   */
   public void quit( ) throws IOException {
      out.println("quit");
   }

   /*
   *  start the new game
   *
   */
   public void newgame( ) throws IOException{
      out.println("newgame");
   }

   /*
   *  digit i is which a client have clicked.
   *
   *  @param i Integer of button
   */
   public void digit( int i ) throws IOException{
      out.println("digit " + i);
   }

   /*
   *  join to the game
   *
   *  @param str String of player name
   */
   public void join( String str ) {
      out.println( "join " + str );
   }


   /*
   *  Class ReaderThread receives messages from the netword, decodes them,
   *  and invokes the proper methods to process them.
   *
   *  @author  Kristin Parker
   *  @version 14-May-2014
   */
   private class ReaderThread extends Thread{
      public void run(){
          String b;
         try{
             while (in.hasNextLine()){

               b = in.nextLine();
               String[] words = b.split(" ");

               if(words[0].equals("quit")){
                  modelListener.quit();
               }else if(words[0].equals("digits")){
                   String d  = words[1];
                   modelListener.digit(d);
               }else if(words[0].equals("name")){
                   int i = Integer.parseInt(words[1]);
                   String name = words[2];
                  modelListener.name(i,name);
               }else if(words[0].equals("id")){
                   int i = Integer.parseInt(words[1]);
                   modelListener.id(i);
               }else if(words[0].equals("score")){
                  int i = Integer.parseInt(words[1]);
                  int s = Integer.parseInt(words[2]);
                  modelListener.score(i,s);
               }else if (words[0].equals("turn")){
                  int i = Integer.parseInt(words[1]);
                  modelListener.turn(i);
               }else if(words[0].equals("win")){
                  int i = Integer.parseInt(words[1]);
                  modelListener.win(i);
               }
            }
         } catch(IOException exc){
         }
         finally{
            try{
               socket.close();
            }catch( IOException exc ){
            }
         }
      }
   }
}
