package it.tylconsulting.vega.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeQuestionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeQuestionDTO.class);
        QeQuestionDTO qeQuestionDTO1 = new QeQuestionDTO();
        qeQuestionDTO1.setId(1L);
        QeQuestionDTO qeQuestionDTO2 = new QeQuestionDTO();
        assertThat(qeQuestionDTO1).isNotEqualTo(qeQuestionDTO2);
        qeQuestionDTO2.setId(qeQuestionDTO1.getId());
        assertThat(qeQuestionDTO1).isEqualTo(qeQuestionDTO2);
        qeQuestionDTO2.setId(2L);
        assertThat(qeQuestionDTO1).isNotEqualTo(qeQuestionDTO2);
        qeQuestionDTO1.setId(null);
        assertThat(qeQuestionDTO1).isNotEqualTo(qeQuestionDTO2);
    }
}
