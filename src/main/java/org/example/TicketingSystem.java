package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class TicketingSystem {
    private Queue<Ticket> ticketQueue = new LinkedList<>();

    public void addTicket(Ticket ticket) {
        ticketQueue.add(ticket);
    }

    public void prioritizeTicket(Ticket ticket) {
        ((LinkedList<Ticket>) ticketQueue).addFirst(ticket);
    }

    public Queue<Ticket> getTicketQueue() {
        return ticketQueue;
    }
}
