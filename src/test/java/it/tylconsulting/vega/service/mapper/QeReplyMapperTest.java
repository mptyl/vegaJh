package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeReplyMapperTest {

    private QeReplyMapper qeReplyMapper;

    @BeforeEach
    public void setUp() {
        qeReplyMapper = new QeReplyMapperImpl();
    }
}
