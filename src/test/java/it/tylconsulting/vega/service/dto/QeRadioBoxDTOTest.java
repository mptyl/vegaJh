package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeRadioBoxDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeRadioBoxDTO.class);
        QeRadioBoxDTO qeRadioBoxDTO1 = new QeRadioBoxDTO();
        qeRadioBoxDTO1.setId(1L);
        QeRadioBoxDTO qeRadioBoxDTO2 = new QeRadioBoxDTO();
        assertThat(qeRadioBoxDTO1).isNotEqualTo(qeRadioBoxDTO2);
        qeRadioBoxDTO2.setId(qeRadioBoxDTO1.getId());
        assertThat(qeRadioBoxDTO1).isEqualTo(qeRadioBoxDTO2);
        qeRadioBoxDTO2.setId(2L);
        assertThat(qeRadioBoxDTO1).isNotEqualTo(qeRadioBoxDTO2);
        qeRadioBoxDTO1.setId(null);
        assertThat(qeRadioBoxDTO1).isNotEqualTo(qeRadioBoxDTO2);
    }
}
