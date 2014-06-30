/**
*@CDAccount
*
*Version:
*       $Id$
*
*Revision:
*       $Log$
*/

/**
*The CDAccount class represents a Certificate of Deposit account
*
*@author : Kristin Parker (kkp5232)
*/

public class CDAccount extends BankAccount {

        /**
        *MINIMUM_ BALANCE - field - the minimum amount that must be kept in the account at all times; interest is earned only on the amount over the minimum- courently $1000
        */
        public static final double MINIMUM_BALANCE = 1000.0;

        /**
        *MONTHLY_INTEREST_RATE - field - the interest rate for balances greater than the minimum - currently 6%
        *
        */
        public static final double MONTHLY_INTEREST_RATE = 0.005;


        /**
        *CDAccount - constructor - a constructor for a CDAccount object
        *It accepts the amount of money deposited when the account is created and the owner name
        *You are guaranteed that the parameter will always be greater than the minimum amount required
        *
        *@param : newMoney - the amount of money deposited when the account is opened
        *         owner - the owner of this account
        */
        public CDAccount( double newMoney, java.lang.String ownerName ) {

                super( newMoney, ownerName );

        }


        /**
        *calcInterest - method 0 Calculate interest on this account
        *With C of D accounts no interest is earned on the minimum that mus talways be kept in the account
        *Interest is only earned on everything on everything over that amount
        *Interest earned should be added to the current balace
        *
        *@Specified by: calcInterest in class BankAccount
        */
        public void calcInterest() {

                double interestEarned = getCurrentBalance() * MONTHLY_INTEREST_RATE;

                if (interestEarned < MINIMUM_BALANCE) {

                        interestEarned = MINIMUM_BALANCE;

                }
                setInterestEarned( interestEarned );
        }

        /**
        *toString - method - what kind of account am I?
        *
        *@overrides : toString in class BankAccount
        *@return : the owner name, current balance and the literal "CD" to to identify this account
        */
        public java.lang.String toString() {

                return String.format("A CDAccount with initial balance %10.2f and owner " + getOwnerName() + "", getCurrentBalance());

        }

        /**
        *main
        */
        public static void main( java.lang.String[] args ) {

        }


}//end of the CDAccount class
