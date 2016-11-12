package com.palle.rathan.pocketcare.model;

/**
 * Created by Rathan on 10/2/2016.
 */
public class Transaction {
    private int id;
    private String name, type, category, date;
    private double amount;

    public Transaction(int id, String name, String type, String category, String date, double amount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
