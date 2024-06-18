package org.example;

public class Ticket {
    private String category;
    private int number;

    public Ticket(String category, int number) {
        this.category = category;
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return category + number;
    }
}

