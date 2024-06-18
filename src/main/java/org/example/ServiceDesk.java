package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class ServiceDesk {
    private String[] categoriesHandled;
    private Queue<Ticket> ticketQueue;
    private Ticket currentTicket;
    private int deskNumber;
    private SpeechUtils speechUtils;

    public ServiceDesk(String[] categoriesHandled, Queue<Ticket> ticketQueue, int deskNumber, SpeechUtils speechUtils) {
        this.categoriesHandled = categoriesHandled;
        this.ticketQueue = ticketQueue;
        this.deskNumber = deskNumber;
        this.speechUtils = speechUtils;
    }

    public void nextTicket() {
        while ((currentTicket = ticketQueue.poll()) != null) {
            for (String category : categoriesHandled) {
                if (currentTicket.getCategory().equals(category)) {
                    String message = "Ticket " + currentTicket + " to desk number " + deskNumber;
                    System.out.println("Powiadomienie głosowe: " + message);
                    speechUtils.speak(message);
                    return;
                }
            }
        }
    }

    public Ticket getCurrentTicket() {
        return currentTicket;
    }

    public String[] getCategoriesHandled() {
        return categoriesHandled;
    }

    public void setCategoriesHandled(String[] categoriesHandled) {
        this.categoriesHandled = categoriesHandled;
    }
}