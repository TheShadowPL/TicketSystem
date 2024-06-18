package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;

public class TicketMachine {
    private Map<String, AtomicInteger> categoryCounters;
    private TicketingSystem system;

    public TicketMachine(TicketingSystem system, String[] categories) {
        this.system = system;
        this.categoryCounters = new HashMap<>();
        for (String category : categories) {
            categoryCounters.put(category, new AtomicInteger(0));
        }
    }

    public Ticket issueTicket(String category) {
        int number = categoryCounters.get(category).incrementAndGet();
        Ticket ticket = new Ticket(category, number);
        system.addTicket(ticket);
        return ticket;
    }
}
