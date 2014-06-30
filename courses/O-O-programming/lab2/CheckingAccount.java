/**
*@CheckAccount
*
*Version:
*       $Id$
*
*Revision:
*       $Log$
*/

/**
*The CheckingAccount class represents a checking account
*It is a subclass of the abstract class BankAccount
*
*@author : Kristin Parker (kkp5232)
*/

public class CheckingAccount extends BankAccount {

        /**
        *BONUS_MONTHLY_RATE - field - the interest rate for a bonus account - currently 1.5%
        */
        public static final double BONUS_MONTHLY_RATE = 0.00125;

        private boolean bonus;

        /**
        *CheckingAccount - constructor - a constructor for a CheckingAccount object
        *It accepts the amount of money deposited when the account is created
        *
        *@param : newMoney - The amount of money deposited when the account is opened
        *         owner - the owner of this account
        *         bonus - a boolean indicating if this account is paid interest
        */
        public CheckingAccount(double newMoney, java.lang.String ownerName, boolean bonus) {

                super( newMoney, ownerName );
                this.bonus = bonus;

        }

        /**
        *calcInterest - method - calculate interest for this account
        *Checking accounts earn interest only if a "bonus" account
        *
        *specified by: calcInterest in class BankAccount
        */
        public void calcInterest() {

                if ( bonus == true ) {
                        double interestEarned = getCurrentBalance() * BONUS_MONTHLY_RATE;
                        setInterestEarned( interestEarned );
                }

        }


        /**
        *toString - method - return a printalbe version of this object
        *
        *@overrides : toString in class BankAccount
        *@return : the owner name, current blance and the litteral "Checking" to identify this account
        */
        public java.lang.String toString() {

                return String.format("A CheckingAccount with inital balance %10.2f, owner " + getOwnerName() + ", and bonus flag \"" + bonus + " \" ", getCurrentBalance());

        }

}//end of the CheckingAccount class
