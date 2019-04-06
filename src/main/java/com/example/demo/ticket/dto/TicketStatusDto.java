package com.example.demo.ticket.dto;

import com.example.demo.ticket.TicketStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketStatusDto {
    @NotNull
    private TicketStatusEnum status;
}
