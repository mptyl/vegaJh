package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeRadioGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeRadioGroup.class);
        QeRadioGroup qeRadioGroup1 = new QeRadioGroup();
        qeRadioGroup1.setId(1L);
        QeRadioGroup qeRadioGroup2 = new QeRadioGroup();
        qeRadioGroup2.setId(qeRadioGroup1.getId());
        assertThat(qeRadioGroup1).isEqualTo(qeRadioGroup2);
        qeRadioGroup2.setId(2L);
        assertThat(qeRadioGroup1).isNotEqualTo(qeRadioGroup2);
        qeRadioGroup1.setId(null);
        assertThat(qeRadioGroup1).isNotEqualTo(qeRadioGroup2);
    }
}
