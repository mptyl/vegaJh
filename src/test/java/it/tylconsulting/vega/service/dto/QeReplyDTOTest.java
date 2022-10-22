package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeReplyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeReplyDTO.class);
        QeReplyDTO qeReplyDTO1 = new QeReplyDTO();
        qeReplyDTO1.setId(1L);
        QeReplyDTO qeReplyDTO2 = new QeReplyDTO();
        assertThat(qeReplyDTO1).isNotEqualTo(qeReplyDTO2);
        qeReplyDTO2.setId(qeReplyDTO1.getId());
        assertThat(qeReplyDTO1).isEqualTo(qeReplyDTO2);
        qeReplyDTO2.setId(2L);
        assertThat(qeReplyDTO1).isNotEqualTo(qeReplyDTO2);
        qeReplyDTO1.setId(null);
        assertThat(qeReplyDTO1).isNotEqualTo(qeReplyDTO2);
    }
}
