package com.luxiti.frls.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.luxiti.frls.web.rest.TestUtil;

public class SLATest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SLA.class);
        SLA sLA1 = new SLA();
        sLA1.setId(1L);
        SLA sLA2 = new SLA();
        sLA2.setId(sLA1.getId());
        assertThat(sLA1).isEqualTo(sLA2);
        sLA2.setId(2L);
        assertThat(sLA1).isNotEqualTo(sLA2);
        sLA1.setId(null);
        assertThat(sLA1).isNotEqualTo(sLA2);
    }
}
