package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeCheckGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeCheckGroupDTO.class);
        QeCheckGroupDTO qeCheckGroupDTO1 = new QeCheckGroupDTO();
        qeCheckGroupDTO1.setId(1L);
        QeCheckGroupDTO qeCheckGroupDTO2 = new QeCheckGroupDTO();
        assertThat(qeCheckGroupDTO1).isNotEqualTo(qeCheckGroupDTO2);
        qeCheckGroupDTO2.setId(qeCheckGroupDTO1.getId());
        assertThat(qeCheckGroupDTO1).isEqualTo(qeCheckGroupDTO2);
        qeCheckGroupDTO2.setId(2L);
        assertThat(qeCheckGroupDTO1).isNotEqualTo(qeCheckGroupDTO2);
        qeCheckGroupDTO1.setId(null);
        assertThat(qeCheckGroupDTO1).isNotEqualTo(qeCheckGroupDTO2);
    }
}
