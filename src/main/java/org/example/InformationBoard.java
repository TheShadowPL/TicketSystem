package org.example;

import java.util.Queue;

public class InformationBoard {
    private Queue<Ticket> ticketQueue;

    public InformationBoard(Queue<Ticket> ticketQueue) {
        this.ticketQueue = ticketQueue;
    }

    public void display() {
        System.out.println("Oczekujące bilety: ");
        for (Ticket ticket : ticketQueue) {
            System.out.println(ticket);
        }
    }
}
