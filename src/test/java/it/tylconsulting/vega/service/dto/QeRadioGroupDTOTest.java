package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeRadioGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeRadioGroupDTO.class);
        QeRadioGroupDTO qeRadioGroupDTO1 = new QeRadioGroupDTO();
        qeRadioGroupDTO1.setId(1L);
        QeRadioGroupDTO qeRadioGroupDTO2 = new QeRadioGroupDTO();
        assertThat(qeRadioGroupDTO1).isNotEqualTo(qeRadioGroupDTO2);
        qeRadioGroupDTO2.setId(qeRadioGroupDTO1.getId());
        assertThat(qeRadioGroupDTO1).isEqualTo(qeRadioGroupDTO2);
        qeRadioGroupDTO2.setId(2L);
        assertThat(qeRadioGroupDTO1).isNotEqualTo(qeRadioGroupDTO2);
        qeRadioGroupDTO1.setId(null);
        assertThat(qeRadioGroupDTO1).isNotEqualTo(qeRadioGroupDTO2);
    }
}
