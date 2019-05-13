package com.example.demo.ticket;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.demo.ticket.domain.TicketRepository;
import com.example.demo.ticket.domain.TicketVO;
import com.example.demo.ticket.dto.TicketDto;
import com.example.demo.ticket.dto.TicketStatusDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketServiceTest {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;

    private Logger logger;
    private ListAppender<ILoggingEvent> listAppender;


    @Before
    public void initAppender(){
        logger = (Logger) LoggerFactory.getLogger(TicketService.class);
        listAppender = new ListAppender<>();
        logger.addAppender(listAppender);
        listAppender.start();
    }

    @After
    public void stopAppender(){
        listAppender.stop();
        listAppender.list.clear();
    }

    /**
     * Проверяем что хороший тикет корректно создается
     */
    @Test
    public void createGoodTicket(){
        TicketDto ticketDto = new TicketDto(null, TicketStatusEnum.FIRST, "test");

        TicketDto createdTicket = ticketService.create(ticketDto);

        // Есть в базе
        assertEquals(1, ticketRepository.count());

        // Присвоился айдишник
        assertNotNull(createdTicket.getId());

    }

    /**
     * Проверяем что тикет с заполненым идентификатором не создается
     */
    @Test(expected = TicketException.class)
    public void createBadWithIdTicket(){
        TicketDto ticketDto = new TicketDto(1L, TicketStatusEnum.FIRST, "test");

        ticketService.create(ticketDto);

    }

    /**
     * Проверяем что тикет с неправильным статусом не создается
     */
    @Test(expected = TicketException.class)
    public void createBadStatusTicket(){
        TicketDto ticketDto = new TicketDto(null, TicketStatusEnum.SECOND, "test");

        ticketService.create(ticketDto);

    }


    /* TODO
     * Можно отдельно проверить что изменений в БД после отправки плохого тикета не происходит
     */

    /**
     * Проверяем что допустимое изменение статуса происходит корректно
     * TODO Нужна проверка изменения статуса в обратном направлении
     */
    @Test
    public void changeGoodStatus(){
        TicketVO vo = new TicketVO();
        vo.setId(1L);
        vo.setStatus(TicketStatusEnum.FIRST);
        vo.setMessage("test");

        ticketRepository.save(vo);


        TicketStatusDto statusDto = new TicketStatusDto();
        statusDto.setStatus(TicketStatusEnum.SECOND);

        ticketService.updateStatus(vo.getId(), statusDto);

        Optional<TicketVO> changedVo = ticketRepository.findById(vo.getId());
        assertTrue(changedVo.isPresent());
        assertEquals(TicketStatusEnum.SECOND, changedVo.get().getStatus());

    }

    // TODO Нужно проверить что нельзя поменять статус у несуществующего тикета

    /**
     * Проверяем что недопустимое изменение статуса не происходит
     */
    @Test(expected = TicketException.class)
    public void changeBadStatus(){
        TicketVO vo = new TicketVO();
        vo.setId(1L);
        vo.setStatus(TicketStatusEnum.FINAL);
        vo.setMessage("test");

        ticketRepository.save(vo);


        TicketStatusDto statusDto = new TicketStatusDto();
        statusDto.setStatus(TicketStatusEnum.SECOND);

        ticketService.updateStatus(vo.getId(), statusDto);
    }

    /**
     * Проверяем что сервис возвращает то что есть в базе
     */
    @Test
    public void getAll(){
        TicketVO vo = new TicketVO();
        vo.setStatus(TicketStatusEnum.FIRST);
        vo.setMessage("test");

        ticketRepository.save(vo);

        vo = new TicketVO();
        vo.setStatus(TicketStatusEnum.FIRST);
        vo.setMessage("test");

        ticketRepository.save(vo);

        List<TicketDto> all = ticketService.getAll();
        assertEquals(2, all.size());
    }

    @Test
    public void log(){

        ticketService.logMessage("test");

        assertEquals(1, listAppender.list.size());

        assertEquals(Level.WARN, listAppender.list.get(0).getLevel());

    }
}