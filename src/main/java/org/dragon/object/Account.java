package org.dragon.object;

public class Account {
    private String name;
    private double balance;

    public Account(String name) {
        this.name = name;
        this.balance = 0;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setBalance(double balance) {
        this.balance = Double.parseDouble(String.format("%.2f", balance));
    }

    public void addBalance(double val) {
        this.balance = Double.parseDouble(String.format("%.2f", this.balance + val));
    }

    public void subtractBalance(double val) {
        this.balance = Double.parseDouble(String.format("%.2f", this.balance - val));
    }

    public double getBalance() {
        return this.balance;
    }

}
