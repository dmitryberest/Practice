/*
 * Создать класс  Account с внутренним классом, с помощью объектов
 * которого можно хранить информацию обо всех операциях со счетом
 * (снятие, платежи, поступления).
 */
package com.company.Main;

        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

public class Bank {


    public static void main(String[] args) {

        Account accaunt = new Account();

        accaunt.newTransaction(Account.typeTransaction.RECEIPT, 2000);
        accaunt.newTransaction(Account.typeTransaction.PAYMENT, 100);
        accaunt.newTransaction(Account.typeTransaction.WITHDRAWAL, 1500);

        System.out.println(accaunt.print());

    }

}



class Account {

    enum typeTransaction {
        WITHDRAWAL, PAYMENT, RECEIPT
    }

    int accountBalance = 0;
    int nextId = 0;

    List<Transaction> history = new ArrayList<>();

    abstract class Transaction {
        int id = 0;
        int amount = 0;
        int balance = 0;
        Date date = new Date();
        typeTransaction type;

        public Transaction() {
            this.id = nextId++;
        }

        @Override
        public String toString() {
            String string;
            string =  '\n' + "Transaction ID: "  + this.id + '\n';
            string += "Type: " + this.type + '\n';
            string += "Date: " + this.date + '\n';
            string += "Amount: " + this.amount + '\n';
            string += "Balance after operation: " + this.balance + '\n';
            return string;
        }

    }

    class Withdrawal extends Transaction {

        public Withdrawal(int amount) {
            this.balance = accountBalance -= amount;
            this.amount = amount;
        }


    }

    class Payment extends Transaction {

        public Payment(int amount) {
            this.balance = accountBalance -= amount;
            this.amount = amount;

        }


    }

    class Receipt extends Transaction {

        public Receipt(int amount) {
            this.balance = accountBalance += amount;
            this.amount = amount;
        }


    }

    public void newTransaction(typeTransaction type, int amount)
            throws UnsupportedOperationException {

        Transaction newTransaction;

        switch (type) {
            case WITHDRAWAL:
                newTransaction = new Withdrawal(amount);
                break;

            case PAYMENT:
                newTransaction = new Payment(amount);
                break;

            case RECEIPT:
                newTransaction = new Receipt(amount);
                break;

            default:
                throw new UnsupportedOperationException("Not supported yet.");

        }

        newTransaction.type = type;
        history.add(newTransaction);
    }

    public String print() {
        String string = "";

        for( Transaction t : history)
        {
            string += t.toString();
        }


        return string;
    }
}