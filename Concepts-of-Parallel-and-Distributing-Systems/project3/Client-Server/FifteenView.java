import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.*;
/**
 * Class FifteenView provides the user interface for the Fifteen network game.
 *
 * @author  Alan Kaminsky
 * @author  Kristin Parker
 * @version 14-May-2014
 */
public class FifteenView extends JFrame implements ModelListener {

// Hidden data members

    private static final int GAP = 10;
    private static final int COLS = 12;
    private ViewListener viewListener;

    //my data
    int myID;
    //String myName;
    int scoreMy;

    String theirName;

    private HashMap<Integer,Integer> player = new HashMap<Integer,Integer>(); //hold id and score
    private HashMap<Integer,String> user = new HashMap<Integer,String>(); //hold id and name

        /**
         * Class DigitButton provides a button labeled with a digit.
         */
        private class DigitButton
                extends JButton
                {
                private int digit;
                private boolean enabled = true;
                private boolean available = true;

                /**
                 * Construct a new digit button.
                 *
                 * @param  digit  Digit for the button label.
                 */
                public DigitButton
                        (int digit)
                        {
                        super ("" + digit);
                        this.digit = digit;
                        addActionListener (new ActionListener()
                                {
                                public void actionPerformed (ActionEvent e)
                                        {
                                        onDigitButton (DigitButton.this.digit);
                                        }
                                });
                        }

                /**
                 * Make this digit button available or unavailable. When available, the
                 * button displays its digit. When not available, the button is blank.
                 *
                 * @param  available  True if available, false if not.
                 */
                public void available
                        (boolean available)
                        {
                        this.available = available;
                        setText (available ? "" + digit : " ");
                        updateButton();
                        }

                /**
                 * Enable or disable this digit button. When enabled and available,
                 * clicking the button performs the appropriate action. Otherwise,
                 * clicking the button has no effect.
                 *
                 * @param  enabled  True if enabled, false if not.
                 */
                public void setEnabled
                        (boolean enabled)
                        {
                        this.enabled = enabled;
                        updateButton();
                        }

                /**
                 * Update this digit button's label and enabled state.
                 */
                private void updateButton()
                        {
                        super.setEnabled (available && enabled);
                        }
                }

        /**
         * User interface widgets.
         */
        private String myName;
        private int myScore;
        private DigitButton[] digitButton;
        private JTextField myScoreField;
        private JTextField theirScoreField;
        private JTextField winnerField;
        private JButton newGameButton;

        /**
         * Construct a new FifteenView object.
         *
         * @param  myName  Player's name.
         */
        public FifteenView
                (String myName)
                {
                super ("Fifteen -- " + myName);
                this.myName = myName;
                JPanel panel = new JPanel();
                add (panel);
                panel.setLayout (new BoxLayout (panel, BoxLayout.X_AXIS));
                panel.setBorder (BorderFactory.createEmptyBorder (GAP, GAP, GAP, GAP));
                JPanel panel_a = new JPanel();
                panel.add (panel_a);
                panel_a.setLayout (new BoxLayout (panel_a, BoxLayout.Y_AXIS));
                digitButton = new DigitButton [9];
                for (int i = 0; i < 9; ++ i)
                        {
                        panel_a.add (digitButton[i] = new DigitButton (i + 1));
                        digitButton[i].setAlignmentX (0.5f);
                        digitButton[i].setEnabled (false);
                        digitButton[i].setMinimumSize (digitButton[i].getPreferredSize());
                        digitButton[i].setMaximumSize (digitButton[i].getPreferredSize());
                        digitButton[i].setSize (digitButton[i].getPreferredSize());
                        }
                panel.add (Box.createHorizontalStrut (GAP));
                JPanel panel_b = new JPanel();
                panel.add (panel_b);
                panel_b.setLayout (new BoxLayout (panel_b, BoxLayout.Y_AXIS));

                // my score field
                panel_b.add (myScoreField = new JTextField (COLS));
                myScoreField.setText(myName);
                myScoreField.setAlignmentX (0.5f);
                myScoreField.setEditable (false);
                myScoreField.setMaximumSize (myScoreField.getPreferredSize());

                // opponent score field
                panel_b.add (Box.createRigidArea (new Dimension (0, GAP)));
                panel_b.add (theirScoreField = new JTextField (COLS));
                theirScoreField.setText("Waiting for partner");
                theirScoreField.setAlignmentX (0.5f);
                theirScoreField.setEditable (false);
                theirScoreField.setMaximumSize (theirScoreField.getPreferredSize());

                // winner field
                panel_b.add (Box.createRigidArea (new Dimension (0, GAP)));
                panel_b.add (winnerField = new JTextField (COLS));
                winnerField.setAlignmentX (0.5f);
                winnerField.setEditable (false);
                winnerField.setMaximumSize (winnerField.getPreferredSize());

                // new game button field box
                panel_b.add (Box.createVerticalGlue());
                panel_b.add (newGameButton = new JButton ("New Game"));
                newGameButton.setAlignmentX (0.5f);
                newGameButton.setMaximumSize (newGameButton.getPreferredSize());
                newGameButton.setEnabled (false);
                newGameButton.addActionListener (new ActionListener()
                        {
                        public void actionPerformed (ActionEvent e)
                                {
                                onNewGameButton();
                                }
                        });
                addWindowListener (new WindowAdapter()
                        {
                        public void windowClosing (WindowEvent e)
                                {
                                onClose();
                                }
                        });
                pack();
        }

