package com.example.demo.ticket.domain;

import com.example.demo.ticket.TicketStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class TicketVO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private TicketStatusEnum status;
    private String message;

}
