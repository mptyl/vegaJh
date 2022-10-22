package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeGroup.class);
        QeGroup qeGroup1 = new QeGroup();
        qeGroup1.setId(1L);
        QeGroup qeGroup2 = new QeGroup();
        qeGroup2.setId(qeGroup1.getId());
        assertThat(qeGroup1).isEqualTo(qeGroup2);
        qeGroup2.setId(2L);
        assertThat(qeGroup1).isNotEqualTo(qeGroup2);
        qeGroup1.setId(null);
        assertThat(qeGroup1).isNotEqualTo(qeGroup2);
    }
}