        /*
        *  Set the view listener object for this Fifteen View
        *
        *  @param viewListener ViewListener
        */
        public synchronized void setViewListener( ViewListener viewListener )throws IOException{
           this.viewListener = viewListener;
           setVisible(true);
           viewListener.join( myName );
        }

        /**
         * Take action when a digit button is clicked.
         *
         * @param  digit  Digit that was clicked.
         */
        private void onDigitButton
                (int digit)
                {
                   //digitButton[digit-1].setEnabled(false);
                   //digitButton[digit-1].available(false);
                   try{
                       viewListener.digit(digit);
                   }catch( IOException io ){}

                }

        /**
         * Take action when the New Game button is clicked.
         */
        private void onNewGameButton()
                {
                   newGameButton.setEnabled(true);

                   winnerField.setText("");

                   for( int i = 0; i < 9; i++){
                        digitButton[i].setEnabled(true);
                        digitButton[i].available(true);
                   }
                   for( int i = 0; i < player.size(); i++ ){
                        player.put(i,0);
                   }
                   try{
                        viewListener.newgame();
                   }catch(IOException io){}
                }

        /**
         * Take action when the Fifteen window is closing.
         */
            private void onClose() {
                try {
                    Thread.sleep(1000);
                    quit();
                    System.exit(0);
                }
                catch (InterruptedException ie)
                    {
                    }
            }

        /*
        * Quit the game!
        */
        public void quit() {
                try {
                     viewListener.quit();
                     System.exit(0);
                }
                catch (IOException io){}
        }

        /*
        *  Id of the player
        *
        *  @param i Integer of player
        */
        public void id( int i ){
            player.put(i,0);
            myID = i;
        }

        /*
        * Digit for the buttons which is available or not.
        *
        * @param s String of button
        */
        public void digit( String s ){
            String[] d = s.split("");

            for(int i = 1; i < d.length; i++){
                int j;
                if( d[i].equals("0") ){
                        j = i;
                        digitButton[j-1].available(false);
                }else{
                        j = i;
                        digitButton[j-1].available(true);
                }
            }
        }

        /*
        *  Score of player
        *
        *  @param i Integer for id of player
        *  @param s Integer for score of that player
        */
        public void score( int i, int s ){
            player.put(i,s);

            int score;

            if( i == myID ){
                myScoreField.setText(myName + " = " + s);
            }else{
                theirScoreField.setText(user.get(i) + " = " + player.get(i));
            }
        }

        /*
        *  Player's turn
        *
        *  @param i Integer -> determine who's turn
        */
        public void turn( int i ){
            if( i == myID ){
                for(int j = 0; j < 9; j++){
                        digitButton[j].setEnabled(true);
                }
            }else{
                for(int j = 0; j< 9; j++){
                        digitButton[j].setEnabled(false);
                }
            }
        }

        /*
        *  Win the game!
        *
        *  @param i Integer id of player who won
        */
        public void win( int i ){
            winnerField.setText(user.get(i) + " wins!");
        }

        /*
        * name of the player
        *
        * @param i Integer of ID for player
        * @param n String of name for player
        */
        public void name( int i, String n ){
            user.put(i,n);
            if( user.size() > 1){
                myScoreField.setText(myName + " = " + myScore);
                int score;
                if(player.get(i) == null) {
                     score = 0;
                }else{
                        score = player.get(i);
                }
                theirScoreField.setText(n + " = " + score);
                theirName = n;
                newGameButton.setEnabled(true);

            }
        }
        }
