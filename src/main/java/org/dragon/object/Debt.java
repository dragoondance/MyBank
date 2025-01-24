package org.dragon.object;

public class Debt {
    private String target;
    private String from;
    private double amount;
    
    public Debt(String name, String target, double amount) {
        this.from = name;
        this.target = target;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getTarget() {
        return target;
    }

    public String getFrom() {
        return from;
    }

    public void subtractAmount(double amount) {
        this.amount = Double.parseDouble(String.format("%.2f", this.amount - amount));
    }

    public void addAmount(double amount) {
        this.amount = Double.parseDouble(String.format("%.2f", this.amount + amount));
    }

    public void setAmount(double amount) {
        this.amount = Double.parseDouble(String.format("%.2f", this.amount + amount));
    }
}
