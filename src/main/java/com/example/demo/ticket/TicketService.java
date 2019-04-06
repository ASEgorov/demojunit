package com.example.demo.ticket;

import com.example.demo.ticket.domain.TicketRepository;
import com.example.demo.ticket.dto.TicketDto;
import com.example.demo.ticket.dto.TicketStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketDto create(TicketDto ticket) {
        return null;
    }

    public List<TicketDto> getAll() {
        return ticketRepository.findAll().stream()
                .map(e -> new TicketDto(e.getId(), e.getStatus(), e.getMessage()))
                .collect(Collectors.toList());
    }

    public TicketDto updateStatus(Long id, TicketStatusDto ticketStatusDto) {
        return null;
    }
}
