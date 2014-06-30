/**
*@SavingsAccount
*
*Version:
*       $Id$
*
*Revision:
*       $Log$
*/

/**
*The SavingAccount class is a subclass of the abstract BankAccount
*Interest is calculated and credited to the account monthly
*
*@author : Kristin Parker (kkp5232)
*/

public class SavingsAccount extends BankAccount {

        /**
        *SAVINGS_MONTHLY_INTEREST_RATE - field - the annual interest rate for a standard savings account is currently 3%
        */
        public static final double SAVINGS_MONTHLY_INTEREST_RATE = 0.0025;


        /**
        *SavingsAccount - constructor - a constructor for a SavingsAccount object
        *It accepts the amount of money deposited when the account is created and the owner name
        *
        *@param : newMoney - the amount of money deposited when the account is opened
        *         owner - the owner of this account
        */
        public SavingsAccount( double newMoney, java.lang.String ownerName ) {

                super( newMoney, ownerName );

        }



        /**
        *calcInterest - method - calculate the interest for this account
        *Savings accounts earn interest on the entire current balance
        *Interest earned is calculated monthly, and added to the current balance
        *
        *@specified by : calcInterest in class BankAccount
        */
        public void calcInterest() {

                double interestEarned = getCurrentBalance() * SAVINGS_MONTHLY_INTEREST_RATE;
                setInterestEarned( interestEarned );
        }


        /**
        *toString - method - a printable version of this account
        *
        *@overrides : toString in class BankAccount
        *@return : the owner name, current balance and the literal "Savings" to identify this account
        */
        public java.lang.String toString() {

                return String.format("A SavingsAccount with initial balance %10.2f and owner " + getOwnerName(), getCurrentBalance());

        }

}//end of the SavingsAccount class
