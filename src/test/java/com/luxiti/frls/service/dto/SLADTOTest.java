package com.luxiti.frls.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.luxiti.frls.web.rest.TestUtil;

public class SLADTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SLADTO.class);
        SLADTO sLADTO1 = new SLADTO();
        sLADTO1.setId(1L);
        SLADTO sLADTO2 = new SLADTO();
        assertThat(sLADTO1).isNotEqualTo(sLADTO2);
        sLADTO2.setId(sLADTO1.getId());
        assertThat(sLADTO1).isEqualTo(sLADTO2);
        sLADTO2.setId(2L);
        assertThat(sLADTO1).isNotEqualTo(sLADTO2);
        sLADTO1.setId(null);
        assertThat(sLADTO1).isNotEqualTo(sLADTO2);
    }
}
