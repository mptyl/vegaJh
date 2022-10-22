package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeReplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeReply.class);
        QeReply qeReply1 = new QeReply();
        qeReply1.setId(1L);
        QeReply qeReply2 = new QeReply();
        qeReply2.setId(qeReply1.getId());
        assertThat(qeReply1).isEqualTo(qeReply2);
        qeReply2.setId(2L);
        assertThat(qeReply1).isNotEqualTo(qeReply2);
        qeReply1.setId(null);
        assertThat(qeReply1).isNotEqualTo(qeReply2);
    }
}
