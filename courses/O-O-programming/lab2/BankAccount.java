/**
*@BankAccount
*
*Version:
*       $Id$
*
*Revision:
*       $Log$
*/


/*
*The BankAccount class represents a bank account.
*This abstract class includes a method to calculate interest on the account
*Since account types use different calculations for calculating interest, the method is abstract; subclasses must implement this method for the specific account type.
*
*@author : Kristin Parker (kkp5232)
*
*/

import java.text.DecimalFormat;

public abstract class BankAccount extends java.lang.Object {

        private double newMoney;
        private java.lang.String ownerName;
        private double interestEarned;



        /**
        *BankAccount - Constructor - A constructor for a BankAccount object
        *It accepts the amount of money deposited ( the balance ) when the account is created
        *
        *@param : newMoney - the amount of money deposited when the account is opened
        *         ownerName - the owner of this account
        */
        public BankAccount( double newMoney, java.lang.String ownerName ) {

                this.newMoney = newMoney;
                this.ownerName = ownerName;

        }


        /**
        *calcInterest - method - Calculate interest for this account
        *The rules for earning interest are different for every kind of account
        */
        public abstract void calcInterest();


        /**
        *setInterestEarned - method - Store the interest earned
        *
        *@param  : interest - the calculated interest amount
        */
        protected void setInterestEarned( double interest ) {

                interestEarned = interest;

        }


        /**
        *printStatement - method - Print a statement for this month
        */
        public void printStatement() {

                System.out.printf(getOwnerName() + "\tInterest Earned:\t %10.2f \t\tCurrent Balance:\t %10.2f \n", getInterest(),getCurrentBalance());    

        }


        /**
        *getCurrentBalance - method - return the current balance
        *
        *@return : the current balance
        */
        public double getCurrentBalance() {
                return newMoney + getInterest();

        }


        /**
        *getInterest - method - Return the current interest earned
        *
        *@return : the current interest earned
        */
        public double getInterest() {

                return interestEarned;

        }

        /**
        *getOwnerName - method - Return the current owner name
        *
        *@return : the owner's name
        */
        public String getOwnerName() {

                return ownerName;

        }

        /**
        *addInterest - method - Add the current interest amount to the current balance
        *
        *@param : newInterestEarned - the total amount of interest earned.
        */
        protected void addInterest( double newInterestEarned ) {

                newMoney += getInterest();
        }


        /**
        *toString - method - A printable version of this account
        *
        *@Overrides : toString in class java.lang.Object
        *@return : the owner name and the current balance
        */
        public java.lang.String toString() {

                return ( "the owner name: " + getOwnerName() + "    Current Balance: %10.2f" + getCurrentBalance() );
        }
}// end of the BankAccount class

