package com.example.demo.ticket;

public class TicketException extends RuntimeException {
    public TicketException(String error) {
        super(error);
    }
}
