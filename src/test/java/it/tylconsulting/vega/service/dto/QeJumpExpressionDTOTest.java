package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeJumpExpressionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeJumpExpressionDTO.class);
        QeJumpExpressionDTO qeJumpExpressionDTO1 = new QeJumpExpressionDTO();
        qeJumpExpressionDTO1.setId(1L);
        QeJumpExpressionDTO qeJumpExpressionDTO2 = new QeJumpExpressionDTO();
        assertThat(qeJumpExpressionDTO1).isNotEqualTo(qeJumpExpressionDTO2);
        qeJumpExpressionDTO2.setId(qeJumpExpressionDTO1.getId());
        assertThat(qeJumpExpressionDTO1).isEqualTo(qeJumpExpressionDTO2);
        qeJumpExpressionDTO2.setId(2L);
        assertThat(qeJumpExpressionDTO1).isNotEqualTo(qeJumpExpressionDTO2);
        qeJumpExpressionDTO1.setId(null);
        assertThat(qeJumpExpressionDTO1).isNotEqualTo(qeJumpExpressionDTO2);
    }
}
