package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeGroupDTO.class);
        QeGroupDTO qeGroupDTO1 = new QeGroupDTO();
        qeGroupDTO1.setId(1L);
        QeGroupDTO qeGroupDTO2 = new QeGroupDTO();
        assertThat(qeGroupDTO1).isNotEqualTo(qeGroupDTO2);
        qeGroupDTO2.setId(qeGroupDTO1.getId());
        assertThat(qeGroupDTO1).isEqualTo(qeGroupDTO2);
        qeGroupDTO2.setId(2L);
        assertThat(qeGroupDTO1).isNotEqualTo(qeGroupDTO2);
        qeGroupDTO1.setId(null);
        assertThat(qeGroupDTO1).isNotEqualTo(qeGroupDTO2);
    }
}
