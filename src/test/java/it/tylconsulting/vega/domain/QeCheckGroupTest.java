package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeCheckGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeCheckGroup.class);
        QeCheckGroup qeCheckGroup1 = new QeCheckGroup();
        qeCheckGroup1.setId(1L);
        QeCheckGroup qeCheckGroup2 = new QeCheckGroup();
        qeCheckGroup2.setId(qeCheckGroup1.getId());
        assertThat(qeCheckGroup1).isEqualTo(qeCheckGroup2);
        qeCheckGroup2.setId(2L);
        assertThat(qeCheckGroup1).isNotEqualTo(qeCheckGroup2);
        qeCheckGroup1.setId(null);
        assertThat(qeCheckGroup1).isNotEqualTo(qeCheckGroup2);
    }
}
