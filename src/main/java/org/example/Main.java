package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

public class Main {
    private static TicketingSystem system = new TicketingSystem();
    private static List<String> categories = new ArrayList<>(Arrays.asList("A", "B", "C"));
    private static Map<String, Integer> priorities = new HashMap<>();
    private static ConfigAgent agent;
    private static TicketMachine machine;
    private static ServiceDesk deskA;
    private static ServiceDesk deskBC;
    private static InformationBoard board;
    private static SpeechUtils speechUtils;

    static {
        priorities.put("A", 1);
        priorities.put("B", 2);
        priorities.put("C", 3);
        agent = new ConfigAgent(categories, priorities);
        speechUtils = new SpeechUtils();
        machine = new TicketMachine(system, categories.toArray(new String[0]));
        deskA = new ServiceDesk(new String[]{"A"}, system.getTicketQueue(), 1, speechUtils);
        deskBC = new ServiceDesk(new String[]{"B", "C"}, system.getTicketQueue(), 2, speechUtils);
        board = new InformationBoard(system.getTicketQueue());
    }

    public static void main(String[] args) {
        agent.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Powiadomienie: " + evt.getPropertyName() + " zmienione z " +
                        evt.getOldValue() + " na " + evt.getNewValue());
            }
        });

        SwingUtilities.invokeLater(Main::createAndShowGUI);
        handleConsoleInput();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("System Biletowy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 550);
        frame.setLayout(new BorderLayout());

        JPanel panelTicketMachine = new JPanel();
        panelTicketMachine.setBorder(BorderFactory.createTitledBorder("Biletomat"));
        JComboBox<String> categoryComboBox = new JComboBox<>(categories.toArray(new String[0]));
        JButton issueTicketButton = new JButton("Pobierz bilet");
        JTextArea ticketArea = new JTextArea(5, 20);
        ticketArea.setEditable(false);
        panelTicketMachine.add(categoryComboBox);
        panelTicketMachine.add(issueTicketButton);
        panelTicketMachine.add(new JScrollPane(ticketArea));

        JPanel panelServiceDeskA = new JPanel();
        panelServiceDeskA.setBorder(BorderFactory.createTitledBorder("Stanowisko 1 (obsługa biletów A)"));
        JButton nextTicketButtonA = new JButton("Następny bilet");
        JTextArea currentTicketAreaA = new JTextArea(5, 20);
        currentTicketAreaA.setEditable(false);
        panelServiceDeskA.add(nextTicketButtonA);
        panelServiceDeskA.add(new JScrollPane(currentTicketAreaA));

        JPanel panelServiceDeskBC = new JPanel();
        panelServiceDeskBC.setBorder(BorderFactory.createTitledBorder("Stanowisko 2 (obsługa biletów B i C)"));
        JButton nextTicketButtonBC = new JButton("Następny bilet");
        JTextArea currentTicketAreaBC = new JTextArea(5, 20);
        currentTicketAreaBC.setEditable(false);
        panelServiceDeskBC.add(nextTicketButtonBC);
        panelServiceDeskBC.add(new JScrollPane(currentTicketAreaBC));

        JPanel panelInformationBoard = new JPanel();
        panelInformationBoard.setBorder(BorderFactory.createTitledBorder("Tablica Informacyjna"));
        JTextArea boardArea = new JTextArea(10, 50);
        boardArea.setEditable(false);
        panelInformationBoard.add(new JScrollPane(boardArea));

        frame.add(panelTicketMachine, BorderLayout.NORTH);
        frame.add(panelServiceDeskA, BorderLayout.WEST);
        frame.add(panelServiceDeskBC, BorderLayout.EAST);
        frame.add(panelInformationBoard, BorderLayout.SOUTH);

        issueTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryComboBox.getSelectedItem();
                Ticket ticket = machine.issueTicket(category);
                ticketArea.append("Wydano bilet: " + ticket + "\n");
                updateBoard(boardArea);
            }
        });

        nextTicketButtonA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deskA.nextTicket();
                Ticket currentTicket = deskA.getCurrentTicket();
                if (currentTicket != null) {
                    currentTicketAreaA.setText("Obecnie obsługiwany bilet: " + currentTicket + "\n");
                } else {
                    currentTicketAreaA.setText("Brak biletów do obsługi\n");
                }
                updateBoard(boardArea);
            }
        });

        nextTicketButtonBC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deskBC.nextTicket();
                Ticket currentTicket = deskBC.getCurrentTicket();
                if (currentTicket != null) {
                    currentTicketAreaBC.setText("Obecnie obsługiwany bilet: " + currentTicket + "\n");
                } else {
                    currentTicketAreaBC.setText("Brak biletów do obsługi\n");
                }
                updateBoard(boardArea);
            }
        });

        frame.setVisible(true);
    }

    private static void updateBoard(JTextArea boardArea) {
        Queue<Ticket> ticketQueue = system.getTicketQueue();
        boardArea.setText("Oczekujące bilety:\n");
        for (Ticket ticket : ticketQueue) {
            boardArea.append(ticket + "\n");
        }
    }

    private static void handleConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Wpisz numer biletu do nadania pierwszeństwa (np. A1):");
            String input = scanner.nextLine();
            if (input.matches("[A-Z]\\d+")) {
                String category = input.substring(0, 1);
                int number = Integer.parseInt(input.substring(1));
                Ticket priorityTicket = null;
                for (Ticket ticket : system.getTicketQueue()) {
                    if (ticket.getCategory().equals(category) && ticket.getNumber() == number) {
                        priorityTicket = ticket;
                        break;
                    }
                }
                if (priorityTicket != null) {
                    system.prioritizeTicket(priorityTicket);
                    System.out.println("Bilet " + priorityTicket + " został ustawiony z pierwszeństwem.");
                } else {
                    System.out.println("Nie znaleziono biletu " + input);
                }
            } else {
                System.out.println("Niepoprawny format biletu. Spróbuj ponownie.");
            }
        }
    }
}


