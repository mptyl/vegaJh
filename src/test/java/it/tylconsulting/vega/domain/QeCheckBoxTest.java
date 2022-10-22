package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeCheckBoxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeCheckBox.class);
        QeCheckBox qeCheckBox1 = new QeCheckBox();
        qeCheckBox1.setId(1L);
        QeCheckBox qeCheckBox2 = new QeCheckBox();
        qeCheckBox2.setId(qeCheckBox1.getId());
        assertThat(qeCheckBox1).isEqualTo(qeCheckBox2);
        qeCheckBox2.setId(2L);
        assertThat(qeCheckBox1).isNotEqualTo(qeCheckBox2);
        qeCheckBox1.setId(null);
        assertThat(qeCheckBox1).isNotEqualTo(qeCheckBox2);
    }
}
