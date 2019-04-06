package com.example.demo.ticket.dto;


import com.example.demo.ticket.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private Long id;
    @NotNull
    private TicketStatusEnum status;
    @NotNull
    private String message;
}
