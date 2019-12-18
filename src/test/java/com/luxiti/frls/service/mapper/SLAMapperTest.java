package com.luxiti.frls.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SLAMapperTest {

    private SLAMapper sLAMapper;

    @BeforeEach
    public void setUp() {
        sLAMapper = new SLAMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(sLAMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sLAMapper.fromId(null)).isNull();
    }
}
