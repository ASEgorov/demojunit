package com.example.demo;

import com.example.demo.ticket.TicketService;
import com.example.demo.ticket.dto.TicketDto;
import com.example.demo.ticket.dto.TicketStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping
    public TicketDto create(@Valid TicketDto ticket){
        return ticketService.create(ticket);
    }

    @PutMapping("/{id}/status")
    public TicketDto updateStatus(@RequestBody @Valid TicketStatusDto ticketStatusDto, @PathVariable("id") Long id){
        return ticketService.updateStatus(id, ticketStatusDto);
    }

    @GetMapping
    public List<TicketDto> getAll(){
        return ticketService.getAll();
    }

}
