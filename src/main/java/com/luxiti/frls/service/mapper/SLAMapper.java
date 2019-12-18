package com.luxiti.frls.service.mapper;

import com.luxiti.frls.domain.*;
import com.luxiti.frls.service.dto.SLADTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SLA} and its DTO {@link SLADTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SLAMapper extends EntityMapper<SLADTO, SLA> {

    @Mapping(source = "user.id", target = "userId")
    SLADTO toDto(SLA sLA);

    @Mapping(source = "userId", target = "user")
    SLA toEntity(SLADTO sLADTO);

    default SLA fromId(Long id) {
        if (id == null) {
            return null;
        }
        SLA sLA = new SLA();
        sLA.setId(id);
        return sLA;
    }
}
