package com.luxiti.frls.service.mapper;

import com.luxiti.frls.domain.*;
import com.luxiti.frls.service.dto.TicketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
    Ticket toEntity(TicketDTO ticketDTO);

    default Ticket fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
