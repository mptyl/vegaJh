package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeJumpExpressionMapperTest {

    private QeJumpExpressionMapper qeJumpExpressionMapper;

    @BeforeEach
    public void setUp() {
        qeJumpExpressionMapper = new QeJumpExpressionMapperImpl();
    }
}
