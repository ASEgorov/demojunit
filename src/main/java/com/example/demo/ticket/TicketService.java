package com.example.demo.ticket;

import com.example.demo.ticket.domain.TicketRepository;
import com.example.demo.ticket.domain.TicketVO;
import com.example.demo.ticket.dto.TicketDto;
import com.example.demo.ticket.dto.TicketStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketDto create(TicketDto ticket) {
        if(ticket.getId() != null){
            throw new TicketException("Идентификатор не должен быть заполнен");
        }

        if(!ticket.getStatus().equals(TicketStatusEnum.FIRST)){
            throw new TicketException("Можно создавать только в начальном статусе");
        }

        TicketVO vo = new TicketVO();
        vo.setStatus(ticket.getStatus());
        vo.setMessage(ticket.getMessage());

        TicketVO newVo = ticketRepository.save(vo);
        return new TicketDto(newVo.getId(), newVo.getStatus(), newVo.getMessage());
    }

    public List<TicketDto> getAll() {
        return ticketRepository.findAll().stream()
                .map(e -> new TicketDto(e.getId(), e.getStatus(), e.getMessage()))
                .collect(Collectors.toList());
    }

    public TicketDto updateStatus(Long id, TicketStatusDto ticketStatusDto) {
        Optional<TicketVO> byId = ticketRepository.findById(id);
        if(!byId.isPresent()){
            throw new TicketException("Тикет не найден");
        }

        if (byId.get().getStatus().equals(TicketStatusEnum.FINAL)){
            throw new TicketException("Статус нельзя менять если статус финальный");
        }

        byId.get().setStatus(ticketStatusDto.getStatus());
        TicketVO newVo = ticketRepository.save(byId.get());
        return new TicketDto(newVo.getId(), newVo.getStatus(), newVo.getMessage());
    }
}
