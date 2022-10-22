package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeCheckBoxDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeCheckBoxDTO.class);
        QeCheckBoxDTO qeCheckBoxDTO1 = new QeCheckBoxDTO();
        qeCheckBoxDTO1.setId(1L);
        QeCheckBoxDTO qeCheckBoxDTO2 = new QeCheckBoxDTO();
        assertThat(qeCheckBoxDTO1).isNotEqualTo(qeCheckBoxDTO2);
        qeCheckBoxDTO2.setId(qeCheckBoxDTO1.getId());
        assertThat(qeCheckBoxDTO1).isEqualTo(qeCheckBoxDTO2);
        qeCheckBoxDTO2.setId(2L);
        assertThat(qeCheckBoxDTO1).isNotEqualTo(qeCheckBoxDTO2);
        qeCheckBoxDTO1.setId(null);
        assertThat(qeCheckBoxDTO1).isNotEqualTo(qeCheckBoxDTO2);
    }
}
