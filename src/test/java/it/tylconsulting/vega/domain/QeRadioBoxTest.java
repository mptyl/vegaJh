package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeRadioBoxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeRadioBox.class);
        QeRadioBox qeRadioBox1 = new QeRadioBox();
        qeRadioBox1.setId(1L);
        QeRadioBox qeRadioBox2 = new QeRadioBox();
        qeRadioBox2.setId(qeRadioBox1.getId());
        assertThat(qeRadioBox1).isEqualTo(qeRadioBox2);
        qeRadioBox2.setId(2L);
        assertThat(qeRadioBox1).isNotEqualTo(qeRadioBox2);
        qeRadioBox1.setId(null);
        assertThat(qeRadioBox1).isNotEqualTo(qeRadioBox2);
    }
}
