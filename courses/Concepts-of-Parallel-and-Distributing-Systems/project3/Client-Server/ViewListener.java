import java.io.IOException;

public interface ViewListener
{
   public void join( String n ) throws IOException;

   public void digit( int d ) throws IOException;

   public void newgame() throws IOException;

   public void quit() throws IOException;
}
