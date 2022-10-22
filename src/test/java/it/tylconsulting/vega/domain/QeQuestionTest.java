package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeQuestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeQuestion.class);
        QeQuestion qeQuestion1 = new QeQuestion();
        qeQuestion1.setId(1L);
        QeQuestion qeQuestion2 = new QeQuestion();
        qeQuestion2.setId(qeQuestion1.getId());
        assertThat(qeQuestion1).isEqualTo(qeQuestion2);
        qeQuestion2.setId(2L);
        assertThat(qeQuestion1).isNotEqualTo(qeQuestion2);
        qeQuestion1.setId(null);
        assertThat(qeQuestion1).isNotEqualTo(qeQuestion2);
    }
}
