package it.tylconsulting.vega.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.tylconsulting.vega.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QeJumpExpressionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QeJumpExpression.class);
        QeJumpExpression qeJumpExpression1 = new QeJumpExpression();
        qeJumpExpression1.setId(1L);
        QeJumpExpression qeJumpExpression2 = new QeJumpExpression();
        qeJumpExpression2.setId(qeJumpExpression1.getId());
        assertThat(qeJumpExpression1).isEqualTo(qeJumpExpression2);
        qeJumpExpression2.setId(2L);
        assertThat(qeJumpExpression1).isNotEqualTo(qeJumpExpression2);
        qeJumpExpression1.setId(null);
        assertThat(qeJumpExpression1).isNotEqualTo(qeJumpExpression2);
    }
}
