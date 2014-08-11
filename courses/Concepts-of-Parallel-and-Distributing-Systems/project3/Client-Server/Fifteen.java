import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException;


/*
*  Class Fifteen is the client main program for the game of Fifteen. The
*  command line arguments specify the host and port to which the server is
*  listening for connections.
*
*   Usage: java Fifteen <playername> <host> <post>
*
*  @author  Kristin Parker
*  @version 14-May-2014
*/
public class Fifteen
{
    /*
    *  Main program.
    */
    public static void main(String[] args) throws Exception
    {
       if (args.length != 3) usage();

       String playername = args[0];
       String host = args[1];
       int port = Integer.parseInt (args[2]);

       Socket socket = new Socket();
       socket.connect (new InetSocketAddress (host,port));
       //System.out.println("Connected to " + socket.toString());

       //inserts the connection to View and Model
       FifteenView view = new FifteenView(playername);
       //System.out.println(view.toString());

       ModelProxy proxy = new ModelProxy( socket );
       view.setViewListener( proxy );
       proxy.setModelListener( view );
    }

    /*
    * Print a usage message and exit.
    */
    private static void usage(){
       System.err.println("Usage: java Fifteen <playername> <host> <port>");
       System.exit(1);
    }
}
